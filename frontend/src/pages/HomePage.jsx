import React from 'react';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const styles = {
  container: {
    minHeight: '100vh',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
    padding: '2rem'
  },
  box: {
    background: 'white',
    padding: '3rem',
    borderRadius: '8px',
    boxShadow: '0 10px 25px rgba(0,0,0,0.2)',
    textAlign: 'center',
    maxWidth: '500px'
  },
  title: {
    fontSize: '2.5rem',
    marginBottom: '1rem',
    color: '#667eea'
  },
  subtitle: {
    fontSize: '1.2rem',
    color: '#7f8c8d',
    marginBottom: '2rem'
  },
  buttonGroup: {
    display: 'flex',
    gap: '1rem',
    justifyContent: 'center'
  },
  button: {
    padding: '0.75rem 2rem',
    fontSize: '1rem',
    fontWeight: 'bold',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    transition: 'all 0.3s'
  },
  loginBtn: {
    background: '#667eea',
    color: 'white'
  },
  registerBtn: {
    background: '#ecf0f1',
    color: '#2c3e50',
    border: '2px solid #667eea'
  }
};

export const HomePage = () => {
  const { user } = useAuth();
  const navigate = useNavigate();

  if (user) {
    navigate('/dashboard');
    return null;
  }

  return (
    <div style={styles.container}>
      <div style={styles.box}>
        <div style={styles.title}>📚 LMS</div>
        <div style={styles.subtitle}>Library Management System</div>
        <p style={{ color: '#7f8c8d', marginBottom: '2rem' }}>
          Manage your library efficiently with our modern platform
        </p>
        <div style={styles.buttonGroup}>
          <button
            style={{ ...styles.button, ...styles.loginBtn }}
            onClick={() => navigate('/login')}
          >
            Login
          </button>
          <button
            style={{ ...styles.button, ...styles.registerBtn }}
            onClick={() => navigate('/register')}
          >
            Register
          </button>
        </div>
      </div>
    </div>
  );
};
