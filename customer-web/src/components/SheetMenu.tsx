import { Link } from "@tanstack/react-router";
import { ChefHat, LogOut, Menu, ShoppingBag, UserCircle } from "lucide-react";
import { CartBtn } from "./Cart";
import { Button } from "./ui/button";
import { Sheet, SheetClose, SheetContent, SheetTrigger } from "./ui/sheet";
import Logo from "./Logo";

export function SheetMenu({ title }: { title: string }) {
  return (
    <header className="top-0  py-4  z-30 flex h-20 items-center gap-4  bg-muted px-4 sm:static sm:h-auto sm:border-0 sm:bg-transparent sm:px-6">
      <Sheet>
        <SheetTrigger asChild>
          <Button
            size="icon"
            variant="outline"
            className="sm:hidden border-0 absolute z-10 bg-muted"
          >
            <Menu className="h-8 w-8" />
            <span className="sr-only">Toggle Menu</span>
          </Button>
        </SheetTrigger>
        <SheetContent
          side="left"
          className="flex flex-col justify-between sm:max-w-xs bg-muted"
        >
          <nav className="grid gap-6 pt-12 text-lg font-loos font-medium">
            <Logo className="w-12 h-12 " />
            <SheetClose asChild>
              <Link
                activeProps={{
                  style: {
                    color: "var(--color-foreground)",
                  },
                }}
                to="/restaurants"
                className="flex items-center gap-4 px-2.5 text-muted-foreground hover:text-foreground"
              >
                <ChefHat className="h-5 w-5" />
                Restaurants
              </Link>
            </SheetClose>
            <SheetClose asChild>
              <Link
                activeProps={{
                  style: {
                    color: "var(--color-foreground)",
                  },
                }}
                to="/orders"
                className="flex items-center gap-4 px-2.5 text-muted-foreground"
              >
                <ShoppingBag className="h-5 w-5" />
                Orders
              </Link>
            </SheetClose>
            <SheetClose asChild>
              <Link
                activeProps={{
                  style: {
                    color: "var(--color-foreground)",
                  },
                }}
                to="/account"
                className="flex items-center gap-4 px-2.5 text-muted-foreground hover:text-foreground"
              >
                <UserCircle className="h-5 w-5" />
                Account
              </Link>
            </SheetClose>
          </nav>
          <Button className="font-bold w-full flex gap-4">
            Log out
            <LogOut className="h-5 w-5" />
          </Button>
        </SheetContent>
      </Sheet>
      <div className="relative items-center  flex justify-center flex-1 md:grow-0">
        <h1 className="font-eloquent text-3xl font-semibold pt-2 text-foreground">
          {title}
        </h1>
      </div>
      <CartBtn className="absolute right-1" />
    </header>
  );
}
