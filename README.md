# Library Management System

A comprehensive web-based Library Management System built with **Spring Boot** backend and **React** frontend.

## Features

### Core Features
- ✅ **User Authentication & Authorization** - Login/Register with JWT-based authentication
- ✅ **Book Management** - Add, edit, delete, and search books
- ✅ **Member Management** - Manage library members and their profiles
- ✅ **Book Borrowing** - Track book borrowing and returns
- ✅ **Fine Management** - Calculate and manage overdue fines
- ✅ **Dashboard & Analytics** - View comprehensive statistics and reports
- ✅ **Search & Filters** - Advanced search capabilities for books and members

### Technology Stack

**Backend:**
- Spring Boot 3.1.5
- Spring Security with JWT
- JPA/Hibernate
- MySQL Database
- Maven

**Frontend:**
- React 18
- React Router for navigation
- Axios for API calls
- Vite for build tooling

## Getting Started

### Prerequisites
- Java 17+
- Node.js 16+
- MySQL 5.7+
- Git

### Backend Setup

1. **Clone the repository**
```bash
cd library-management-system/backend
```

2. **Configure Database**
   - Open `src/main/resources/application.properties`
   - Update database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/library_management_system
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```

3. **Create Database**
```bash
mysql -u root -p
CREATE DATABASE library_management_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EXIT;
```

4. **Build and Run**
```bash
mvn clean install
mvn spring-boot:run
```

Backend will start at: `http://localhost:8080/api`

### Frontend Setup

1. **Install Dependencies**
```bash
cd library-management-system/frontend
npm install
```

2. **Run Development Server**
```bash
npm run dev
```

Frontend will be available at: `http://localhost:3000`

## API Endpoints

### Authentication
- `POST /auth/register` - Register new user
- `POST /auth/login` - Login user
- `GET /auth/user/{userId}` - Get user details

### Books
- `GET /books` - Get all books
- `GET /books?title=search` - Search books by title
- `GET /books/{id}` - Get book details
- `POST /books` - Create book (Admin/Librarian)
- `PUT /books/{id}` - Update book (Admin/Librarian)
- `DELETE /books/{id}` - Delete book (Admin/Librarian)

### Members
- `GET /members` - Get all members
- `GET /members/{id}` - Get member details
- `POST /members/{userId}` - Create member profile
- `PUT /members/{id}/suspend` - Suspend member (Admin/Librarian)
- `PUT /members/{id}/activate` - Activate member (Admin/Librarian)

### Borrowing
- `POST /borrowing/borrow?memberId=X&bookId=Y` - Borrow a book
- `POST /borrowing/return/{transactionId}` - Return a book
- `GET /borrowing` - Get all transactions
- `GET /borrowing/member/{memberId}` - Get member transactions
- `GET /borrowing/overdue` - Get overdue transactions (Admin/Librarian)

### Fines
- `POST /fines/calculate/{transactionId}` - Calculate fine
- `PUT /fines/{id}/pay` - Pay a fine
- `GET /fines/{id}` - Get fine details
- `GET /fines/member/{memberId}` - Get member fines
- `GET /fines/pending` - Get all pending fines (Admin/Librarian)

### Dashboard
- `GET /dashboard/stats` - Get dashboard statistics (Admin/Librarian)

## Database Schema

### Tables
- **users** - System users
- **books** - Book catalog
- **members** - Member profiles
- **borrowing_transactions** - Borrowing history
- **fines** - Fine records

## Usage

### As an Administrator
1. Login with admin credentials
2. View dashboard with system statistics
3. Manage books (add, edit, delete)
4. Manage members
5. Monitor fines and overdue books
6. Generate reports

### As a Member
1. Register and create account
2. Browse available books
3. Borrow books (up to 5 books limit)
4. Check borrowing history
5. Pay fines if any

### As a Librarian
1. Login with librarian credentials
2. Manage book inventory
3. Process book borrowing and returns
4. Monitor overdue books
5. Manage fines

## Project Structure

```
library-management-system/
├── backend/
│   ├── src/main/java/com/lms/
│   │   ├── controller/        # REST API Controllers
│   │   ├── service/          # Business Logic
│   │   ├── entity/           # Database Entities
│   │   ├── repository/       # Database Access Layer
│   │   ├── security/         # JWT & Authentication
│   │   ├── config/           # Spring Configuration
│   │   ├── dto/              # Data Transfer Objects
│   │   └── exception/        # Exception Handling
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── pages/            # Page Components
│   │   ├── components/       # Reusable Components
│   │   ├── services/         # API Services
│   │   ├── context/          # React Context (Auth)
│   │   ├── App.jsx
│   │   └── main.jsx
│   ├── package.json
│   ├── vite.config.js
│   └── index.html
└── README.md

```

## Authentication & Authorization

### User Roles
1. **ADMIN** - Full system access
2. **LIBRARIAN** - Book and member management
3. **MEMBER** - Book borrowing and fine management

### JWT Token
- Tokens are valid for 24 hours
- Include token in Authorization header: `Bearer <token>`
- Tokens are stored in localStorage on the client

## Default Test Credentials

Admin User:
- Email: admin@lms.com
- Password: admin123

## Future Enhancements

- [ ] Email notifications for overdue books
- [ ] Book reservations system
- [ ] Advanced reporting and analytics
- [ ] Mobile app
- [ ] Payment gateway integration
- [ ] QR code-based borrowing
- [ ] Recommendation engine
- [ ] User ratings and reviews

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is open source and available under the MIT License.

## Support & Contact

For support, email support@lms.com or create an issue in the repository.

---

**Built with ❤️ for Library Management**
