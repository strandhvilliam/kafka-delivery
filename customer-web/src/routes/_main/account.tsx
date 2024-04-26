import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { createFileRoute } from "@tanstack/react-router";
import { Edit, Heart, ShoppingBag } from "lucide-react";

export const Route = createFileRoute("/_main/account")({
  component: () => <AccountPage />,
});

function AccountPage() {
  return (
    <div className="flex min-h-screen w-full flex-col bg-muted overflow-hidden">
      <div className="flex flex-col sm:gap-4 sm:py-4 sm:pl-14">
        <main className="grid flex-1 items-start gap-4 px-4 sm:px-6 sm:py-0 md:gap-8 lg:grid-cols-3 xl:grid-cols-3">
          <div className="flex gap-6 py-8">
            <Card className="flex rounded-2xl py-8 items-center justify-center flex-col gap-2 w-full">
              <Heart className="h-8 w-8 text-primary" />
              <h1 className="text-lg font-semibold">Favorites</h1>
            </Card>
            <Card className="flex rounded-2xl py-8 items-center justify-center flex-col gap-2 w-full">
              <ShoppingBag className="h-8 w-8 text-primary" />
              <h1 className="text-lg font-semibold">History</h1>
            </Card>
          </div>
          <Card className="flex flex-col gap-4 p-8  text-stone-800 rounded-2xl">
            <h3 className="text-xl font-semibold border-b-2 border-stone-800 pb-2 text-stone-800">
              Name LastName
            </h3>
            <div className="flex gap-2 items-center pb-4 justify-between text-sm border-b-2 border-stone-800">
              <div className="flex flex-col gap-2">
                <span className="text-stone-800">Street address 4</span>
                <span className="text-stone-800">12345, ZipArea</span>
              </div>
              <Button variant="outline" className="border-0 bg-transparent ">
                <Edit className="stroke-stone-800 w-5 h-5" />
              </Button>
            </div>
            <div className="flex gap-2 items-center justify-between text-sm">
              <div className="flex flex-col gap-2">
                <span className="text-stone-800">mymail@mymail.com</span>
                <span className="text-stone-800">+46123456</span>
              </div>
              <Button variant="outline" className="border-0 bg-transparent ">
                <Edit className="stroke-stone-800 w-5 h-5" />
              </Button>
            </div>
          </Card>
          <Accordion type="single" collapsible className="w-full text-sm py-8">
            <AccordionItem value="support" className="px-4">
              <AccordionTrigger>Is it accessible?</AccordionTrigger>
              <AccordionContent>
                Lorem ipsum dolor sit amet, qui minim labore adipisicing minim
                sint cillum sint consectetur cupidatat.
              </AccordionContent>
            </AccordionItem>
            <AccordionItem value="terms-of-use" className="px-4">
              <AccordionTrigger>Terms of use</AccordionTrigger>
              <AccordionContent>
                laboris cupidatat officia voluptate. Culpa proident adipisicing
                id nulla nisi laboris ex in Lorem sunt duis officia eiusmod.
                Aliqua reprehenderit commodo ex non excepteur duis sunt velit
                enim. Voluptate laboris sint cupidatat ullamco ut ea consectetur
                et est culpa et culpa duis.
              </AccordionContent>
            </AccordionItem>
          </Accordion>
        </main>
      </div>
    </div>
  );
}

