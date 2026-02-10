// Tipos para mensagens
export interface Message {
  id: string;
  content: string;
  createdAt: string;
}

// Tipos para estatísticas
export interface Stats {
  totalMessages: number;
}

// Tipos para views da aplicação
export type ViewType = 'home' | 'send' | 'receive' | 'my';

// Tipos para respostas da API
export interface ApiResponse<T> {
  data: T;
  message?: string;
}

// Tipos para erros da API
export interface ApiError {
  message: string;
  status?: number;
}
