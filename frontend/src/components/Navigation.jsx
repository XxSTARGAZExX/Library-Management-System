import React from 'react';
import { useAuth } from '../context/AuthContext';

const styles = {
  nav: {
    background: '#2c3e50',
    padding: '1rem 2rem',
    color: 'white',
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    boxShadow: '0 2px 8px rgba(0,0,0,0.1)'
  },
  navLeft: {
    display: 'flex',
    gap: '2rem',
    alignItems: 'center'
  },
  logo: {
    fontSize: '1.5rem',
    fontWeight: 'bold',
    color: '#667eea'
  },
  navLinks: {
    display: 'flex',
    gap: '1.5rem',
    listStyle: 'none'
  },
  navLink: {
    cursor: 'pointer',
    color: '#ecf0f1',
    textDecoration: 'none',
    transition: 'color 0.3s'
  },
  navRight: {
    display: 'flex',
    gap: '1rem',
    alignItems: 'center'
  },
  userInfo: {
    fontSize: '0.9rem',
    color: '#bdc3c7'
  },
  logoutBtn: {
    padding: '0.5rem 1rem',
    background: '#e74c3c',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    transition: 'background 0.3s'
  }
};

export const Navigation = () => {
  const { user, logout } = useAuth();

  return (
    <nav style={styles.nav}>
      <div style={styles.navLeft}>
        <div style={styles.logo}>📚 LMS</div>
        <ul style={styles.navLinks}>
          <li><a href="/dashboard" style={styles.navLink}>Dashboard</a></li>
          <li><a href="/books" style={styles.navLink}>Books</a></li>
          <li><a href="/members" style={styles.navLink}>Members</a></li>
          <li><a href="/borrowing" style={styles.navLink}>Borrowings</a></li>
          <li><a href="/fines" style={styles.navLink}>Fines</a></li>
        </ul>
      </div>
      <div style={styles.navRight}>
        {user && (
          <>
            <div style={styles.userInfo}>
              {user.firstName} {user.lastName}
              <br />
              <small>{user.role}</small>
            </div>
            <button style={styles.logoutBtn} onClick={logout}>Logout</button>
          </>
        )}
      </div>
    </nav>
  );
};
