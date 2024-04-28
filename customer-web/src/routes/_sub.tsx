import { CartBtn } from "@/components/Cart";
import { Outlet, createFileRoute, useRouter } from "@tanstack/react-router";
import { ChevronLeft } from "lucide-react";

export const Route = createFileRoute("/_sub")({
  component: () => <SubLayout />,
});

function SubLayout() {
  const router = useRouter();

  const handleClick = () => {
    router.history.go(-1);
  };
  return (
    <div className="flex min-h-screen w-full flex-col bg-background ">
      <div className="flex flex-col sm:gap-4 pb-8 sm:py-4 sm:pl-14 bg-muted">
        <header className="absolute justify-between pr-1 w-full bg-transparent top-0 py-4 z-30 flex h-20 items-center gap-4  sm:static sm:h-auto sm:border-0 sm:px-6">
          <button
            className="bg-transparent text-white p-4"
            onClick={handleClick}
            type="button"
          >
            <ChevronLeft className="w-8 h-8" />
          </button>
          <CartBtn className="text-white" />
        </header>
        <Outlet />
      </div>
    </div>
  );
}
