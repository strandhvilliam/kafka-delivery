import { Link, createFileRoute } from "@tanstack/react-router";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Separator } from "@/components/ui/separator";
import GoogleIcon from "@/components/GoogleIcon";

export const Route = createFileRoute("/_auth/login")({
  component: () => <LoginPage />,
});

export function LoginPage() {
  return (
    <div className="w-full lg:grid lg:min-h-[600px] lg:grid-cols-2 xl:min-h-[800px]">
      <div className="flex w-full py-4 justify-end px-4">
        <a className="underline">Delivery login</a>
      </div>
      <div className="flex items-center justify-center py-12">
        <div className="mx-auto grid px-4  gap-6">
          <div className="grid gap-2 text-center">
            <h1 className="text-5xl font-eloquent font-bold">Nom-Nom</h1>
            <Separator className="border-b-2 border-stone-800" />
          </div>
          <div className="grid gap-4 pt-8">
            <div className="grid gap-2">
              <Input
                className="bg-transparent border-2 pl-6 placeholder:text-stone-800 border-stone-800 rounded-full py-6"
                id="email"
                type="email"
                placeholder="Email"
                required
              />
            </div>
            <div className="grid gap-2">
              <Input
                className="bg-transparent border-2 pl-6 placeholder:text-stone-800 border-stone-800 rounded-full py-6"
                id="password"
                type="password"
                placeholder="Password"
                required
              />
              <div className="flex items-center">
                <Link
                  href="/forgot-password"
                  className="ml-auto inline-block text-sm underline"
                >
                  Forgot your password?
                </Link>
              </div>
            </div>
            <Button
              type="submit"
              className="text-lg mt-4 font-bold py-6 border-2 border-stone-800  w-full rounded-full"
            >
              Login
            </Button>
            <div className="text-center text-sm">
              Don&apos;t have an account?{" "}
              <Link href="#" className="underline">
                Sign up
              </Link>
            </div>
            <div className="mt-6">
              <Button
                variant="outline"
                className="overflow-hidden flex gap-2 items-center bg-transparent w-full py-6 border-2 border-stone-800 rounded-full"
              >
                <GoogleIcon className="w-5 h-5" />
                Login with Google
              </Button>
            </div>
            <div>
              <p className="text-center text-sm">
                By Signing in with google you agree to Nom-nom’s{" "}
                <a className="underline whitespace-nowrap">Terms Of Use</a> and{" "}
                <a className="underline whitespace-nowrap">Privacy Policy</a>.
              </p>
            </div>
          </div>
        </div>
      </div>
      <div className="hidden bg-muted lg:block">
        <img
          src="/placeholder.svg"
          alt="Image"
          width="1920"
          height="1080"
          className="h-full w-full object-cover dark:brightness-[0.2] dark:grayscale"
        />
      </div>
    </div>
  );
}

