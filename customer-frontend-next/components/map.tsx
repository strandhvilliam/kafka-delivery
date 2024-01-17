"use client";

import Map from "react-map-gl";
import styles from "./styles.module.css";

import GeolocateControl from "react-map-gl/dist/esm/components/geolocate-control";
import NavigationControl from "react-map-gl/dist/esm/components/navigation-control";

export default function MapComponent() {
  const mapboxToken =
    "pk.eyJ1Ijoic3RyYW5kaHZpbGxpYW0iLCJhIjoiY2xvMGlnYmxtMGc0aDJtcXVhMW03ZmF6byJ9.5rev_MbPFGQC-pBCnKwcgQ";

  return (
    <main className={styles.mainStyle}>
      <Map
        mapboxAccessToken={mapboxToken}
        mapStyle="mapbox://styles/mapbox/streets-v12"
        initialViewState={{
          latitude: 35.668641,
          longitude: 139.750567,
          zoom: 10,
        }}
        maxZoom={20}
        minZoom={3}
      >
        <GeolocateControl position="top-left" />
        <NavigationControl position="top-left" />
      </Map>
    </main>
  );
}
