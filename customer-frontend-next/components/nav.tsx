"use client";

import Link from "next/link";
import { Button } from "./ui/button";
import { usePathname } from "next/navigation";
import { Sheet, SheetTrigger } from "./ui/sheet";
import CartContent from "./cart-content";

export default function Nav() {
  const pathname = usePathname();
  return (
    <div className="w-full flex flex-row">
      <Link className="mr-6 lg:flex" href="/restaurants">
        <span className="font-serif font-bold text-xl underline">Nom-nom</span>
      </Link>
      <div className="ml-auto flex gap-2">
        <Link
          className={`${
            pathname.includes("/restaurants") && "!font-bold"
          } group inline-flex h-9 w-max items-center justify-center rounded-md bg-white px-4 py-2 text-sm font-medium transition-colors hover:bg-gray-100 hover:text-gray-900 focus:bg-gray-100 focus:text-gray-900 focus:outline-none disabled:pointer-events-none disabled:opacity-50 data-[active]:bg-gray-100/50 data-[state=open]:bg-gray-100/50 dark:bg-gray-950 dark:hover:bg-gray-800 dark:hover:text-gray-50 dark:focus:bg-gray-800 dark:focus:text-gray-50 dark:data-[active]:bg-gray-800/50 dark:data-[state=open]:bg-gray-800/50`}
          href="/restaurants"
        >
          Restaurants
        </Link>
        <Link
          className={`
            ${pathname.includes("/orders") && "!font-bold"}
          group inline-flex h-9 w-max items-center justify-center rounded-md bg-white px-4 py-2 text-sm font-medium transition-colors hover:bg-gray-100 hover:text-gray-900 focus:bg-gray-100 focus:text-gray-900 focus:outline-none disabled:pointer-events-none disabled:opacity-50 data-[active]:bg-gray-100/50 data-[state=open]:bg-gray-100/50 dark:bg-gray-950 dark:hover:bg-gray-800 dark:hover:text-gray-50 dark:focus:bg-gray-800 dark:focus:text-gray-50 dark:data-[active]:bg-gray-800/50 dark:data-[state=open]:bg-gray-800/50`}
          href="/orders"
        >
          Orders
        </Link>
        <Link
          className={`
          ${pathname.includes("/profile") && "!font-bold"}
          group inline-flex h-9 w-max items-center justify-center rounded-md bg-white px-4 py-2 text-sm font-medium transition-colors hover:bg-gray-100 hover:text-gray-900 focus:bg-gray-100 focus:text-gray-900 focus:outline-none disabled:pointer-events-none disabled:opacity-50 data-[active]:bg-gray-100/50 data-[state=open]:bg-gray-100/50 dark:bg-gray-950 dark:hover:bg-gray-800 dark:hover:text-gray-50 dark:focus:bg-gray-800 dark:focus:text-gray-50 dark:data-[active]:bg-gray-800/50 dark:data-[state=open]:bg-gray-800/50`}
          href="/profile"
        >
          Profile
        </Link>
        <Sheet>
          <SheetTrigger asChild>
            <Button className="justify-self-end ext-xs">Cart</Button>
          </SheetTrigger>
          <CartContent />
        </Sheet>
      </div>
    </div>
  );
}
