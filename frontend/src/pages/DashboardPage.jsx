import React, { useEffect, useState } from 'react';
import { dashboardService } from '../services/api';
import { Navigation } from '../components/Navigation';

const styles = {
  container: {
    minHeight: '100vh',
    background: '#f5f7fa'
  },
  content: {
    maxWidth: '1200px',
    margin: '0 auto',
    padding: '2rem'
  },
  title: {
    fontSize: '2rem',
    marginBottom: '2rem',
    color: '#2c3e50'
  },
  statsGrid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))',
    gap: '1.5rem',
    marginBottom: '2rem'
  },
  statCard: {
    background: 'white',
    padding: '1.5rem',
    borderRadius: '8px',
    boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
    textAlign: 'center'
  },
  statValue: {
    fontSize: '2.5rem',
    fontWeight: 'bold',
    color: '#667eea',
    marginBottom: '0.5rem'
  },
  statLabel: {
    color: '#7f8c8d',
    fontSize: '0.9rem'
  },
  loading: {
    textAlign: 'center',
    padding: '2rem',
    color: '#7f8c8d'
  }
};

export const DashboardPage = () => {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const response = await dashboardService.getStats();
        setStats(response.data.data);
      } catch (error) {
        console.error('Failed to fetch dashboard stats:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchStats();
  }, []);

  return (
    <div style={styles.container}>
      <Navigation />
      <div style={styles.content}>
        <h1 style={styles.title}>📊 Dashboard</h1>
        
        {loading ? (
          <div style={styles.loading}>Loading stats...</div>
        ) : stats ? (
          <div style={styles.statsGrid}>
            <div style={styles.statCard}>
              <div style={styles.statValue}>{stats.totalBooks}</div>
              <div style={styles.statLabel}>Total Books</div>
            </div>
            <div style={styles.statCard}>
              <div style={styles.statValue}>{stats.totalMembers}</div>
              <div style={styles.statLabel}>Active Members</div>
            </div>
            <div style={styles.statCard}>
              <div style={styles.statValue}>{stats.borrowedBooks}</div>
              <div style={styles.statLabel}>Books Borrowed</div>
            </div>
            <div style={styles.statCard}>
              <div style={styles.statValue}>{stats.overdueBooks}</div>
              <div style={styles.statLabel}>Overdue Books</div>
            </div>
            <div style={styles.statCard}>
              <div style={styles.statValue}>₹{stats.totalFines.toFixed(2)}</div>
              <div style={styles.statLabel}>Pending Fines</div>
            </div>
            <div style={styles.statCard}>
              <div style={styles.statValue}>{stats.activeUsers}</div>
              <div style={styles.statLabel}>Active Users</div>
            </div>
          </div>
        ) : null}
      </div>
    </div>
  );
};
