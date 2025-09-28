@echo off
REM ============================================================================
REM Docker Image Build Script for Patient Management Microservices
REM ============================================================================

echo.
echo ============================================================================
echo Building Docker Images for Patient Management System
echo ============================================================================
echo.

REM Set error handling
setlocal enabledelayedexpansion

REM Define image tags and versions
set VERSION=latest

REM Color codes for output
set GREEN=[32m
set RED=[31m
set YELLOW=[33m
set NC=[0m

echo %YELLOW%Building Patient Service...%NC%
cd ..\patient-service
docker build -t patient-service:%VERSION% .
if !errorlevel! neq 0 (
    echo %RED%ERROR: Failed to build patient-service image%NC%
    exit /b 1
) else (
    echo %GREEN%SUCCESS: patient-service image built successfully%NC%
)
echo.

echo %YELLOW%Building Analytics Service...%NC%
cd ..\analytics-service
docker build -t analytics-service:%VERSION% .
if !errorlevel! neq 0 (
    echo %RED%ERROR: Failed to build analytics-service image%NC%
    exit /b 1
) else (
    echo %GREEN%SUCCESS: analytics-service image built successfully%NC%
)
echo.

echo %YELLOW%Building Billing Service...%NC%
cd ..\billing-service
docker build -t billing-service:%VERSION% .
if !errorlevel! neq 0 (
    echo %RED%ERROR: Failed to build billing-service image%NC%
    exit /b 1
) else (
    echo %GREEN%SUCCESS: billing-service image built successfully%NC%
)
echo.

echo %YELLOW%Building API Gateway...%NC%
cd ..\api-gateway
docker build -t api-gateway:%VERSION% .
if !errorlevel! neq 0 (
    echo %RED%ERROR: Failed to build api-gateway image%NC%
    exit /b 1
) else (
    echo %GREEN%SUCCESS: api-gateway image built successfully%NC%
)
echo.

REM Return to original directory
cd ..\docker

echo ============================================================================
echo %GREEN%All Docker images built successfully!%NC%
echo ============================================================================
echo.

REM List all built images
echo %YELLOW%Built Images:%NC%
docker images | findstr "patient-service\|analytics-service\|billing-service\|api-gateway"
echo.

REM Optional: Tag images with additional tags
echo %YELLOW%Creating additional tags...%NC%
docker tag patient-service:%VERSION% patient-service:v1.0.0
docker tag analytics-service:%VERSION% analytics-service:v1.0.0
docker tag billing-service:%VERSION% billing-service:v1.0.0
docker tag api-gateway:%VERSION% api-gateway:v1.0.0

echo %GREEN%Additional version tags created (v1.0.0)%NC%
echo.

REM Clean up any dangling images
echo %YELLOW%Cleaning up dangling images...%NC%
docker image prune -f

echo ============================================================================
echo %GREEN%Build process completed successfully!%NC%
echo ============================================================================
echo.
echo %YELLOW%Next Steps:%NC%
echo 1. Start support services: docker-compose -f 01-docker-compose-support.yml up -d
echo 2. Access API Gateway at: http://localhost:4004
echo.

REM Optional: Show disk usage
echo %YELLOW%Docker disk usage:%NC%
docker system df

pause
