type PostGeolocationParams = {
  driverId: string;
  orderId: string;
  latitude: string;
  longitude: string;
};

export type Job = {
  id: string;
  status: string;
};

const API_URL = "http://localhost:48082";

export async function pickupPost(orderId: string) {
  const response = await fetch(`${API_URL}/pickup/${orderId}`, {
    method: "POST",
  });

  if (!response.ok) {
    throw new Error("Network response was not ok");
  }
}

export async function deliverPost(orderId: string) {
  const response = await fetch(`${API_URL}/deliver/${orderId}`, {
    method: "POST",
  });

  if (!response.ok) {
    throw new Error("Network response was not ok");
  }
}

export async function jobsGet(driverId: string): Promise<string[]> {
  const response = await fetch(`${API_URL}/jobs?driverId=${driverId}`);
  return response.json();
}

export async function postGeolocation(params: PostGeolocationParams) {
  const response = await fetch(`${API_URL}/geolocation`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(params),
  });

  if (!response.ok) {
    throw new Error("Network response was not ok");
  }
}
