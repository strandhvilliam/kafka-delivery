import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/_auth/signup")({
  component: () => <SignUpPage />,
});

export function SignUpPage() {
  return (
    <div className="w-full lg:grid lg:min-h-[600px] lg:grid-cols-2 xl:min-h-[800px]">
      <div className="flex items-center justify-center py-12">
        <div className="mx-auto grid px-4  gap-6">
          <div className="flex w-full justify-center">
            <h1 className="text-2xl  font-bold">Create Account</h1>
          </div>
          <div className="grid gap-4">
            <div className="grid gap-2">
              <Input
                className="bg-transparent border-2 pl-6 placeholder:text-stone-800 border-stone-800 rounded-full py-6"
                id="firstname"
                type="text"
                placeholder="First Name"
                required
              />
            </div>
            <div className="grid gap-2">
              <Input
                className="bg-transparent border-2 pl-6 placeholder:text-stone-800 border-stone-800 rounded-full py-6"
                id="lastname"
                type="text"
                placeholder="Last Name"
                required
              />
            </div>
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
                id="phone"
                type="text"
                placeholder="Phone"
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
            </div>
            <div className="grid gap-2">
              <Input
                className="bg-transparent border-2 pl-6 placeholder:text-stone-800 border-stone-800 rounded-full py-6"
                id="repeatpassword"
                type="password"
                placeholder="Repeat Password"
                required
              />
            </div>
            <Button
              type="submit"
              className="text-lg mt-4 font-bold py-6 border-2 border-stone-800  w-full rounded-full"
            >
              Sign Up
            </Button>
            <div>
              <p className="text-center text-sm">
                By Signing up you agree to Nom-nom’s{" "}
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

