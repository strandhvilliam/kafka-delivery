import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { createFileRoute } from "@tanstack/react-router";
import { Filter, Heart, Search } from "lucide-react";

export const Route = createFileRoute("/_main/restaurants")({
  component: () => <RestaurantsPage />,
});

const restaurants = [
  {
    id: "1",
    name: "Pizza Hut",
    shortDescription:
      "Pizza Hut is an American restaurant chain and international franchise founded in 1958 in Wichita.",
    imageUrl: "https://source.unsplash.com/random/?pizza",
  },
  {
    id: "2",
    name: "McDonald's",
    shortDescription:
      "McDonald's Corporation is an American fast food company.",
    imageUrl: "https://source.unsplash.com/random/?mcdonalds",
  },
  {
    id: "3",
    name: "KFC",
    shortDescription: "KFC is an American fast food restaurant chain.",
    imageUrl: "https://source.unsplash.com/random/?kfc",
  },
];

const categories = [
  {
    id: "1",
    name: "Pizza",
  },
  {
    id: "2",
    name: "Burgers",
  },
  {
    id: "3",
    name: "Chinese",
  },
  {
    id: "4",
    name: "Indian",
  },
  {
    id: "5",
    name: "Italian",
  },
  {
    id: "6",
    name: "Mexican",
  },
];

export function RestaurantsPage() {
  return (
    <div className="flex min-h-screen w-full flex-col bg-muted overflow-hidden">
      <div className="flex flex-col sm:gap-4 sm:py-4 sm:pl-14">
        <div className="relative mx-4 h-fit  flex-1 md:grow-0">
          <Search className="absolute left-2.5 top-4 h-4 w-4 text-stone-800 " />
          <Input
            type="search"
            placeholder="Search..."
            className="w-full placeholder:text-stone-800 border-stone-800 text-stone-800 h-12 rounded-none bg-muted border-b-2 border-r-0 border-l-0 border-t-0 pl-8 md:w-[200px] lg:w-[336px]"
          />
        </div>
        <div className="flex gap-2 px-4 flex-1 items-center md:grow-0 py-3 h-12 w-full overflow-x-auto">
          <Button
            variant="outline"
            className="w-9 h-9 rounded-full border-2 inline-grid border-stone-800 bg-muted"
          >
            <Filter className="stroke-stone-800 w-5 h-5" />
          </Button>
          {categories.map((category) => (
            <Badge
              key={category.id}
              variant="outline"
              className=" h-9 rounded-lg border-2 border-stone-800 font-bold"
            >
              {category.name}
            </Badge>
          ))}
        </div>
        <main className="grid flex-1 items-start gap-4 px-4 sm:px-6 sm:py-0 md:gap-8 lg:grid-cols-3 xl:grid-cols-3">
          <div className="grid auto-rows-max items-start gap-4 md:gap-8 lg:col-span-2">
            {restaurants.map((restaurant) => (
              <Card
                className="shadow-none border-0  bg-transparent"
                key={restaurant.id}
              >
                <CardHeader className="p-0">
                  <img
                    src={restaurant.imageUrl}
                    alt={restaurant.name}
                    className="w-full h-48 object-cover rounded-2xl"
                  />
                </CardHeader>
                <CardContent className="flex justify-between items-end p-0 border-b-2 pt-4 px-0 text-sm border-b-stone-800">
                  <div className="flex flex-col gap-1 pr-2">
                    <h2 className="text-2xl font-semibold">
                      {restaurant.name}
                    </h2>
                    <p className="text-muted-foreground text-xs pb-2">
                      {restaurant.shortDescription}
                    </p>
                  </div>
                  <Button
                    variant="ghost"
                    size="lg"
                    className="border-l-2 border-l-stone-800 p-4 h-full rounded-none"
                  >
                    <Heart className="stroke-stone-800 w-8 h-8" />
                  </Button>
                </CardContent>
              </Card>
            ))}
          </div>
        </main>
      </div>
    </div>
  );
}
