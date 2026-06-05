SHELL := /bin/bash

ENV_FILE ?= .env
COMPOSE_FILE ?= docker-compose.yaml
COMPOSE = docker compose --env-file $(ENV_FILE) -f $(COMPOSE_FILE)

.PHONY: help env-check up down clean restart reset ps logs postgres redis minio run dev test check build secret

help:
	@echo "사용 가능한 명령어:"
	@echo "  make env-check   필수 환경변수 확인"
	@echo "  make up          컨테이너 실행"
	@echo "  make down        컨테이너 중지"
	@echo "  make clean       컨테이너 중지 및 볼륨 삭제"
	@echo "  make restart     컨테이너 재시작"
	@echo "  make reset       DEBUG=true일 때만 볼륨 삭제 후 컨테이너 재실행"
	@echo "  make ps          컨테이너 상태 확인"
	@echo "  make logs        컨테이너 로그 확인"
	@echo "  make postgres    PostgreSQL 셸 접속"
	@echo "  make redis       Redis CLI 접속"
	@echo "  make minio       MinIO 접속 URL 출력"
	@echo "  make run         Spring Boot 애플리케이션 실행"
	@echo "  make dev         local 프로필로 Spring Boot 애플리케이션 실행"
	@echo "  make test        테스트 실행"
	@echo "  make check       테스트 및 빌드 검사 실행"
	@echo "  make build       애플리케이션 빌드"
	@echo "  make secret      랜덤 시크릿 생성"

env-check:
	@test -f $(ENV_FILE) || (echo "$(ENV_FILE) file not found. Copy .env.example to $(ENV_FILE)." && exit 1)
	@set -a; source $(ENV_FILE); set +a; \
	for var in \
		DEBUG \
		POSTGRES_PORT POSTGRES_USER POSTGRES_PASSWORD POSTGRES_DB \
		REDIS_PORT REDIS_PASSWORD \
		MINIO_ROOT_USER MINIO_ROOT_PASSWORD MINIO_API_PORT MINIO_CONSOLE_PORT MINIO_ENDPOINT_URL MINIO_BUCKET MINIO_BROWSER_REDIRECT_URL; \
	do \
		if [ -z "$${!var}" ]; then \
			echo "Missing required environment variable: $$var"; \
			exit 1; \
		fi; \
	done; \
	echo "Environment variables are valid."

up: env-check
	$(COMPOSE) up -d

down:
	$(COMPOSE) down

clean:
	$(COMPOSE) down --volumes

restart: down up

reset:
	@test -f $(ENV_FILE) || (echo "$(ENV_FILE) file not found. reset is allowed only when DEBUG=true in $(ENV_FILE)." && exit 1)
	@debug_val=$$(awk -F= '/^[[:space:]]*DEBUG[[:space:]]*=/{val=$$2; gsub(/[[:space:]"\r]/, "", val); print val; exit}' $(ENV_FILE)); \
	if [ "$$debug_val" != "true" ]; then \
		echo "reset is blocked. Set DEBUG=true in $(ENV_FILE) to allow this command."; \
		exit 1; \
	fi
	@echo "WARNING: Docker volumes will be removed. PostgreSQL, Redis, and MinIO data will be deleted."
	@printf "Continue? [y/N] "
	@read yn; \
	yn=$${yn:-n}; \
	case "$$yn" in \
		y|Y) ;; \
		*) echo "Canceled."; exit 1 ;; \
	esac
	$(MAKE) clean
	$(MAKE) up

ps:
	$(COMPOSE) ps

logs:
	$(COMPOSE) logs -f

postgres:
	@set -a; source $(ENV_FILE); set +a; \
	docker exec -it pg psql -U $$POSTGRES_USER -d $$POSTGRES_DB

redis:
	@set -a; source $(ENV_FILE); set +a; \
	docker exec -it redis redis-cli -a $$REDIS_PASSWORD

minio:
	@set -a; source $(ENV_FILE); set +a; \
	echo "MinIO API:     http://localhost:$$MINIO_API_PORT"; \
	echo "MinIO Console: http://localhost:$$MINIO_CONSOLE_PORT"

run:
	set -a; source $(ENV_FILE); set +a; ./gradlew bootRun

dev:
	set -a; source $(ENV_FILE); set +a; ./gradlew bootRun --args='--spring.profiles.active=local'

test:
	./gradlew test

check:
	./gradlew clean test

build:
	./gradlew clean build

secret:
	openssl rand -hex 32
