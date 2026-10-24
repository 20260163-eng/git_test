import { Navigate, Routes, Route, Link, useLocation } from 'react-router-dom';
import { useAuth } from './auth';
import BoardList from './pages/BoardList.jsx';
import BoardDetail from './pages/BoardDetail.jsx';
import ArticleDetail from './pages/ArticleDetail.jsx';
import ArticleForm from './pages/ArticleForm.jsx';
import Login from './pages/Login.jsx';
import Signup from './pages/Signup.jsx';

function RequireAuth({ children }) {
  const { user, loading } = useAuth();
  const location = useLocation();

  if (loading) return <main className="empty">로딩 중...</main>;
  if (!user) {
    return <Navigate to="/login" state={{ from: location.pathname }} replace />;
  }
  return children;
}

export default function App() {
  const { user, logout } = useAuth();

  return (
    <div className="container">
      <header className="header">
        <Link to="/" className="logo">Community</Link>
        <nav>
          {user ? (
            <>
              <span className="nickname">{user.nickname}님</span>
              <button
                onClick={() =>
                  logout()
                    .then(() => {})
                    .catch((e) => alert(e.message))
                }
              >
                로그아웃
              </button>
            </>
          ) : null}
        </nav>
      </header>

      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/" element={<RequireAuth><BoardList /></RequireAuth>} />
        <Route path="/boards/:boardId" element={<RequireAuth><BoardDetail /></RequireAuth>} />
        <Route path="/boards/:boardId/articles/new" element={<RequireAuth><ArticleForm mode="create" /></RequireAuth>} />
        <Route path="/articles/:articleId" element={<RequireAuth><ArticleDetail /></RequireAuth>} />
        <Route path="/articles/:articleId/edit" element={<RequireAuth><ArticleForm mode="edit" /></RequireAuth>} />
      </Routes>
    </div>
  );
}
