import { IdInput } from "@/components/idInput";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";

export default function Driver() {
  return (
    <Card>
      <CardHeader>
        <CardTitle>Driver Analytics</CardTitle>
        <CardDescription>Please enter driver ID</CardDescription>
      </CardHeader>

      <CardContent>
        <IdInput target="driver" />
      </CardContent>
    </Card>
  );
}
