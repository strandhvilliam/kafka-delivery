import { IdInput } from "@/components/idInput";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";

//122a7242-ae5f-11ee-b2a3-0242c0a8b004

export default async function DriverAnalytics({
  params,
}: {
  params: { id: string };
}) {
  const getAveragePickupToDeliveryTime = async () => {
    const response = await fetch(
      `http://localhost:48090/average-pickup-to-delivered-time/${params.id}`,
    );
    const json = await response.json();
    console.log(json);
    return json;
  };

  const getAverageRestaurantFinishToPickupTime = async () => {
    const response = await fetch(
      `http://localhost:48090/average-restaurant-finish-to-pickup-time/${params.id}`,
    );
    const json = await response.json();
    console.log(json);
    return json;
  };

  return (
    <Card>
      <CardHeader>
        <CardTitle>{`Driver ID: ${params.id}`}</CardTitle>
        <CardDescription>
          This is the current analytics for the driver
        </CardDescription>
      </CardHeader>

      <CardContent className="grid grid-cols-2 gap-4">
        <Card className="col-span-1">
          <CardHeader className="flex space-y-0 pb-2">
            <CardTitle className="text-sm font-normal">
              Average pickup to delivery time
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-lg font-bold">
              {(await getAveragePickupToDeliveryTime()) + ""} seconds
            </div>
            <p className="text-xs text-muted-foreground">Period: forever</p>
          </CardContent>
        </Card>
        <Card className="col-span-1">
          <CardHeader className="flex space-y-0 pb-2">
            <CardTitle className="text-sm font-normal">
              Average restaurant finish to pickup time
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-lg font-bold">1 min, 52 sec</div>
            <p className="text-xs text-muted-foreground">Period: forever</p>
          </CardContent>
        </Card>
      </CardContent>
    </Card>
  );
}
