"use client";
import React from "react";
import { OrderProvider } from "./order-context";

export default function Providers({ children }: { children: React.ReactNode }) {
  return <OrderProvider>{children}</OrderProvider>;
}
