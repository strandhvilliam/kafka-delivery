import { Button } from "@/components/ui/button";
import {
  Drawer,
  DrawerContent,
  DrawerFooter,
  DrawerHeader,
  DrawerTitle,
  DrawerTrigger,
} from "@/components/ui/drawer";
import { Separator } from "@/components/ui/separator";
import { Outlet, createFileRoute, useRouter } from "@tanstack/react-router";
import { ChevronLeft, ShoppingCart } from "lucide-react";

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
          <Drawer>
            <DrawerTrigger asChild>
              <button className="bg-transparent text-white p-4" type="button">
                <ShoppingCart className="w-6 h-6" />
              </button>
            </DrawerTrigger>
            <CartDrawer />
          </Drawer>
        </header>
        <Outlet />
      </div>
    </div>
  );
}

const chosenProducts = [
  {
    id: "1",
    name: "Pizza nomero uno",
    description: "Pizza description goes here.",
    quantity: 1,
  },
  {
    id: "2",
    name: "Pizza nomero dos",
    description: "Pizza description goes here.",
    quantity: 2,
  },
  {
    id: "2",
    name: "Pizza nomero dos",
    description: "Pizza description goes here.",
    quantity: 2,
  },
  {
    id: "2",
    name: "Pizza nomero dos",
    description: "Pizza description goes here.",
    quantity: 2,
  },
  {
    id: "2",
    name: "Pizza nomero dos",
    description: "Pizza description goes here.",
    quantity: 2,
  },
  {
    id: "2",
    name: "Pizza nomero dos",
    description: "Pizza description goes here.",
    quantity: 2,
  },
];

function CartDrawer() {
  return (
    <DrawerContent className="h-[60%]">
      <DrawerHeader>
        <DrawerTitle className="text-2xl">Cart</DrawerTitle>
        <Separator className="b px-4" />
      </DrawerHeader>
      <div className="w-full h-full overflow-y-auto">
        {chosenProducts.map((product) => (
          <div
            key={product.id}
            className="flex justify-between items-center gap-4 p-4"
          >
            <div className="flex flex-col gap-1">
              <h3 className="text-lg">{product.name}</h3>
              <p className="text-sm">{product.description}</p>
            </div>
            <div className="flex gap-2 items-center">
              <Button variant="outline" size="icon">
                -
              </Button>
              <span>{product.quantity}</span>
              <Button variant="outline" size="icon">
                +
              </Button>
            </div>
          </div>
        ))}
      </div>
      <DrawerFooter>
        <Button>Go to checkout</Button>
      </DrawerFooter>
    </DrawerContent>
  );
}
