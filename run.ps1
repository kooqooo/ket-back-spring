# .env 파일 경로
$envFile = ".env"

# .env 파일이 없으면 실행 중단
if (-not (Test-Path $envFile)) {
    Write-Error ".env file not found. Copy .env.example to .env."
    exit 1
}

# .env 파일의 key=value 항목을 현재 PowerShell 프로세스 환경변수로 등록
Get-Content $envFile | ForEach-Object {
    # 주석과 빈 줄은 무시
    if ($_ -match '^\s*#') { return }
    if ($_ -match '^\s*$') { return }

    $parts = $_ -split '=', 2

    if ($parts.Length -eq 2) {
        $name = $parts[0].Trim()
        $value = $parts[1].Trim()

        [System.Environment]::SetEnvironmentVariable($name, $value, "Process")
    }
}

# Docker Compose로 PostgreSQL, Redis, MinIO 실행
docker compose --env-file .env up -d

# Spring Boot 애플리케이션 실행
.\gradlew.bat bootRun