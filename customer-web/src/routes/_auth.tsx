import { Outlet, createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/_auth")({
  component: () => <AuthLayout />,
});

function AuthLayout() {
  return (
    <div className="min-h-dvh items-center justify-center flex flex-col">
      <Outlet />
    </div>
  );
}
