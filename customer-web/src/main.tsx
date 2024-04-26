import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import { RouterProvider, createRouter } from "@tanstack/react-router";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { AuthProvider, useAuth } from "./store/useAuth";
import { routeTree } from "./routeTree.gen";
import { TooltipProvider } from "./components/ui/tooltip";

const router = createRouter({
  routeTree,
  defaultPreload: "intent",
  context: { auth: undefined! },
});

declare module "@tanstack/react-router" {
  interface Register {
    router: typeof router;
  }
}

const queryClient = new QueryClient();

declare module "@tanstack/react-router" {
  interface Register {
    router: typeof router;
  }
}

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <AuthProvider>
        <TooltipProvider>
          <Router />
        </TooltipProvider>
      </AuthProvider>
    </QueryClientProvider>
  </React.StrictMode>,
);

function Router() {
  const auth = useAuth();
  try {
    return <RouterProvider router={router} context={{ auth }} />;
  } catch (e) {
    console.error("err: ", e);
  }
}
