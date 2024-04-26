import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Separator } from "@/components/ui/separator";
import { createFileRoute } from "@tanstack/react-router";
import { PlusSquare } from "lucide-react";

export const Route = createFileRoute("/_sub/restaurant/$slug")({
  component: () => <RestaurantPage />,
});

const restaurant = {
  name: "Pizza Place",
  description: "Pizza Place description goes here.",
  imageUrl: "https://source.unsplash.com/random/?restaurant",
  tags: ["Pizza", "Italian", "Winner 2023"],
  products: [
    {
      id: "1",
      name: "Pizza nomero uno",
      description: "Pizza description goes here.",
    },
    {
      id: "2",
      name: "Pizza nomero dos",
      description: "Pizza description goes here.",
    },
  ],
};

function RestaurantPage() {
  return (
    <div className="h-full">
      <div className="relative flex items-end w-full h-[500px] overflow-hidden">
        <div className="absolute top-0 left-0 z-20  h-full w-full bg-stone-900/50" />
        <img
          className="absolute top-0 left-0 w-full h-full object-cover"
          src={restaurant.imageUrl}
        />
        <div className="z-30 px-8 w-full flex flex-col gap-2 -translate-y-12">
          <h1 className="text-white text-3xl font-bold">{restaurant.name}</h1>
          <Separator className="fill-white border-b-2" />
          <p className="text-white text-sm">{restaurant.description}</p>
          <div className="flex gap-4 pt-2">
            {restaurant.tags.map((tag, index) => (
              <div className="flex gap-4  items-center">
                <div key={tag} className="text-sm text-white">
                  {tag}
                </div>
                {index === restaurant.tags.length - 1 ? null : (
                  <Separator
                    orientation="vertical"
                    className="h-6 border-r-2 fill-white"
                  />
                )}
              </div>
            ))}
          </div>
        </div>
      </div>
      <div className="relative">
        <Card className=" absolute w-full -top-8 z-20 rounded-2xl flex flex-col gap-4 p-4">
          {restaurant.products.map((product) => (
            <div className="p-4 flex">
              <div key={product.id} className="flex flex-col ">
                <h2 className="text-lg font-semibold">{product.name}</h2>
                <p className="text-xs text-stone-800">{product.description}</p>
              </div>
              <Button variant="ghost" className="ml-auto">
                <PlusSquare />
              </Button>
            </div>
          ))}
        </Card>
      </div>
    </div>
  );
}
