import { createContext, useContext, useEffect, useState } from 'react';
import api from './api';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api
      .get('/users/me')
      .then((res) => {
        if (res.data.success) setUser(res.data.data);
      })
      .catch(() => {
        // 401 등은 비로그인 상태일 뿐, 에러로 취급하지 않음
      })
      .finally(() => setLoading(false));
  }, []);

  const login = (data) =>
    api.post('/users/login', data).then((res) => {
      const u = res.data.data;
      setUser(u);
      return u;
    });

  const logout = () =>
    api.post('/users/logout').then(() => setUser(null));

  return (
    <AuthContext.Provider value={{ user, loading, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}
