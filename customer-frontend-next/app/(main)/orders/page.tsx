import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import MapComponent from "@/components/map";

const fakeProducts = [
  {
    id: 1,
    name: "Product 1",
    price: 100,
  },
  {
    id: 2,
    name: "Product 2",
    price: 200,
  },
  {
    id: 3,
    name: "Product 3",
    price: 300,
  },
  {
    id: 4,
    name: "Product 4",
    price: 400,
  },
];

export default function OrdersPage() {
  return (
    <div className="container gap-4 w-full flex flex-col py-6 px-4">
      <Card>
        <CardHeader>
          <CardTitle>Your order</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="flex flex-col gap-4">
            <div className="flex flex-row justify-between">
              <div className="flex flex-row">
                <div className="flex flex-col ">
                  <span className="text-zinc-700">Status:</span>
                </div>
              </div>
              <div className="flex flex-col">
                <span className="text-zinc-700 text-right">On route!</span>
              </div>
            </div>
            <div className="w-full h-[400px] rounded-lg border border-zinc-800 overflow-hidden">
              <MapComponent />
            </div>
          </div>
        </CardContent>
      </Card>
      {/* <Card> */}
      {/*   <CardHeader> */}
      {/*     <CardTitle>Products ordered</CardTitle> */}
      {/*   </CardHeader> */}
      {/*   <CardContent> */}
      {/*     {fakeProducts.map((product) => ( */}
      {/*       <div className="flex flex-col" key={product.id}> */}
      {/*         <div className="flex flex-row justify-between"> */}
      {/*           <div className="flex flex-row"> */}
      {/*             <div className="flex flex-col "> */}
      {/*               <span className="text-zinc-700">{product.name}</span> */}
      {/*             </div> */}
      {/*           </div> */}
      {/*           <div className="flex flex-col"> */}
      {/*             <span className="text-zinc-700 text-right"> */}
      {/*               ${product.price} */}
      {/*             </span> */}
      {/*           </div> */}
      {/*         </div> */}
      {/*         <hr className="my-2" /> */}
      {/*       </div> */}
      {/*     ))} */}
      {/*     <div className="flex flex-col"> */}
      {/*       <div className="flex flex-row justify-between"> */}
      {/*         <div className="flex flex-row"> */}
      {/*           <div className="flex flex-col "> */}
      {/*             <span className="text-zinc-700">Total</span> */}
      {/*           </div> */}
      {/*         </div> */}
      {/*         <div className="flex flex-col"> */}
      {/*           <span className="text-zinc-700 font-bold text-right"> */}
      {/*             $1000 */}
      {/*           </span> */}
      {/*         </div> */}
      {/*       </div> */}
      {/*     </div> */}
      {/*   </CardContent> */}
      {/* </Card> */}
    </div>
  );
}
