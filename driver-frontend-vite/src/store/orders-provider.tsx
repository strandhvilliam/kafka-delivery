import { Job, deliverPost, jobsGet, pickupPost } from "@/lib/api";
import React, { createContext, useContext, useEffect, useState } from "react";

const OrderEventContext = createContext<{
  data: Job[];
  pickupOrder: (id: string) => void;
  deliverOrder: (id: string) => void;
}>({
  data: [],
  pickupOrder: () => null,
  deliverOrder: () => null,
});

export const useOrderEvents = () => {
  const context = useContext(OrderEventContext);
  if (!context) {
    throw new Error("useOrderEvents must be used within an OrderEventProvider");
  }
  return context;
};

const sseUrl = `http://localhost:48082/sse?driverId=driver-1`;

export const OrderEventProvider = ({
  children,
}: {
  children: React.ReactNode;
}) => {
  const [data, setData] = useState<Job[]>([]);

  useEffect(() => {
    (async () => {
      const res = await jobsGet("driver-1");

      const data = res.map((d) => JSON.parse(d)) as Job[];

      setData(data);
    })();
  }, []);

  useEffect(() => {
    const eventSource = new EventSource(sseUrl);
    eventSource.addEventListener("message", (event) => {
      console.log(event);
      const eventData = JSON.parse(event.data);

      console.log(eventData);

      setData((prevData) => [...prevData, eventData]);
    });

    return () => {
      eventSource.close();
    };
  }, []);

  const pickupOrder = async (id: string) => {
    await pickupPost(id);
    setData((prevData) => prevData.filter((d) => d.id !== id));
  };

  const deliverOrder = async (id: string) => {
    await deliverPost(id);
    setData((prevData) => prevData.filter((d) => d.id !== id));
  };

  return (
    <OrderEventContext.Provider value={{ data, pickupOrder, deliverOrder }}>
      {children}
    </OrderEventContext.Provider>
  );
};
