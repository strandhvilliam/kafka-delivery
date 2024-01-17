import {
  CardTitle,
  CardDescription,
  CardHeader,
  CardContent,
  CardFooter,
  Card,
} from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import Link from "next/link";

export default function Component() {
  return (
    <>
      <Card className="max-w-md mx-auto mt-10">
        <CardHeader>
          <CardTitle className="text-3xl font-bold text-center">
            Welcome Back!
          </CardTitle>
          <CardDescription className="text-center text-sm">
            Please enter your credentials to log in.
          </CardDescription>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="space-y-2">
            <Label
              className="text-sm font-medium text-gray-700"
              htmlFor="username"
            >
              Username
            </Label>
            <Input
              className="w-full p-2 border border-gray-300 rounded"
              id="username"
              placeholder="Enter your username"
              required
              type="text"
            />
          </div>
          <div className="space-y-2">
            <Label
              className="text-sm font-medium text-gray-700"
              htmlFor="password"
            >
              Password
            </Label>
            <Input
              className="w-full p-2 border border-gray-300 rounded"
              id="password"
              placeholder="Enter your password"
              required
              type="password"
            />
          </div>
        </CardContent>
        <CardFooter className="flex justify-end">
          <Button className="w-32 text-white bg-blue-500 hover:bg-blue-700">
            Login
          </Button>
        </CardFooter>
      </Card>
      <Link
        className="block text-center mt-4 text-sm text-blue-500 hover:underline"
        href="#"
      >
        Forgot your password?
      </Link>
    </>
  );
}
