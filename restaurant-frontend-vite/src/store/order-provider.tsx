import React, { createContext, useContext, useEffect, useState } from "react";

export type OrderEventData = {
  id: string;
  status: string;
};

const OrderEventContext = createContext<{
  data: OrderEventData[];
  setOrderReady: (id: string) => void;
}>({
  data: [],
  setOrderReady: () => null,
});

export const useOrderEvents = () => {
  const context = useContext(OrderEventContext);
  if (!context) {
    throw new Error("useOrderEvents must be used within an OrderEventProvider");
  }
  return context;
};

const sseUrl = `http://localhost:48083/orders/sse/res1`;
const url = `http://localhost:48083/orders/res1`;
const updateUrl = `http://localhost:48083/order`;

export const OrderEventProvider = ({
  children,
}: {
  children: React.ReactNode;
}) => {
  const [data, setData] = useState<OrderEventData[]>([]);

  useEffect(() => {
    (async () => {
      const response = await fetch(url);
      const json = (await response.json()) as string[];
      const data = json.map((str) => JSON.parse(str) as OrderEventData);
      setData(() => data);
    })();
  }, []);

  useEffect(() => {
    const eventSource = new EventSource(sseUrl);
    eventSource.addEventListener("message", (event) => {
      const eventData = JSON.parse(event.data);
      console.log("Received event:", eventData);

      if (
        eventData.status === "ORDER_PICKED_UP" ||
        eventData.status === "ORDER_DELIVERED"
      ) {
        setData((prevData) => prevData.filter((d) => d.id !== eventData.id));
        return;
      }

      setData((prevData) => [...prevData, eventData]);
    });

    eventSource.addEventListener("error", (error) => {
      console.error("Error with SSE connection:", error);
      eventSource.close();
    });

    return () => {
      eventSource.close();
    };
  }, []);

  const setOrderReady = async (id: string) => {
    const response = await fetch(`${updateUrl}/${id}`, {
      method: "POST",
    });
    if (response.status !== 200) {
      throw new Error(`Error setting order ${id} to ready`);
    }

    setData((prevData) =>
      prevData.map((d) => (d.id === id ? { ...d, status: "ORDER_READY" } : d)),
    );
  };

  return (
    <OrderEventContext.Provider value={{ data, setOrderReady }}>
      {children}
    </OrderEventContext.Provider>
  );
};
