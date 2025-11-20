# How to Start Backend in VS Code

## Method 1: Using Run Button (Easiest) ✅

1. Open the file: `backend/src/main/java/com/ai/tutor/DemoApplication.java`
2. Look for the **green "Run" button** (▶️) above the `main` method
3. Click it, or press **F5**
4. Wait for "Started DemoApplication" message

## Method 2: Using VS Code Terminal

1. Open Terminal: Press `Ctrl + ` (backtick) or go to `Terminal` → `New Terminal`
2. Navigate to backend:
   ```powershell
   cd backend
   ```
3. Set JAVA_HOME (if not set permanently):
   ```powershell
   $env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot"
   ```
4. Run the application:
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

## Method 3: Using Command Palette

1. Press `Ctrl + Shift + P`
2. Type: `Java: Run Java`
3. Select your `DemoApplication.java` file

## Verify It's Running

Once you see "Started DemoApplication" in the console:
- Open browser: http://localhost:8080/api/health
- You should see: **OK**

## Troubleshooting

### If JAVA_HOME error appears:
Set it in the terminal:
```powershell
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot"
```

### If port 8080 is already in use:
Change port in `application.properties`:
```
server.port=8081
```

