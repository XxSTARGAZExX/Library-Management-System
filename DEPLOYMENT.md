# DEPLOYMENT & GETTING STARTED GUIDE

## 📚 Project Overview

**Library Management System** is a full-stack web application for managing library operations including:
- User authentication and role-based access control
- Book catalog management with search functionality
- Member registration and profile management
- Book borrowing and return tracking
- Automated fine calculation for overdue books
- Comprehensive dashboard with analytics

## 🏗️ Project Structure

```
library-management-system/
├── backend/                    # Spring Boot REST API
│   ├── src/main/java/com/lms/
│   │   ├── controller/        # REST endpoints
│   │   ├── service/           # Business logic
│   │   ├── entity/            # JPA entities
│   │   ├── repository/        # Spring Data repositories
│   │   ├── security/          # JWT authentication
│   │   ├── config/            # Spring configuration
│   │   ├── dto/               # Data transfer objects
│   │   └── exception/         # Custom exceptions
│   ├── pom.xml                # Maven dependencies
│   ├── SETUP.md               # Backend setup guide
│   └── application.properties  # Database config
│
├── frontend/                   # React application
│   ├── src/
│   │   ├── pages/             # React page components
│   │   ├── components/        # Reusable components
│   │   ├── services/          # API client
│   │   ├── context/           # React context (auth)
│   │   ├── App.jsx            # Main app component
│   │   └── main.jsx           # Entry point
│   ├── package.json           # NPM dependencies
│   ├── vite.config.js         # Vite configuration
│   ├── SETUP.md               # Frontend setup guide
│   └── index.html             # HTML template
│
├── README.md                   # Project documentation
├── GITHUB_PUSH.md             # GitHub push instructions
└── DEPLOYMENT.md              # This file
```

## 🚀 Quick Start

### Backend Setup (Spring Boot)

#### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 5.7+

#### Steps

1. **Create Database**
```bash
mysql -u root -p
CREATE DATABASE library_management_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EXIT;
```

2. **Configure database credentials**
   - Open: `backend/src/main/resources/application.properties`
   - Update:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/library_management_system
     spring.datasource.username=root
     spring.datasource.password=your_password
     ```

3. **Build and Run**
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

✅ Backend runs on: `http://localhost:8080/api`

### Frontend Setup (React)

#### Prerequisites
- Node.js 16+
- npm 8.0+

#### Steps

1. **Install Dependencies**
```bash
cd frontend
npm install
```

2. **Start Development Server**
```bash
npm run dev
```

✅ Frontend runs on: `http://localhost:3000`

## 🔐 Authentication

### Default Admin Account (Create by registering)
- Email: admin@lms.com
- Password: admin123 (set your own during registration)

### Token Management
- JWT tokens valid for 24 hours
- Stored in localStorage
- Auto-included in API requests

## 📊 Features Overview

### Dashboard
- Total books count
- Active members count
- Books borrowed today
- Overdue books count
- Pending fines amount

### Book Management
- View all books with details
- Search by title, author, or category
- Add new books (Admin/Librarian)
- Update book details
- Track availability

### Member Management
- View member profiles
- Check member status (Active/Suspended)
- View borrowed books count
- Track member fines

### Borrowing System
- Borrow books (max 5 per member)
- Track borrow date and due date
- Return books
- View borrowing history
- Monitor overdue books

### Fine Management
- Auto-calculate fines for overdue books
- Track pending and paid fines
- Process fine payments
- View fine history

## 🔗 API Endpoints

### Authentication
```
POST   /auth/register          - Register new user
POST   /auth/login             - Login user
GET    /auth/user/{id}         - Get user details
```

### Books
```
GET    /books                  - Get all books
GET    /books?title=search     - Search by title
GET    /books?author=search    - Search by author
GET    /books?category=search  - Search by category
GET    /books/{id}             - Get book details
POST   /books                  - Create book (Admin/Librarian)
PUT    /books/{id}             - Update book (Admin/Librarian)
DELETE /books/{id}             - Delete book (Admin/Librarian)
```

