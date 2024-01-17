import React, { createContext, useContext, useEffect, useState } from "react";

type OrderEvent = {
  event: string;
  id: string;
  customerId: string;
  status: string;
};

type OrderEventData = {
  id: string;
  customerId: string;
  status: string;
};

const OrderEventContext = createContext<{ data: OrderEventData[] }>({
  data: [],
});

export const useOrderEvents = () => {
  const context = useContext(OrderEventContext);
  if (!context) {
    throw new Error("useOrderEvents must be used within an OrderEventProvider");
  }
  return context;
};

const sseUrl = `http://localhost:48081/orders/sse/user123`;
const url = `http://localhost:48081/orders/user123`;

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
      const eventData = JSON.parse(event.data) as OrderEvent;

      if (eventData.event === "order-event") {
        const orderData: OrderEventData = {
          id: eventData.id,
          customerId: eventData.customerId,
          status: eventData.status,
        };
        setData((prevData) => [...prevData, orderData]);
      }
    });

    eventSource.addEventListener("error", (error) => {
      console.error("Error with SSE connection:", error);
      eventSource.close();
    });

    return () => {
      eventSource.close();
    };
  }, []);

  return (
    <OrderEventContext.Provider value={{ data }}>
      {children}
    </OrderEventContext.Provider>
  );
};
