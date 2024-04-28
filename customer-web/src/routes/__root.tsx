import { Outlet, createRootRouteWithContext } from "@tanstack/react-router";
import { AuthContext } from "../store/useAuth";

interface RootRouteContext {
  auth: AuthContext | undefined;
}

export const Route = createRootRouteWithContext<RootRouteContext>()({
  component: () => (
    <div className="font-loos">
      <Outlet />
    </div>
  ),
});
