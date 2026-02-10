import axios, { AxiosInstance } from 'axios';
import { Message, Stats } from 'types';

const API_BASE_URL = 'http://10.0.0.13:8080/api';

const api: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const messageService = {
  // Enviar um desabafo
  sendMessage: async (content: string): Promise<Message> => {
    const response = await api.post<Message>('/messages', { content });
    return response.data;
  },

  // Receber um desabafo aleatório
  getRandomMessage: async (): Promise<Message | null> => {
    const response = await api.get<Message | null>('/messages/random');
    return response.data;
  },

  // Ver meus desabafos
  getMyMessages: async (): Promise<Message[]> => {
    const response = await api.get<Message[]>('/messages/my');
    return response.data;
  },

  // Estatísticas
  getStats: async (): Promise<Stats> => {
    const response = await api.get<Stats>('/messages/stats');
    return response.data;
  },
};

export default api;
