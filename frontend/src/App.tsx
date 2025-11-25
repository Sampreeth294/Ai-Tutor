import { FormEvent, useState } from "react";
import { getHealthStatus, postAsk } from "./services/api";


function App() {
  const [health, setHealth] = useState<string>("");
  const [question, setQuestion] = useState("");
  const [context, setContext] = useState("");
  const [answer, setAnswer] = useState("");
  const [notionPageId, setNotionPageId] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleCheckBackend = async () => {
    try {
      const response = await getHealthStatus();
      setHealth(response.data.status);   // Correct usage
    } catch (error) {
      console.error("Error fetching health status:", error);
      setHealth("Error connecting to backend");
    }
  };

  const handleAsk = async (event: FormEvent) => {
    event.preventDefault();
    if (!question.trim()) {
      setError("Please enter a question.");
      return;
    }

    setLoading(true);
    setError("");
    setAnswer("");
    setNotionPageId("");

    try {
      const response = await postAsk({ question, context });
      setAnswer(response.data.answer);
      setNotionPageId(response.data.notionPageId || "");
    } catch (err) {
      console.error("Error asking tutor:", err);
      setError("Error asking tutor.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>AI Tutor</h1>

      <section style={{ marginBottom: "24px" }}>
        <h2>Backend Health</h2>
        <button onClick={handleCheckBackend}>Check Backend</button>
        {health && (
          <div style={{ marginTop: "12px" }}>
            <strong>Backend Response:</strong>
            <p>Status: {health}</p>
          </div>
        )}
      </section>

      <section>
        <h2>Ask the Tutor</h2>
        <form onSubmit={handleAsk} style={{ display: "flex", flexDirection: "column", gap: "12px", maxWidth: "480px" }}>
          <label>
            Question
            <textarea
              value={question}
              onChange={(event) => setQuestion(event.target.value)}
              rows={4}
              style={{ width: "100%", marginTop: "4px" }}
            />
          </label>
          <label>
            Context (optional)
            <textarea
              value={context}
              onChange={(event) => setContext(event.target.value)}
              rows={3}
              style={{ width: "100%", marginTop: "4px" }}
            />
          </label>
          <button type="submit" disabled={loading}>
            {loading ? "Generating..." : "Ask"}
          </button>
        </form>

        {error && <p style={{ color: "red", marginTop: "12px" }}>{error}</p>}

        {answer && (
          <div style={{ marginTop: "20px" }}>
            <h3>Answer</h3>
            <p>{answer}</p>
            {notionPageId && (
              <p style={{ fontSize: "0.9rem", color: "#555" }}>
                Saved to Notion. Page ID: {notionPageId}
              </p>
            )}
          </div>
        )}
      </section>
    </div>
  );
}

export default App;
