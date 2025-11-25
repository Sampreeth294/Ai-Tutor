# AI Tutor Backend

Spring Boot service that powers the AI Tutor application.

## Dev environment

Set the required secrets via environment variables before starting the backend.

```powershell
# Windows PowerShell (temporary)
$env:OPENAI_API_KEY="sk-..."
$env:NOTION_API_TOKEN="secret_notion_token"
$env:NOTION_PARENT_PAGE_ID="your_notion_parent_page_or_database_id"
```

```bash
# Linux / macOS
export OPENAI_API_KEY="sk-..."
export NOTION_API_TOKEN="secret_notion_token"
export NOTION_PARENT_PAGE_ID="your_notion_parent_page_or_database_id"
```

Optional overrides:

- `OPENAI_MODEL` (default `gpt-4o-mini`)
- `OPENAI_TIMEOUT_SECONDS` (default `60`)
- `NOTION_API_VERSION` (default `2022-06-28`)

Start the backend:

```bash
cd backend
./mvnw spring-boot:run
```

Never commit real tokens to source control. Use your CI/CD provider's secret management for production deployments.

## Testing the `/api/ask` endpoint

```powershell
curl.exe -X POST http://localhost:8080/api/ask ^
  -H "Content-Type: application/json" ^
  -d "{\"question\":\"What is recursion?\",\"context\":\"Explain simply\"}"
```

Expect a JSON response similar to:

```json
{
  "answer": "Recursion is ...",
  "source": "openai",
  "raw": null,
  "notionPageId": "some-notion-page-id-or-null"
}
```

If OpenAI or Notion credentials are missing, the service logs a warning and still returns a safe fallback response.

