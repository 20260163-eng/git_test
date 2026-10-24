import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { articleApi } from '../api';

export default function ArticleForm({ mode }) {
  const { boardId, articleId } = useParams();
  const navigate = useNavigate();
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  useEffect(() => {
    if (mode === 'edit') {
      articleApi.findById(articleId).then((a) => {
        setTitle(a.title);
        setContent(a.content);
      });
    }
  }, [mode, articleId]);

  const submit = (e) => {
    e.preventDefault();
    const action =
      mode === 'create'
        ? articleApi.create(boardId, { title, content })
        : articleApi.update(articleId, { title, content });
    action
      .then((a) => {
        navigate(mode === 'create' ? `/boards/${boardId}` : `/articles/${articleId}`);
      })
      .catch((err) => alert(err.message));
  };

  return (
    <main>
      <h1>{mode === 'create' ? '글쓰기' : '게시글 수정'}</h1>
      <form onSubmit={submit} className="form">
        <input value={title} onChange={(e) => setTitle(e.target.value)} placeholder="제목" required />
        <textarea
          value={content}
          onChange={(e) => setContent(e.target.value)}
          placeholder="내용"
          rows={12}
          required
        />
        <button type="submit">{mode === 'create' ? '등록' : '저장'}</button>
      </form>
    </main>
  );
}
