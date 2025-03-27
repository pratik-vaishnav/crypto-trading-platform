# Crypto Trading Platform

A high-performance cryptocurrency trading platform built with Java and Spring Boot.

## Project Overview
This platform provides a scalable and reliable cryptocurrency trading system with the following features:
- Real-time market data processing
- High-performance trading engine
- Secure user authentication and authorization
- Order management system
- Time-series market data storage

## Technical Stack
- Java 17
- Spring Boot 3.x
- Spring Security
- Netty.io
- PostgreSQL & TimescaleDB
- Redis
- Apache Kafka
- Docker

## Project Structure
```
crypto-trading-platform/
├── auth-service/        # Authentication and authorization
├── trading-engine/      # Core trading engine using Netty.io
├── market-data/         # Market data processing
├── user-service/        # User management
├── order-service/       # Order management
└── common/             # Shared utilities and models
```

## Prerequisites
- Java 17 or higher
- Maven 3.8+
- Docker and Docker Compose
- PostgreSQL 14+
- Redis 6+
- Apache Kafka

## Getting Started
1. Clone the repository
2. Set up the required environment variables
3. Run `docker-compose up` to start dependent services
4. Build and run each service using Maven

## Development Setup
```bash
# Build all services
./mvnw clean install

# Run individual services
cd auth-service
./mvnw spring-boot:run
```

## API Documentation
API documentation is available through Swagger UI at `/swagger-ui.html` when each service is running.

## Contributing
Please read CONTRIBUTING.md for details on our code of conduct and the process for submitting pull requests.
