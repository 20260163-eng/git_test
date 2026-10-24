import { useState } from 'react';
import { useLocation, useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../auth';

export default function Login() {
  const navigate = useNavigate();
  const location = useLocation();
  const { login } = useAuth();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const submit = (e) => {
    e.preventDefault();
    login({ email, password })
      .then(() => navigate(location.state?.from || '/', { replace: true }))
      .catch((err) => alert(err.message));
  };

  return (
    <main>
      <h1>로그인</h1>
      <form onSubmit={submit} className="form narrow">
        <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} placeholder="이메일" required />
        <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="비밀번호" required />
        <button type="submit">로그인</button>
      </form>
      <p className="empty">
        계정이 없나요? <Link to="/signup">회원가입</Link>
      </p>
    </main>
  );
}
