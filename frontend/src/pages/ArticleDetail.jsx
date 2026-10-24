import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { articleApi, commentApi } from '../api';
import { useAuth } from '../auth';

function CommentItem({ comment, onDeleted }) {
  const { user } = useAuth();
  const [editing, setEditing] = useState(false);
  const [content, setContent] = useState(comment.content);

  const isMine = user && comment.authorNickname === user.nickname;

  const update = () => {
    commentApi
      .update(comment.id, { content })
      .then(() => setEditing(false))
      .catch((e) => alert(e.message));
  };

  return (
    <li className="comment">
      <div className="comment-meta">
        <strong>{comment.authorNickname || '익명'}</strong>
        <span>{comment.createdAt}</span>
      </div>
      {editing ? (
        <>
          <textarea value={content} onChange={(e) => setContent(e.target.value)} />
          <button onClick={update}>저장</button>
          <button onClick={() => setEditing(false)}>취소</button>
        </>
      ) : (
        <>
          <p>{comment.content}</p>
          {isMine && (
            <>
              <button onClick={() => setEditing(true)}>수정</button>
              <button onClick={() => confirm('삭제할까요?') && onDeleted(comment.id)}>삭제</button>
            </>
          )}
        </>
      )}
    </li>
  );
}

export default function ArticleDetail() {
  const { articleId } = useParams();
  const navigate = useNavigate();
  const { user } = useAuth();
  const [article, setArticle] = useState(null);
  const [comments, setComments] = useState([]);
  const [commentText, setCommentText] = useState('');

  useEffect(() => {
    articleApi.findById(articleId).then(setArticle).catch((e) => alert(e.message));
    commentApi.findAllByArticle(articleId).then(setComments).catch(() => {});
  }, [articleId]);

  if (!article) return <main>로딩 중...</main>;

  const isMine = user && article.authorNickname === user.nickname;

  const removeArticle = () => {
    if (!confirm('게시글을 삭제할까요?')) return;
    articleApi
      .remove(articleId)
      .then(() => navigate(`/boards/${article.boardId}`))
      .catch((e) => alert(e.message));
  };

  const addComment = (e) => {
    e.preventDefault();
    commentApi
      .create(articleId, { content: commentText })
      .then((c) => { setComments([...comments, c]); setCommentText(''); })
      .catch((err) => alert(err.message));
  };

  const removeComment = (id) => {
    commentApi.remove(id).then(() => setComments(comments.filter((c) => c.id !== id)))
      .catch((e) => alert(e.message));
  };

  return (
    <main>
      <h1>{article.title}</h1>
      <div className="meta">
        {article.authorNickname || '익명'} · {article.createdAt}
        {isMine && (
          <>
            <button onClick={() => navigate(`/articles/${articleId}/edit`)}>수정</button>
            <button onClick={removeArticle}>삭제</button>
          </>
        )}
      </div>
      <div className="content">{article.content}</div>

      <section className="comments">
        <h2>댓글 ({comments.length})</h2>
        {user ? (
          <form onSubmit={addComment} className="form">
            <textarea
              value={commentText}
              onChange={(e) => setCommentText(e.target.value)}
              placeholder="댓글을 입력하세요"
              required
            />
            <button type="submit">등록</button>
          </form>
        ) : (
          <p className="empty">댓글을 작성하려면 로그인이 필요합니다.</p>
        )}
        <ul>
          {comments.map((c) => (
            <CommentItem key={c.id} comment={c} onDeleted={removeComment} />
          ))}
        </ul>
      </section>
    </main>
  );
}
