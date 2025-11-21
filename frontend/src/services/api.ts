import axios from "axios";

export interface HealthResponse {
  status: string;
}

// Use relative URL in development (via Vite proxy) or absolute URL in production
const baseURL = import.meta.env.DEV 
  ? "" // Use Vite proxy in development
  : import.meta.env.VITE_API_URL || "http://localhost:8080"; // Use env variable or default in production

const api = axios.create({
  baseURL,
  headers: {
    "Content-Type": "application/json",
  },
});

export const getHealthStatus = async () => {
  return api.get<HealthResponse>("/api/health");
};

export default api;
