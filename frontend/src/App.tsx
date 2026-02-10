import React, { useState, useEffect, FormEvent } from 'react';
import { messageService } from './services/api';
import { Message, Stats, ViewType } from './types';
import './App.css';

const App: React.FC = () => {
  const [view, setView] = useState<ViewType>('home');
  const [content, setContent] = useState<string>('');
  const [randomMessage, setRandomMessage] = useState<Message | null>(null);
  const [myMessages, setMyMessages] = useState<Message[]>([]);
  const [stats, setStats] = useState<Stats>({ totalMessages: 0 });
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>('');
  const [success, setSuccess] = useState<string>('');

  useEffect(() => {
    loadStats();
  }, []);

  const loadStats = async (): Promise<void> => {
    try {
      const data = await messageService.getStats();
      setStats(data);
    } catch (err) {
      console.error('Erro ao carregar estatísticas:', err);
    }
  };

  const handleSendMessage = async (e: FormEvent<HTMLFormElement>): Promise<void> => {
    e.preventDefault();
    setError('');
    setSuccess('');
    setLoading(true);

    try {
      await messageService.sendMessage(content);
      setSuccess('Desabafo enviado com sucesso! 💙');
      setContent('');
      loadStats();
      setTimeout(() => setView('home'), 2000);
    } catch (err: any) {
      setError(err.response?.data?.message || 'Erro ao enviar desabafo');
    } finally {
      setLoading(false);
    }
  };

  const handleGetRandomMessage = async (): Promise<void> => {
    setError('');
    setLoading(true);

    try {
      const message = await messageService.getRandomMessage();
      if (message) {
        setRandomMessage(message);
      } else {
        setError('Ainda não há desabafos disponíveis. Seja o primeiro!');
      }
    } catch (err) {
      setError('Erro ao buscar desabafo');
    } finally {
      setLoading(false);
    }
  };

  const handleLoadMyMessages = async (): Promise<void> => {
    setError('');
    setLoading(true);

    try {
      const messages = await messageService.getMyMessages();
      setMyMessages(messages);
    } catch (err) {
      setError('Erro ao carregar seus desabafos');
    } finally {
      setLoading(false);
    }
  };

  const renderHome = (): JSX.Element => (
    <div className="home-container">
      <h1 className="title">Desabafo Anônimo</h1>
      <p className="subtitle">
        Um espaço seguro e anônimo para compartilhar seus sentimentos
      </p>

      <div className="stats">
        <p>📝 {stats.totalMessages} desabafos compartilhados</p>
      </div>

      <div className="button-container">
        <button
          className="btn btn-primary"
          onClick={() => setView('send')}
        >
          ✍️ Enviar um Desabafo
        </button>

        <button
          className="btn btn-secondary"
          onClick={() => {
            setView('receive');
            handleGetRandomMessage();
          }}
        >
          👂 Ler um Desabafo
        </button>

        <button
          className="btn btn-tertiary"
          onClick={() => {
            setView('my');
            handleLoadMyMessages();
          }}
        >
          📋 Meus Desabafos
        </button>
      </div>
    </div>
  );

  const renderSend = (): JSX.Element => (
    <div className="send-container">
      <button className="btn-back" onClick={() => setView('home')}>
        ← Voltar
      </button>

      <h2>Compartilhe seu desabafo</h2>
      <p className="info-text">
        Você está completamente anônimo. Expresse-se livremente.
      </p>

      <form onSubmit={handleSendMessage}>
        <textarea
          className="textarea"
          placeholder="Escreva aqui o que está sentindo..."
          value={content}
          onChange={(e) => setContent(e.target.value)}
          maxLength={500}
          rows={8}
          required
        />
        <div className="char-count">
          {content.length}/500 caracteres
        </div>

        {error && <div className="error-message">{error}</div>}
        {success && <div className="success-message">{success}</div>}

        <button
          type="submit"
          className="btn btn-primary"
          disabled={loading || !content.trim()}
        >
          {loading ? 'Enviando...' : 'Enviar Desabafo'}
        </button>
      </form>
    </div>
  );

  const renderReceive = (): JSX.Element => (
    <div className="receive-container">
      <button className="btn-back" onClick={() => setView('home')}>
        ← Voltar
      </button>

      <h2>Desabafo Aleatório</h2>

      {loading && <p className="loading">Carregando...</p>}

      {error && <div className="error-message">{error}</div>}

      {randomMessage && (
        <div className="message-card">
          <p className="message-content">{randomMessage.content}</p>
          <p className="message-date">
            {new Date(randomMessage.createdAt).toLocaleDateString('pt-BR', {
              day: '2-digit',
              month: '2-digit',
              year: 'numeric',
              hour: '2-digit',
              minute: '2-digit',
            })}
          </p>
        </div>
      )}

      <button
        className="btn btn-secondary"
        onClick={handleGetRandomMessage}
        disabled={loading}
      >
        🔄 Ler Outro Desabafo
      </button>
    </div>
  );

  const renderMyMessages = (): JSX.Element => (
    <div className="my-messages-container">
      <button className="btn-back" onClick={() => setView('home')}>
        ← Voltar
      </button>

      <h2>Meus Desabafos</h2>

      {loading && <p className="loading">Carregando...</p>}

      {error && <div className="error-message">{error}</div>}

      {myMessages.length === 0 && !loading && (
        <p className="info-text">Você ainda não enviou nenhum desabafo.</p>
      )}

      <div className="messages-list">
        {myMessages.map((message) => (
          <div key={message.id} className="message-card">
            <p className="message-content">{message.content}</p>
            <p className="message-date">
              {new Date(message.createdAt).toLocaleDateString('pt-BR', {
                day: '2-digit',
                month: '2-digit',
                year: 'numeric',
                hour: '2-digit',
                minute: '2-digit',
              })}
            </p>
          </div>
        ))}
      </div>
    </div>
  );

  return (
    <div className="App">
      <div className="container">
        {view === 'home' && renderHome()}
        {view === 'send' && renderSend()}
        {view === 'receive' && renderReceive()}
        {view === 'my' && renderMyMessages()}
      </div>
    </div>
  );
};

export default App;
