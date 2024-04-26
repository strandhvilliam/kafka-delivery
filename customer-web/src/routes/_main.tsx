import { SheetMenu } from "@/components/SheetMenu";
import {
  Outlet,
  createFileRoute,
  useRouterState,
} from "@tanstack/react-router";

export const Route = createFileRoute("/_main")({
  component: () => <MainLayout />,
});

function MainLayout() {
  const routerState = useRouterState();
  const title = parseTitle(routerState.location.pathname);
  return (
    <>
      <div className="flex min-h-screen w-full flex-col bg-background ">
        <div className="flex flex-col sm:gap-4 pb-8 sm:py-4 sm:pl-14 bg-muted">
          <SheetMenu title={title} />
          <div className="border-b-2 border-b-stone-800 mx-4 " />
          <Outlet />
        </div>
      </div>
    </>
  );
}

function parseTitle(pathname: string) {
  const v1 = pathname.split("/").pop();
  if (!v1) {
    return "";
  }
  return v1.slice(0, 1).toUpperCase() + v1.slice(1);
}
