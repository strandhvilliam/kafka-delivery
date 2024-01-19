import { Button } from "./components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "./components/ui/card";
import { OrderEventData, useOrderEvents } from "./store/order-provider";

function App() {
  const { data, setOrderReady } = useOrderEvents();
  return (
    <main className="min-h-screen w-full flex ">
      <div className="container flex flex-col gap-2 rounded-md  mx-auto w-10/12 mt-12">
        <div className="flex flex-col gap-4 mb-4">
          <h1 className="text-4xl font-bold">Restaurant Demo</h1>
        </div>
        {data.map((orderEvent: OrderEventData) => (
          <Card
            key={orderEvent.id}
            className="flex items-center justify-between"
          >
            <CardHeader>
              <CardTitle>{orderEvent.status}</CardTitle>
              <CardDescription>{orderEvent.id}</CardDescription>
            </CardHeader>
            <CardContent className="py-0 ">
              {orderEvent.status === "ORDER_CREATED" ? (
                <Button onClick={() => setOrderReady(orderEvent.id)}>
                  Finish order
                </Button>
              ) : (
                <Button
                  type="button"
                  className="hover:bg-zinc-600 cursor-default bg-zinc-600 rounded px-4 py-2"
                >
                  Waiting for pickup
                </Button>
              )}
            </CardContent>
          </Card>
        ))}
      </div>
    </main>
  );
}

export default App;
