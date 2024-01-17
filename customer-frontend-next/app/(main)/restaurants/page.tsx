import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import {
  DropdownMenuTrigger,
  DropdownMenuRadioItem,
  DropdownMenuRadioGroup,
  DropdownMenuContent,
  DropdownMenu,
} from "@/components/ui/dropdown-menu";
import {
  CardTitle,
  CardDescription,
  CardHeader,
  CardContent,
  Card,
} from "@/components/ui/card";

export default function Component() {
  return (
    <div className="container w-full py-6 px-4">
      <div className="flex items-center justify-between mb-6">
        <Input
          className="w-full md:w-1/2 lg:w-1/3"
          placeholder="Search restaurants..."
        />
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button className="ml-4" variant="outline">
              Sort by
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end" className="w-[200px]">
            <DropdownMenuRadioGroup value="rating">
              <DropdownMenuRadioItem value="rating">
                Rating
              </DropdownMenuRadioItem>
              <DropdownMenuRadioItem value="alphabetical">
                Alphabetical
              </DropdownMenuRadioItem>
            </DropdownMenuRadioGroup>
          </DropdownMenuContent>
        </DropdownMenu>
      </div>
      <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
        <Card className="flex flex-col">
          <CardHeader>
            <CardTitle>Restaurant 1</CardTitle>
            <CardDescription>Italian</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="flex items-center mb-2">
              <PizzaIcon className="h-6 w-6 mr-2" />
              <span>123 Main St, City, State</span>
            </div>
            <div className="flex items-center mb-4">
              <StarIcon className="h-4 w-4 fill-primary mr-2" />
              <span>4.5</span>
            </div>
            <Button className="mt-auto" variant="outline">
              View Details
            </Button>
          </CardContent>
        </Card>
        <Card className="flex flex-col">
          <CardHeader>
            <CardTitle>Restaurant 2</CardTitle>
            <CardDescription>Japanese</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="flex items-center mb-2">
              <FishIcon className="h-6 w-6 mr-2" />
              <span>456 Oak St, City, State</span>
            </div>
            <div className="flex items-center mb-4">
              <StarIcon className="h-4 w-4 fill-primary mr-2" />
              <span>4.7</span>
            </div>
            <Button className="mt-auto" variant="outline">
              View Details
            </Button>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}

function FishIcon(props: any) {
  return (
    <svg
      {...props}
      xmlns="http://www.w3.org/2000/svg"
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
    >
      <path d="M6.5 12c.94-3.46 4.94-6 8.5-6 3.56 0 6.06 2.54 7 6-.94 3.47-3.44 6-7 6s-7.56-2.53-8.5-6Z" />
      <path d="M18 12v.5" />
      <path d="M16 17.93a9.77 9.77 0 0 1 0-11.86" />
      <path d="M7 10.67C7 8 5.58 5.97 2.73 5.5c-1 1.5-1 5 .23 6.5-1.24 1.5-1.24 5-.23 6.5C5.58 18.03 7 16 7 13.33" />
      <path d="M10.46 7.26C10.2 5.88 9.17 4.24 8 3h5.8a2 2 0 0 1 1.98 1.67l.23 1.4" />
      <path d="m16.01 17.93-.23 1.4A2 2 0 0 1 13.8 21H9.5a5.96 5.96 0 0 0 1.49-3.98" />
    </svg>
  );
}

function PizzaIcon(props: any) {
  return (
    <svg
      {...props}
      xmlns="http://www.w3.org/2000/svg"
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
    >
      <path d="M15 11h.01" />
      <path d="M11 15h.01" />
      <path d="M16 16h.01" />
      <path d="m2 16 20 6-6-20A20 20 0 0 0 2 16" />
      <path d="M5.71 17.11a17.04 17.04 0 0 1 11.4-11.4" />
    </svg>
  );
}

function StarIcon(props: any) {
  return (
    <svg
      {...props}
      xmlns="http://www.w3.org/2000/svg"
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
    >
      <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2" />
    </svg>
  );
}
