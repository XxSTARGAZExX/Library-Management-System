import axios from 'axios';

const API_BASE_URL = '/api';

const getAuthHeader = () => {
  const token = localStorage.getItem('token');
  return token ? { 'Content-Type': 'application/json', 'Authorization': `Bearer ${token}` } : { 'Content-Type': 'application/json' };
};

// Book Services
export const bookService = {
  getAllBooks: (params) => axios.get(`${API_BASE_URL}/books`, { headers: getAuthHeader(), params }),
  getBookById: (id) => axios.get(`${API_BASE_URL}/books/${id}`, { headers: getAuthHeader() }),
  createBook: (data) => axios.post(`${API_BASE_URL}/books`, data, { headers: getAuthHeader() }),
  updateBook: (id, data) => axios.put(`${API_BASE_URL}/books/${id}`, data, { headers: getAuthHeader() }),
  deleteBook: (id) => axios.delete(`${API_BASE_URL}/books/${id}`, { headers: getAuthHeader() }),
  searchBooks: (query, field) => {
    const params = {};
    params[field] = query;
    return axios.get(`${API_BASE_URL}/books`, { headers: getAuthHeader(), params });
  }
};

// Member Services
export const memberService = {
  getAllMembers: () => axios.get(`${API_BASE_URL}/members`, { headers: getAuthHeader() }),
  getMemberById: (id) => axios.get(`${API_BASE_URL}/members/${id}`, { headers: getAuthHeader() }),
  createMember: (userId) => axios.post(`${API_BASE_URL}/members/${userId}`, {}, { headers: getAuthHeader() }),
  suspendMember: (id) => axios.put(`${API_BASE_URL}/members/${id}/suspend`, {}, { headers: getAuthHeader() }),
  activateMember: (id) => axios.put(`${API_BASE_URL}/members/${id}/activate`, {}, { headers: getAuthHeader() })
};

// Borrowing Services
export const borrowingService = {
  borrowBook: (memberId, bookId) => 
    axios.post(`${API_BASE_URL}/borrowing/borrow`, null, { 
      headers: getAuthHeader(), 
      params: { memberId, bookId } 
    }),
  returnBook: (transactionId) => 
    axios.post(`${API_BASE_URL}/borrowing/return/${transactionId}`, {}, { headers: getAuthHeader() }),
  getMemberTransactions: (memberId) => 
    axios.get(`${API_BASE_URL}/borrowing/member/${memberId}`, { headers: getAuthHeader() }),
  getOverdueTransactions: () => 
    axios.get(`${API_BASE_URL}/borrowing/overdue`, { headers: getAuthHeader() }),
  getAllTransactions: () => 
    axios.get(`${API_BASE_URL}/borrowing`, { headers: getAuthHeader() })
};

// Fine Services
export const fineService = {
  calculateFine: (transactionId) => 
    axios.post(`${API_BASE_URL}/fines/calculate/${transactionId}`, {}, { headers: getAuthHeader() }),
  payFine: (fineId) => 
    axios.put(`${API_BASE_URL}/fines/${fineId}/pay`, {}, { headers: getAuthHeader() }),
  getFineById: (id) => 
    axios.get(`${API_BASE_URL}/fines/${id}`, { headers: getAuthHeader() }),
  getMemberFines: (memberId) => 
    axios.get(`${API_BASE_URL}/fines/member/${memberId}`, { headers: getAuthHeader() }),
  getPendingFines: () => 
    axios.get(`${API_BASE_URL}/fines/pending`, { headers: getAuthHeader() }),
  getTotalPendingFines: () => 
    axios.get(`${API_BASE_URL}/fines/total-pending`, { headers: getAuthHeader() })
};

// Dashboard Services
export const dashboardService = {
  getStats: () => 
    axios.get(`${API_BASE_URL}/dashboard/stats`, { headers: getAuthHeader() })
};
