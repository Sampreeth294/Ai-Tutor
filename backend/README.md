# AI Tutor Backend

Spring Boot service that powers the AI Tutor application.

## Dev environment

```powershell
# set Notion token for local dev (Windows PowerShell)
$env:NOTION_API_TOKEN="your_token_here"
```

```bash
# OR (Linux / macOS)
export NOTION_API_TOKEN="your_token_here"
```

```bash
# start the backend
./mvnw spring-boot:run
```

Never commit real tokens to source control. Use your CI/CD provider's secret management for production deployments.

## Testing the `/api/ask` endpoint

```powershell
curl.exe -X POST http://localhost:8080/api/ask -H "Content-Type: application/json" -d '{"question":"What is recursion?"}'
```

Expect a JSON response similar to:

```json
{ "answer": "This is a placeholder answer for: What is recursion?" }
```

When `NOTION_API_TOKEN` is set, the backend logs that the token is present but continues returning the stub answer until the Notion integration is fully implemented.

