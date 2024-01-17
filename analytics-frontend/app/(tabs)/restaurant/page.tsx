import { IdInput } from "@/components/idInput";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";

export default function Restaurant() {
  return (
    <Card>
      <CardHeader>
        <CardTitle>Restaurant Analytics</CardTitle>
        <CardDescription>Please enter restaurant ID</CardDescription>
      </CardHeader>

      <CardContent>
        <IdInput target="restaurant" />
      </CardContent>
    </Card>
  );
}
