"use client";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import Link from "next/link";
import { usePathname } from "next/navigation";

export default function TabsLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const pathname = usePathname();

  return (
    <Tabs
      value={pathname.includes("/restaurant") ? "restaurant" : "driver"}
      className="w-[48rem]"
    >
      <TabsList className="grid w-fit grid-cols-2">
        <Link href={"/restaurant"}>
          <TabsTrigger value="restaurant">Restaurant</TabsTrigger>
        </Link>
        <Link href={"/driver"}>
          <TabsTrigger className="w-full" value="driver">
            Driver
          </TabsTrigger>
        </Link>
      </TabsList>
      <div className="py-2">{children}</div>
    </Tabs>
  );
}
