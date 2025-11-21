import { useState } from "react";
import { getHealthStatus } from "./services/api";


function App() {
  const [health, setHealth] = useState<string>("");

  const handleCheckBackend = async () => {
    try {
      const response = await getHealthStatus();
      setHealth(response.data.status);   // Correct usage
    } catch (error) {
      console.error("Error fetching health status:", error);
      setHealth("Error connecting to backend");
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>React + Spring Boot Test</h1>

      <button onClick={handleCheckBackend}>Check Backend</button>

      {health && (
        <div style={{ marginTop: "20px" }}>
          <strong>Backend Response:</strong>
          <p>Status: {health}</p>
        </div>
      )}
    </div>
  );
}

export default App;
