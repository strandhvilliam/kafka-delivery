"use client";
import React, { createContext, useContext, useState } from "react";

const OrderContext = createContext(
  {} as {
    orderItems: any[];
    addItemToOrder: (item: any) => void;
    removeItemFromOrder: (itemId: string) => void;
    placeOrder: () => void;
  },
);

export const useOrder = () => {
  return useContext(OrderContext);
};

export const OrderProvider = ({ children }: any) => {
  const [orderItems, setOrderItems] = useState<any[]>([]);

  const addItemToOrder = (item: any) => {
    setOrderItems((prevItems) => [...prevItems, item]);
  };

  const removeItemFromOrder = (itemId: string) => {
    setOrderItems((prevItems) =>
      prevItems.filter((item) => item.id !== itemId),
    );
  };

  const placeOrder = async () => {
    const body = JSON.stringify({
      customerId: "user123",
      restaurantId: "res1",
      productIds: ["abc125", "abc124"],
    });

    try {
      const response = await fetch("http://localhost:48081/order", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body,
      });

      console.log(response);
    } catch (error) {
      console.log(error);
    }

    setOrderItems([]);
  };

  // Value object to be provided by the context
  const contextValue = {
    orderItems,
    addItemToOrder,
    removeItemFromOrder,
    placeOrder,
  };

  // Provide the context value to the children components
  return (
    <OrderContext.Provider value={contextValue}>
      {children}
    </OrderContext.Provider>
  );
};
