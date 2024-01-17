import Nav from "@/components/nav";
import Providers from "@/lib/context/providers";

export default function MainLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <>
      <div className="container mx-auto px-4 md:px-6 lg:px-8">
        <Providers>
          <header className="flex h-20 w-full shrink-0 items-center px-4">
            <Nav />
          </header>
          {children}
        </Providers>
      </div>
    </>
  );
}
