import Link from "next/link";
import { Button } from "./ui/button";
import Nav from "./nav";

export default function Header() {
  return (
    <div className="container mx-auto px-4 md:px-6 lg:px-8">
      <header className="flex h-20 w-full shrink-0 items-center px-4 md:px-6">
        <Nav />
      </header>
    </div>
  );
}
