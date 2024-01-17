import DriverMap from "./components/driver-map";
import { Button } from "./components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "./components/ui/card";
import { useToast } from "./components/ui/use-toast";
import { useOrderEvents } from "./store/order-event-context";

function App() {
  const { data: events } = useOrderEvents();
  const { toast } = useToast();

  const createDemoOrder = async () => {
    const response = await fetch("http://localhost:48081/order", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        customerId: "user123",
        restaurantId: "res1",
        productIds: ["abc125", "abc124"],
      }),
    });

    toast({
      title: "Order created",
      description: `Order created with id:  ${await response.text()}`,
    });
  };

  return (
    <main className="min-h-screen w-full flex ">
      <div className="container flex flex-col gap-2 rounded-md  mx-auto w-10/12 mt-12">
        <div className="flex flex-col gap-4 mb-4">
          <h1 className="text-4xl font-bold">Customer Demo</h1>
        </div>
        <div>
          <Button onClick={createDemoOrder}>Create demo order</Button>
        </div>
        <Card>
          <CardHeader>
            <CardTitle>Delivery driver location</CardTitle>
          </CardHeader>
          <CardContent>
            <DriverMap />
          </CardContent>
        </Card>
        <Card>
          <CardHeader>
            <CardTitle>Events</CardTitle>
          </CardHeader>
          <CardContent>
            <CardContent className="flex flex-col gap-2">
              {events.map((event, index) => (
                <div key={index} className="flex flex-col gap-1">
                  <span className="text-sm font-bold">{event.id}</span>
                  <span className="text-sm">{event.status}</span>
                </div>
              ))}
            </CardContent>
          </CardContent>
        </Card>
      </div>
    </main>
  );
}

export default App;
