import { useGeoLocation } from "@/store/geolocation-provider";
import Map, { GeolocateControl, Marker, NavigationControl } from "react-map-gl";
import "mapbox-gl/dist/mapbox-gl.css";

const mapboxToken =
  "pk.eyJ1Ijoic3RyYW5kaHZpbGxpYW0iLCJhIjoiY2xvMGlnYmxtMGc0aDJtcXVhMW03ZmF6byJ9.5rev_MbPFGQC-pBCnKwcgQ";

export default function DriverMap() {
  const { viewport } = useGeoLocation();

  return (
    <main className="max-w-full h-[18rem] flex">
      {viewport !== null && (
        <Map
          {...viewport}
          style={{ width: "100%", height: "100%" }}
          mapboxAccessToken={mapboxToken}
          mapStyle="mapbox://styles/mapbox/streets-v12"
          initialViewState={viewport}
          maxZoom={20}
          minZoom={3}
        >
          <Marker latitude={viewport.latitude} longitude={viewport.longitude} />
          <GeolocateControl position="top-left" />
          <NavigationControl position="top-left" />
        </Map>
      )}
    </main>
  );
}
