import { Card, CardContent, CardHeader, CardTitle } from "./components/ui/card";
import { Form, FormLabel } from "./components/ui/form";
import { Button } from "./components/ui/button";
import {
  FormControl,
  FormField,
  FormItem,
  FormMessage,
} from "./components/ui/form";
import { Input } from "./components/ui/input";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import * as z from "zod";
import { toast } from "./components/ui/use-toast";
import { useOrderEvents } from "./store/orders-provider";
import { postGeolocation } from "./lib/api";

const FormSchema = z.object({
  latitude: z.string(),
  longitude: z.string(),
  orderId: z.string(),
});

function App() {
  const { data, pickupOrder, deliverOrder } = useOrderEvents();

  const form = useForm<z.infer<typeof FormSchema>>({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      latitude: "59.345415390174985",
      longitude: "18.023276463336853",
      orderId: "",
    },
  });

  const onSubmit = async (data: z.infer<typeof FormSchema>) => {
    const { latitude, longitude, orderId } = data;

    const driverId = "driver-1";
    await postGeolocation({ latitude, longitude, orderId, driverId });

    toast({
      title: "Geolocation updated:",
    });
  };

  return (
    <main className="min-h-screen w-full flex ">
      <div className="container flex flex-col gap-2 rounded-md  mx-auto w-10/12 mt-12">
        <div className="flex flex-col gap-4 mb-4">
          <h1 className="text-4xl font-bold">Driver Demo</h1>
        </div>
        <Card>
          <CardHeader>
            <CardTitle>Update Location</CardTitle>
          </CardHeader>
          <CardContent>
            <Form {...form}>
              <form
                onSubmit={form.handleSubmit(onSubmit)}
                className="w-full flex flex-col gap-4 "
              >
                <FormField
                  control={form.control}
                  name="latitude"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Latitude</FormLabel>
                      <FormControl>
                        <Input placeholder="latitude" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="longitude"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Longitude</FormLabel>
                      <FormControl>
                        <Input placeholder="longitude" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="orderId"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Order ID</FormLabel>
                      <FormControl>
                        <Input placeholder="abc123" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <div>
                  <Button type="submit">Submit</Button>
                </div>
              </form>
            </Form>
          </CardContent>
        </Card>
        <Card>
          <CardHeader>
            <CardTitle>Events</CardTitle>
          </CardHeader>
          <CardContent>
            <CardContent className="flex flex-col gap-2">
              {data.map((event, index) => (
                <Card key={index}>
                  <CardContent className="flex flex-col gap-2 justify-center py-4">
                    <div className="flex flex-col justify-center ">
                      <span className="text-sm font-bold">Status</span>
                      <span>{event.status}</span>
                    </div>
                    <div className="flex flex-col">
                      <span className="text-sm font-bold">Order ID</span>
                      <span>{event.id}</span>
                    </div>
                    {event.status === "ORDER_READY" && (
                      <div>
                        <Button onClick={() => pickupOrder(event.id)}>
                          Pickup
                        </Button>
                      </div>
                    )}
                    {event.status === "ORDER_CREATED" && (
                      <div>
                        <Button disabled className="bg-gray-600">
                          Waiting
                        </Button>
                      </div>
                    )}
                    {event.status === "ORDER_PICKED_UP" && (
                      <div>
                        <Button
                          onClick={() => deliverOrder(event.id)}
                          className="bg-green-400"
                        >
                          Deliver
                        </Button>
                      </div>
                    )}
                  </CardContent>
                </Card>
              ))}
            </CardContent>
          </CardContent>
        </Card>
      </div>
    </main>
  );
}

export default App;
