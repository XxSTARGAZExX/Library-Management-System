import React, { useEffect, useState } from 'react';
import { bookService } from '../services/api';
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
  header: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '2rem'
  },
  title: {
    fontSize: '2rem',
    color: '#2c3e50'
  },
  searchBox: {
    display: 'flex',
    gap: '0.5rem'
  },
  input: {
    padding: '0.5rem 1rem',
    border: '1px solid #bdc3c7',
    borderRadius: '4px',
    fontSize: '1rem'
  },
  button: {
    padding: '0.5rem 1rem',
    background: '#667eea',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer'
  },
  booksGrid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fill, minmax(280px, 1fr))',
    gap: '1.5rem'
  },
  bookCard: {
    background: 'white',
    padding: '1rem',
    borderRadius: '8px',
    boxShadow: '0 2px 8px rgba(0,0,0,0.1)'
  },
  bookTitle: {
    fontSize: '1.2rem',
    fontWeight: 'bold',
    marginBottom: '0.5rem',
    color: '#2c3e50'
  },
  bookAuthor: {
    color: '#7f8c8d',
    marginBottom: '0.5rem'
  },
  bookCategory: {
    display: 'inline-block',
    background: '#ecf0f1',
    color: '#2c3e50',
    padding: '0.25rem 0.75rem',
    borderRadius: '20px',
    fontSize: '0.8rem',
    marginBottom: '1rem'
  },
  bookInfo: {
    fontSize: '0.9rem',
    color: '#7f8c8d',
    marginBottom: '0.5rem'
  },
  availability: {
    marginTop: '1rem',
    padding: '0.75rem',
    borderRadius: '4px',
    textAlign: 'center',
    fontWeight: 'bold'
  },
  available: {
    background: '#d5f4e6',
    color: '#27ae60'
  },
  unavailable: {
    background: '#fadbd8',
    color: '#c0392b'
  },
  loading: {
    textAlign: 'center',
    padding: '2rem',
    color: '#7f8c8d'
  }
};

export const BooksPage = () => {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchQuery, setSearchQuery] = useState('');
  const [searchField, setSearchField] = useState('title');

  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = async (query = '', field = 'title') => {
    try {
      setLoading(true);
      const response = await bookService.getAllBooks(
        query ? { [field]: query } : {}
      );
      setBooks(response.data.data || []);
    } catch (error) {
      console.error('Failed to fetch books:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = () => {
    fetchBooks(searchQuery, searchField);
  };

  return (
    <div style={styles.container}>
      <Navigation />
      <div style={styles.content}>
        <div style={styles.header}>
          <h1 style={styles.title}>📚 Books Catalog</h1>
          <div style={styles.searchBox}>
            <select
              value={searchField}
              onChange={(e) => setSearchField(e.target.value)}
              style={styles.input}
            >
              <option value="title">Search by Title</option>
              <option value="author">Search by Author</option>
              <option value="category">Search by Category</option>
            </select>
            <input
              type="text"
              placeholder="Search..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              style={styles.input}
            />
            <button onClick={handleSearch} style={styles.button}>Search</button>
          </div>
        </div>

        {loading ? (
          <div style={styles.loading}>Loading books...</div>
        ) : (
          <div style={styles.booksGrid}>
            {books.map((book) => (
              <div key={book.id} style={styles.bookCard}>
                <div style={styles.bookTitle}>{book.title}</div>
                <div style={styles.bookAuthor}>by {book.author}</div>
                <span style={styles.bookCategory}>{book.category}</span>
                <div style={styles.bookInfo}>
                  <strong>ISBN:</strong> {book.isbn}
                </div>
                <div style={styles.bookInfo}>
                  <strong>Publisher:</strong> {book.publisher}
                </div>
                <div style={styles.bookInfo}>
                  <strong>Year:</strong> {book.publishYear}
                </div>
                <div style={styles.bookInfo}>
                  <strong>Price:</strong> ₹{book.price}
                </div>
                <div style={styles.bookInfo}>
                  Available: {book.availableCopies} / {book.totalCopies}
                </div>
                <div style={{
                  ...styles.availability,
                  ...(book.availableCopies > 0 ? styles.available : styles.unavailable)
                }}>
                  {book.availableCopies > 0 ? '✓ Available' : '✗ Unavailable'}
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};
