# Frontend Setup Instructions

## Prerequisites
- Node.js 16 or higher
- npm 8.0 or higher

## Setup Steps

### 1. Install Dependencies
```bash
npm install
```

### 2. Start Development Server
```bash
npm run dev
```

The application will start on `http://localhost:3000`

### 3. Configure API URL
The frontend is configured to proxy API requests to `http://localhost:8080` in `vite.config.js`

## Building for Production

```bash
npm run build
```

Output will be in `dist/` directory

## Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production

## Features

### Authentication
- User registration and login
- JWT token-based authentication
- Protected routes

### Main Features
- Dashboard with statistics
- Book catalog with search
- Member management
- Borrowing transactions
- Fine management

## Environment Configuration

Create `.env` file (optional):
```
VITE_API_URL=http://localhost:8080/api
```

## Troubleshooting

### Port 3000 already in use
Change port in `vite.config.js`:
```js
server: {
  port: 3001,  // Change to different port
}
```

### API connection errors
- Ensure backend is running on `http://localhost:8080`
- Check CORS configuration in backend
- Verify proxy settings in `vite.config.js`

### Module not found
Clear dependencies and reinstall:
```bash
rm -rf node_modules package-lock.json
npm install
```
