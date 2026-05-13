import React, { useEffect, useState } from 'react';
import { borrowingService } from '../services/api';
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
  borrowed: {
    background: '#d4efdf',
    color: '#28a745'
  },
  returned: {
    background: '#d5f4e6',
    color: '#27ae60'
  },
  overdue: {
    background: '#fadbd8',
    color: '#c0392b'
  },
  actionBtn: {
    padding: '0.5rem 1rem',
    margin: '0.25rem',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    fontSize: '0.9rem'
  },
  returnBtn: {
    background: '#667eea',
    color: 'white'
  },
  loading: {
    textAlign: 'center',
    padding: '2rem',
    color: '#7f8c8d'
  }
};

export const BorrowingPage = () => {
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchTransactions();
  }, []);

  const fetchTransactions = async () => {
    try {
      setLoading(true);
      const response = await borrowingService.getAllTransactions();
      setTransactions(response.data.data || []);
    } catch (error) {
      console.error('Failed to fetch transactions:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleReturnBook = async (transactionId) => {
    try {
      await borrowingService.returnBook(transactionId);
      fetchTransactions();
      alert('Book returned successfully');
    } catch (error) {
      alert('Failed to return book: ' + error.message);
    }
  };

  const getStatusStyle = (status) => {
    if (status === 'BORROWED') return styles.borrowed;
    if (status === 'OVERDUE') return styles.overdue;
    return styles.returned;
  };

  return (
    <div style={styles.container}>
      <Navigation />
      <div style={styles.content}>
        <h1 style={styles.title}>📖 Borrowing Transactions</h1>

        {loading ? (
          <div style={styles.loading}>Loading transactions...</div>
        ) : (
          <table style={styles.table}>
            <thead>
              <tr>
                <th style={styles.th}>Member</th>
                <th style={styles.th}>Book Title</th>
                <th style={styles.th}>Book Author</th>
                <th style={styles.th}>Borrow Date</th>
                <th style={styles.th}>Due Date</th>
                <th style={styles.th}>Return Date</th>
                <th style={styles.th}>Status</th>
                <th style={styles.th}>Action</th>
              </tr>
            </thead>
            <tbody>
              {transactions.map((trans) => (
                <tr key={trans.id}>
                  <td style={styles.td}>{trans.member.user.firstName} {trans.member.user.lastName}</td>
                  <td style={styles.td}>{trans.book.title}</td>
                  <td style={styles.td}>{trans.book.author}</td>
                  <td style={styles.td}>{new Date(trans.borrowDate).toLocaleDateString()}</td>
                  <td style={styles.td}>{new Date(trans.dueDate).toLocaleDateString()}</td>
                  <td style={styles.td}>{trans.returnDate ? new Date(trans.returnDate).toLocaleDateString() : '-'}</td>
                  <td style={styles.td}>
                    <span style={{...styles.status, ...getStatusStyle(trans.status)}}>
                      {trans.status}
                    </span>
                  </td>
                  <td style={styles.td}>
                    {trans.status === 'BORROWED' && (
                      <button
                        style={{...styles.actionBtn, ...styles.returnBtn}}
                        onClick={() => handleReturnBook(trans.id)}
                      >
                        Return
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
