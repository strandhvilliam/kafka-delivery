import { useEffect, useState } from "react";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "./components/ui/card";

type LogEvent = {
  source: string;
  message: string;
  level: string;
  timestamp: string;
};

function App() {
  const [data, setData] = useState<LogEvent[]>([]);

  useEffect(() => {
    const eventSource = new EventSource("http://localhost:48091/subscribe");
    eventSource.addEventListener("message", (event) => {
      try {
        const eventData = JSON.parse(event.data);
        setData((data) => [...data, eventData]);
      } catch (e) {
        console.error("Error parsing event data:", e);
        return;
      }
    });

    eventSource.addEventListener("error", (error) => {
      console.error("Error with SSE connection:", error);
      eventSource.close();
    });

    return () => {
      eventSource.close();
    };
  }, []);

  return (
    <main className="min-h-screen w-full flex ">
      <div className="container flex flex-col gap-2 rounded-md  mx-auto w-10/12 mt-12">
        <div className="flex flex-col gap-4 mb-4">
          <h1 className="text-4xl font-bold">Logging Demo</h1>
        </div>
        {data.reverse().map((event, index) => (
          <Card key={index}>
            <CardHeader>
              <CardTitle>{`[${event.level}]  ${event.source}`}</CardTitle>
              <CardDescription>{event.timestamp}</CardDescription>
            </CardHeader>
            <CardContent>{event.message}</CardContent>
          </Card>
        ))}
      </div>
    </main>
  );
}

export default App;
