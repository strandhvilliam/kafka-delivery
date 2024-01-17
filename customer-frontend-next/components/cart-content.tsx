import { useOrder } from "@/lib/context/order-context";
import { Button } from "./ui/button";
import {
  SheetClose,
  SheetContent,
  SheetDescription,
  SheetFooter,
  SheetHeader,
  SheetTitle,
} from "./ui/sheet";

const fakeOrderItems = [
  {
    id: 1,
    name: "Product 1",
    price: 100,
  },
  {
    id: 2,
    name: "Product 2",
    price: 200,
  },
  {
    id: 3,
    name: "Product 2",
    price: 300,
  },
];

export default function CartContent() {
  const { placeOrder } = useOrder();
  return (
    <SheetContent className="gap-4 flex flex-col">
      <SheetHeader>
        <SheetTitle>Your cart</SheetTitle>
        <SheetDescription>Items will show up here</SheetDescription>
      </SheetHeader>
      <div className="flex flex-col gap-4">
        {fakeOrderItems.map((item) => (
          <div className="flex flex-row justify-between" key={item.id}>
            <div className="flex flex-row">
              <div className="flex flex-col">
                <span className="text-zinc-700">{item.name}</span>
              </div>
            </div>
            <div className="flex flex-col">
              <span className="text-zinc-700 text-sm">{item.price} SEK</span>
            </div>
          </div>
        ))}
        <hr className="border-zinc-800" />
        <div className="flex flex-row justify-between">
          <div className="flex flex-row">
            <div className="flex flex-col">
              <span className="text-zinc-700">Total:</span>
            </div>
          </div>
          <div className="flex flex-col">
            <span className="text-zinc-700 text-sm">600 SEK</span>
          </div>
        </div>
      </div>

      <SheetFooter>
        <SheetClose asChild>
          <Button onClick={placeOrder} className="w-full" type="submit">
            Place order
          </Button>
        </SheetClose>
      </SheetFooter>
    </SheetContent>
  );
}
