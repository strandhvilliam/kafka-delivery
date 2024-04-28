import { ShoppingCart } from "lucide-react";
import { Button } from "./ui/button";
import {
  Drawer,
  DrawerTrigger,
  DrawerContent,
  DrawerFooter,
  DrawerHeader,
  DrawerTitle,
} from "./ui/drawer";
import { Separator } from "./ui/separator";
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
    id: "3",
    name: "Pizza nomero tres",
    description: "Pizza description goes here.",
    quantity: 1,
  },
];

export function CartBtn({ className }: { className?: string }) {
  return (
    <Drawer>
      <DrawerTrigger asChild>
        <button className={`${className} bg-transparent p-4`} type="button">
          <ShoppingCart className="w-6 h-6" />
        </button>
      </DrawerTrigger>
      <CartDrawer />
    </Drawer>
  );
}

function CartDrawer() {
  return (
    <DrawerContent className="h-[75%]">
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
