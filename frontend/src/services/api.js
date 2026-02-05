import axios from 'axios';

const API_BASE_URL = 'http://10.0.0.14:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const messageService = {
  // Enviar um desabafo
  sendMessage: async (content) => {
    const response = await api.post('/messages', { content });
    return response.data;
  },

  // Receber um desabafo aleatório
  getRandomMessage: async () => {
    const response = await api.get('/messages/random');
    return response.data;
  },

  // Ver meus desabafos
  getMyMessages: async () => {
    const response = await api.get('/messages/my');
    return response.data;
  },

  // Estatísticas
  getStats: async () => {
    const response = await api.get('/messages/stats');
    return response.data;
  },
};

export default api;
