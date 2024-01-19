import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import "./index.css";
import { ThemeProvider } from "./store/theme-provider.tsx";
import { OrderEventProvider } from "./store/orders-provider.tsx";

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
      <OrderEventProvider>
        <App />
      </OrderEventProvider>
    </ThemeProvider>
  </React.StrictMode>,
);
