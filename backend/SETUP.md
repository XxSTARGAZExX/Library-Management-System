# Backend Setup Instructions

## Prerequisites
- Java 17 or higher
- Maven 3.8.0 or higher
- MySQL 5.7 or higher

## Setup Steps

### 1. Database Configuration
```sql
mysql -u root -p
CREATE DATABASE library_management_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EXIT;
```

### 2. Update application.properties
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_management_system
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Build the Application
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080/api`

## API Testing

Use Postman or curl to test endpoints:

```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe",
    "phone": "1234567890"
  }'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "user@example.com", "password": "password123"}'
```

## Troubleshooting

### Port 8080 already in use
Change port in `application.properties`:
```properties
server.port=8081
```

### Database connection error
- Verify MySQL is running
- Check credentials in `application.properties`
- Ensure database exists

### Build errors
Clear cache and rebuild:
```bash
mvn clean
mvn install
```
