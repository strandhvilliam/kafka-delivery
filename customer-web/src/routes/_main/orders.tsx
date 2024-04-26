import MapView from "@/components/MapView";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Separator } from "@/components/ui/separator";
import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/_main/orders")({
  component: () => <OrdersPage />,
});

const items = [
  {
    id: "1",
    name: "Pizza A la carte",
    price: 129,
  },
  {
    id: "2",
    name: "Pizza B la carte",
    price: 79,
  },
  {
    id: "3",
    name: "Pizza C la carte",
    price: 99,
  },
];

function OrdersPage() {
  return (
    <div className="flex min-h-screen w-full flex-col bg-muted overflow-hidden">
      <div className="flex py-8 gap-8 px-4 items-center flex-col sm:gap-4 sm:py-4 sm:pl-14">
        <MapView />
        <h3 className="text-xl font-bold">Order is on it's way!</h3>
        <Card className=" w-[80%] border-stone-800 border-2">
          <CardContent className="py-6 items-center flex gap-1 flex-col">
            <span className="text-sm font-semibold">Expected time:</span>
            <span className="text-5xl font-bold">15 min</span>
          </CardContent>
        </Card>
        <Card className="w-full">
          <CardHeader>
            <CardTitle>Items</CardTitle>
          </CardHeader>
          <CardContent>
            <Separator className="border-stone-800 mb-4 border-b-2" />
            {items.map((item) => (
              <>
                <div className="flex justify-between py-2">
                  <span>{item.name}</span>
                  <span>{item.price} kr</span>
                </div>
                <Separator className="border-stone-800 my-4 border-b-2" />
              </>
            ))}
          </CardContent>
        </Card>
      </div>
    </div>
  );
}