### Members
```
GET    /members                - Get all members
GET    /members/{id}           - Get member details
POST   /members/{userId}       - Create member profile
PUT    /members/{id}/suspend   - Suspend member (Admin/Librarian)
PUT    /members/{id}/activate  - Activate member (Admin/Librarian)
```

### Borrowing
```
POST   /borrowing/borrow?memberId=X&bookId=Y  - Borrow book
POST   /borrowing/return/{id}                  - Return book
GET    /borrowing                              - Get all transactions
GET    /borrowing/member/{id}                  - Get member transactions
GET    /borrowing/overdue                      - Get overdue books (Admin/Librarian)
```

### Fines
```
POST   /fines/calculate/{id}          - Calculate fine
PUT    /fines/{id}/pay                - Pay fine
GET    /fines/{id}                    - Get fine details
GET    /fines/member/{id}             - Get member fines
GET    /fines/pending                 - Get pending fines (Admin/Librarian)
GET    /fines/total-pending           - Get total pending fines
```

### Dashboard
```
GET    /dashboard/stats        - Get dashboard statistics (Admin/Librarian)
```

## 🐛 Troubleshooting

### Backend Issues

**Port 8080 already in use:**
```properties
# Change in application.properties
server.port=8081
```

**Database connection error:**
- Verify MySQL is running
- Check connection string and credentials
- Ensure database exists

**Maven build fails:**
```bash
mvn clean install -DskipTests
```

### Frontend Issues

**Port 3000 already in use:**
```javascript
// In vite.config.js
server: {
  port: 3001
}
```

**API not found errors:**
- Ensure backend is running on port 8080
- Check CORS configuration
- Verify proxy settings in vite.config.js

**Dependencies not installing:**
```bash
rm -rf node_modules package-lock.json
npm install
```

## 📤 Pushing to GitHub

### Step 1: Create GitHub Repository
1. Go to https://github.com/new
2. Name it: `library-management-system`
3. Click Create

### Step 2: Push Code
```bash
cd library-management-system

# Add remote
git remote add origin https://github.com/YOUR_USERNAME/library-management-system.git

# Rename branch
git branch -M main

# Push
git push -u origin main
```

### Step 3: Verify
Visit your repository: `https://github.com/YOUR_USERNAME/library-management-system`

## 🔄 Development Workflow

### Creating New Features

1. Create feature branch:
```bash
git checkout -b feature/new-feature
```

2. Make changes and commit:
```bash
git add .
git commit -m "Add new feature"
```

3. Push to GitHub:
```bash
git push origin feature/new-feature
```

4. Create Pull Request on GitHub

## 📝 Important Files to Know

### Backend Configuration
- `backend/src/main/resources/application.properties` - Database and server config
- `backend/pom.xml` - Maven dependencies

### Frontend Configuration
- `frontend/vite.config.js` - Vite and proxy setup
- `frontend/src/services/api.js` - API configuration
- `frontend/.env` - Environment variables (if needed)

## 🏭 Production Deployment

### Backend (Spring Boot)
```bash
# Build JAR
cd backend
mvn clean package

# Run JAR
java -jar target/library-management-system-1.0.0.jar
```

### Frontend (React)
```bash
# Build production bundle
cd frontend
npm run build

# Output in dist/ directory, ready to serve with any web server
```

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [React Documentation](https://react.dev)
- [JWT Authentication](https://jwt.io)
- [Vite Documentation](https://vitejs.dev)

## 📞 Support

For issues or questions:
1. Check the README.md
2. Review setup guides (SETUP.md files)
3. Check GitHub Issues
4. Contact: akakas@example.com

## ✅ Deployment Checklist

- [ ] Git repository created
- [ ] Code pushed to GitHub
- [ ] Backend running on localhost:8080
- [ ] Frontend running on localhost:3000
- [ ] Can login with registered credentials
- [ ] Can view dashboard
- [ ] Can perform CRUD operations on books
- [ ] Can borrow and return books
- [ ] Can view member profiles
- [ ] Can manage fines

---

**Happy Coding! 🚀**

Built with ❤️ for Library Management
