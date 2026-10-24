import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { userApi } from '../api';

export default function Signup() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ email: '', password: '', nickname: '' });

  const change = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const submit = (e) => {
    e.preventDefault();
    userApi
      .signup(form)
      .then(() => navigate('/login'))
      .catch((err) => alert(err.message));
  };

  return (
    <main>
      <h1>회원가입</h1>
      <form onSubmit={submit} className="form narrow">
        <input name="email" type="email" value={form.email} onChange={change} placeholder="이메일" required />
        <input name="password" type="password" value={form.password} onChange={change} placeholder="비밀번호" required />
        <input name="nickname" value={form.nickname} onChange={change} placeholder="닉네임" required />
        <button type="submit">가입</button>
      </form>
      <p className="empty">
        이미 계정이 있나요? <Link to="/login">로그인</Link>
      </p>
    </main>
  );
}
