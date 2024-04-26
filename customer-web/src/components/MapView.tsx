import { LegacyRef, useRef, useState } from "react";
import Map, {
  GeolocateControl,
  MapRef,
  Marker,
  AttributionControl,
  MarkerProps,
} from "react-map-gl";
import { Card } from "./ui/card";
import "mapbox-gl/dist/mapbox-gl.css";

const publicToken =
  "pk.eyJ1Ijoic3RyYW5kaHZpbGxpYW0iLCJhIjoiY2xvMGlnYmxtMGc0aDJtcXVhMW03ZmF6byJ9.5rev_MbPFGQC-pBCnKwcgQ";

export default function MapView() {
  const [marker, setMarker] = useState<MarkerProps | null>(null);
  const mapRef = useRef<MapRef>();

  return (
    <Card className="h-[200px] w-full overflow-hidden border-stone-800 border-2">
      <Map
        id="map"
        style={{
          width: "100%",
          height: "100%",
          display: "flex",
          position: "relative",
          flexDirection: "column",
          overflow: "hidden",
        }}
        ref={mapRef as LegacyRef<MapRef>}
        initialViewState={{
          latitude: 62.3866,
          longitude: 16.3213,
          zoom: 16,
        }}
        maxZoom={22}
        attributionControl={false}
        mapboxAccessToken={publicToken}
        mapStyle="mapbox://styles/mapbox/streets-v12"
      >
        {marker && (
          <Marker {...marker} style={{ cursor: "none !important" }}></Marker>
        )}
        <GeolocateControl
          positionOptions={{ enableHighAccuracy: true }}
          trackUserLocation={true}
          onGeolocate={(position) => {
            mapRef.current?.flyTo({
              center: [position.coords.longitude, position.coords.latitude],
              zoom: 16,
            });
          }}
        />
        <AttributionControl style={{ position: "absolute", top: "0" }} />
      </Map>
    </Card>
  );
}
