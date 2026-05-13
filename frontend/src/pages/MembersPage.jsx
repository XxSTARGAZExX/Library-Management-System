import React, { useEffect, useState } from 'react';
import { memberService } from '../services/api';
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
  active: {
    background: '#d5f4e6',
    color: '#27ae60'
  },
  inactive: {
    background: '#fadbd8',
    color: '#c0392b'
  },
  suspended: {
    background: '#fdebd0',
    color: '#d68910'
  },
  loading: {
    textAlign: 'center',
    padding: '2rem',
    color: '#7f8c8d'
  }
};

export const MembersPage = () => {
  const [members, setMembers] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchMembers();
  }, []);

  const fetchMembers = async () => {
    try {
      setLoading(true);
      const response = await memberService.getAllMembers();
      setMembers(response.data.data || []);
    } catch (error) {
      console.error('Failed to fetch members:', error);
    } finally {
      setLoading(false);
    }
  };

  const getStatusStyle = (status) => {
    if (status === 'ACTIVE') return styles.active;
    if (status === 'SUSPENDED') return styles.suspended;
    return styles.inactive;
  };

  return (
    <div style={styles.container}>
      <Navigation />
      <div style={styles.content}>
        <h1 style={styles.title}>👥 Members</h1>

        {loading ? (
          <div style={styles.loading}>Loading members...</div>
        ) : (
          <table style={styles.table}>
            <thead>
              <tr>
                <th style={styles.th}>Membership ID</th>
                <th style={styles.th}>Name</th>
                <th style={styles.th}>Email</th>
                <th style={styles.th}>Phone</th>
                <th style={styles.th}>Status</th>
                <th style={styles.th}>Books Borrowed</th>
                <th style={styles.th}>Total Fines</th>
              </tr>
            </thead>
            <tbody>
              {members.map((member) => (
                <tr key={member.id}>
                  <td style={styles.td}>{member.membershipId}</td>
                  <td style={styles.td}>{member.user.firstName} {member.user.lastName}</td>
                  <td style={styles.td}>{member.user.email}</td>
                  <td style={styles.td}>{member.user.phone || '-'}</td>
                  <td style={styles.td}>
                    <span style={{...styles.status, ...getStatusStyle(member.status)}}>
                      {member.status}
                    </span>
                  </td>
                  <td style={styles.td}>{member.borrowedBooks}</td>
                  <td style={styles.td}>₹{member.totalFines.toFixed(2)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
};
