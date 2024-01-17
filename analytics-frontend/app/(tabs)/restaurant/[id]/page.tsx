import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";

export default async function RestaurantAnalytics({
  params,
}: {
  params: { id: string };
}) {
  const getAverageTotalDeliveryTime = async () => {
    const response = await fetch(
      `http://127.0.0.1:48090/average-total-delivery-time/${params.id}`,
    );

    return response.json();
  };
  const getPopularProduct = async () => {
    const response = await fetch(
      `http://127.0.0.1:48090/most-popular-product/${params.id}`,
    );

    return response.text();
  };
  const getAverageRestaurantFinishTime = async () => {
    const response = await fetch(
      `http://127.0.0.1:48090/average-restaurant-finish-time/${params.id}`,
    );

    return response.json();
  };
  const getAverageOrderValue = async () => {
    const response = await fetch(
      `http://127.0.0.1:48090/average-order-value/${params.id}`,
    );

    return response.json();
  };

  return (
    <Card>
      <CardHeader>
        <CardTitle>{`Restaurant ID: ${params.id}`}</CardTitle>
        <CardDescription>
          This is the current analytics for restaurant
        </CardDescription>
      </CardHeader>

      <CardContent className="grid grid-cols-2 gap-4">
        <Card className="col-span-1">
          <CardHeader className="flex space-y-0 pb-2">
            <CardTitle className="text-sm font-normal">
              Popular Product
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-lg font-bold">
              {((await getPopularProduct()) + "").split("(")[0]}
            </div>
            <p className="text-xs text-muted-foreground">
              Quantity Sold:{" "}
              {
                ((await getPopularProduct()) + "")
                  .split("(")[1]
                  .split("sold")[0]
              }
            </p>
          </CardContent>
        </Card>
        <Card className="col-span-1">
          <CardHeader className="flex space-y-0 pb-2">
            <CardTitle className="text-sm font-normal">
              Average order value
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-lg font-bold">
              {await getAverageOrderValue()} SEK
            </div>
            <p className="text-xs text-muted-foreground">Period: forever</p>
          </CardContent>
        </Card>
        <Card className="col-span-1">
          <CardHeader className="flex space-y-0 pb-2">
            <CardTitle className="text-sm font-normal">
              Average total delivery time
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-lg font-bold">
              {await getAverageTotalDeliveryTime()} seconds
            </div>
            <p className="text-xs text-muted-foreground">Period: forever</p>
          </CardContent>
        </Card>
        <Card className="col-span-1">
          <CardHeader className="flex space-y-0 pb-2">
            <CardTitle className="text-sm font-normal">
              Average restaurant finish time
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-lg font-bold">
              {await getAverageRestaurantFinishTime()} seconds
            </div>
            <p className="text-xs text-muted-foreground">Period: forever</p>
          </CardContent>
        </Card>
      </CardContent>
    </Card>
  );
}
