// import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import "./index.css";
import { OrderEventProvider } from "./store/order-event-context.tsx";
import React from "react";
import { Toaster } from "./components/ui/toaster.tsx";
import { ThemeProvider } from "./store/theme-provider.tsx";
import { GeoLocationProvider } from "./store/geolocation-provider.tsx";

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
      <OrderEventProvider>
        <GeoLocationProvider>
          <Toaster />
          <App />
        </GeoLocationProvider>
      </OrderEventProvider>
    </ThemeProvider>
  </React.StrictMode>,
);
