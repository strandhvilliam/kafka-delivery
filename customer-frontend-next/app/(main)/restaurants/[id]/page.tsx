import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import Image from "next/image";

export default function RestaurantPage() {
  return (
    <section className="py-6" id="specials">
      <div className="container px-4">
        <h2 className="text-xl mb-8">Restaurant 1</h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
          <Card>
            <CardHeader className="">
              <CardTitle>Chicken Alfredo</CardTitle>
            </CardHeader>
            <CardContent>
              <p>
                Creamy and delicious chicken alfredo topped with parmesan
                cheese.
              </p>
              <div className="flex items-center justify-between mt-4">
                <span className="text-lg font-semibold">$20.00</span>
                <Button>Add to Cart</Button>
              </div>
            </CardContent>
          </Card>
          <Card>
            <CardHeader className="">
              <CardTitle>Chicken Alfredo</CardTitle>
            </CardHeader>
            <CardContent>
              <p>
                Creamy and delicious chicken alfredo topped with parmesan
                cheese.
              </p>
              <div className="flex items-center justify-between mt-4">
                <span className="text-lg font-semibold">$20.00</span>
                <Button>Add to Cart</Button>
              </div>
            </CardContent>
          </Card>
          <Card>
            <CardHeader className="">
              <CardTitle>Chicken Alfredo</CardTitle>
            </CardHeader>
            <CardContent>
              <p>
                Creamy and delicious chicken alfredo topped with parmesan
                cheese.
              </p>
              <div className="flex items-center justify-between mt-4">
                <span className="text-lg font-semibold">$20.00</span>
                <Button>Add to Cart</Button>
              </div>
            </CardContent>
          </Card>
          <Card>
            <CardHeader className="">
              <CardTitle>Chicken Alfredo</CardTitle>
            </CardHeader>
            <CardContent>
              <p>
                Creamy and delicious chicken alfredo topped with parmesan
                cheese.
              </p>
              <div className="flex items-center justify-between mt-4">
                <span className="text-lg font-semibold">$20.00</span>
                <Button>Add to Cart</Button>
              </div>
            </CardContent>
          </Card>
        </div>
      </div>
    </section>
  );
}
