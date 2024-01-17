import { createContext, useContext, useEffect, useState } from "react";

type Viewport = {
  latitude: number;
  longitude: number;
  zoom: number;
  distance: number;
};

type GeoLocationContextType = {
  viewport: Viewport | null;
};

const GeoLocationContext = createContext<GeoLocationContextType>({
  viewport: null,
});

const sseUrl = `http://localhost:48081/geolocation/sse/user123`;

export function useGeoLocation() {
  const context = useContext(GeoLocationContext);
  if (!context) {
    throw new Error("useGeoLocation must be used within a GeoLocationProvider");
  }
  return context;
}

export function GeoLocationProvider({
  children,
}: {
  children: React.ReactNode;
}) {
  const [viewport, setViewport] = useState<Viewport | null>(null);

  // useEffect(() => {
  //   console.log("Getting location");
  //   navigator.geolocation.getCurrentPosition((pos) => {
  //     setViewport({
  //       latitude: pos.coords.latitude,
  //       longitude: pos.coords.longitude,
  //       zoom: 12.0,
  //       distance: 0,
  //     });
  //   });
  // }, []);

  useEffect(() => {
    console.log("Connecting to SSE");
    const eventSource = new EventSource(sseUrl);
    eventSource.addEventListener("message", (event) => {
      const eventData = JSON.parse(event.data);

      if (eventData.event === "geo-event") {
        const vp: Viewport = {
          latitude: eventData.latitude,
          longitude: eventData.longitude,
          zoom: 14.0,
          distance: eventData.distance,
        };

        console.log("Got event", vp);

        setViewport(vp);
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

  console.log("Viewport", viewport);

  return (
    <GeoLocationContext.Provider value={{ viewport: viewport }}>
      {children}
    </GeoLocationContext.Provider>
  );
}
