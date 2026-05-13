import React, { useEffect, useState } from 'react';
import { fineService } from '../services/api';
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
  table: {
    width: '100%',
    background: 'white',
    borderCollapse: 'collapse',
    boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
    borderRadius: '8px',
    overflow: 'hidden'
  },
  th: {
    background: '#2c3e50',
    color: 'white',
    padding: '1rem',
    textAlign: 'left',
    fontWeight: 'bold'
  },
  td: {
    padding: '1rem',
    borderBottom: '1px solid #ecf0f1'
  },
  status: {
    padding: '0.5rem 1rem',
    borderRadius: '20px',
    fontSize: '0.9rem',
    fontWeight: 'bold'
  },
  pending: {
    background: '#fdebd0',
    color: '#d68910'
  },
  paid: {
    background: '#d5f4e6',
    color: '#27ae60'
  },
  actionBtn: {
    padding: '0.5rem 1rem',
    margin: '0.25rem',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    fontSize: '0.9rem'
  },
  payBtn: {
    background: '#27ae60',
    color: 'white'
  },
  loading: {
    textAlign: 'center',
    padding: '2rem',
    color: '#7f8c8d'
  }
};

export const FinesPage = () => {
  const [fines, setFines] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchFines();
  }, []);

  const fetchFines = async () => {
    try {
      setLoading(true);
      const response = await fineService.getPendingFines();
      setFines(response.data.data || []);
    } catch (error) {
      console.error('Failed to fetch fines:', error);
    } finally {
      setLoading(false);
    }
  };

  const handlePayFine = async (fineId) => {
    try {
      await fineService.payFine(fineId);
      fetchFines();
      alert('Fine paid successfully');
    } catch (error) {
      alert('Failed to pay fine: ' + error.message);
    }
  };

  const getStatusStyle = (status) => {
    if (status === 'PENDING') return styles.pending;
    if (status === 'PAID') return styles.paid;
    return styles.pending;
  };

  return (
    <div style={styles.container}>
      <Navigation />
      <div style={styles.content}>
        <h1 style={styles.title}>💰 Fines Management</h1>

        {loading ? (
          <div style={styles.loading}>Loading fines...</div>
        ) : (
          <table style={styles.table}>
            <thead>
              <tr>
                <th style={styles.th}>Member</th>
                <th style={styles.th}>Book Title</th>
                <th style={styles.th}>Fine Amount</th>
                <th style={styles.th}>Overdue Days</th>
                <th style={styles.th}>Status</th>
                <th style={styles.th}>Date Created</th>
                <th style={styles.th}>Action</th>
              </tr>
            </thead>
            <tbody>
              {fines.map((fine) => (
                <tr key={fine.id}>
                  <td style={styles.td}>{fine.member.user.firstName} {fine.member.user.lastName}</td>
                  <td style={styles.td}>{fine.transaction.book.title}</td>
                  <td style={styles.td}>₹{fine.amount.toFixed(2)}</td>
                  <td style={styles.td}>{fine.overdueDays} days</td>
                  <td style={styles.td}>
                    <span style={{...styles.status, ...getStatusStyle(fine.status)}}>
                      {fine.status}
                    </span>
                  </td>
                  <td style={styles.td}>{new Date(fine.createdAt).toLocaleDateString()}</td>
                  <td style={styles.td}>
                    {fine.status === 'PENDING' && (
                      <button
                        style={{...styles.actionBtn, ...styles.payBtn}}
                        onClick={() => handlePayFine(fine.id)}
                      >
                        Pay Fine
                      </button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
};
