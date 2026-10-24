import { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { articleApi } from '../api';

export default function BoardDetail() {
  const { boardId } = useParams();
  const navigate = useNavigate();
  const [pageData, setPageData] = useState(null);
  const [page, setPage] = useState(0);

  useEffect(() => {
    articleApi
      .findAllByBoard(boardId, page)
      .then(setPageData)
      .catch((e) => alert(e.message));
  }, [boardId, page]);

  if (!pageData) return <main>로딩 중...</main>;

  return (
    <main>
      <h1>게시글</h1>
      <button onClick={() => navigate(`/boards/${boardId}/articles/new`)}>글쓰기</button>
      <table className="article-table">
        <thead>
          <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>댓글</th>
            <th>작성일</th>
          </tr>
        </thead>
        <tbody>
          {pageData.content.map((a) => (
            <tr key={a.id}>
              <td>{a.id}</td>
              <td>
                <Link to={`/articles/${a.id}`}>{a.title}</Link>
              </td>
              <td>{a.authorNickname || '-'}</td>
              <td>{a.commentCount}</td>
              <td>{a.createdAt}</td>
            </tr>
          ))}
          {pageData.content.length === 0 && (
            <tr><td colSpan="5" className="empty">게시글이 없습니다.</td></tr>
          )}
        </tbody>
      </table>

      <div className="pagination">
        <button disabled={pageData.first} onClick={() => setPage(page - 1)}>이전</button>
        <span>{pageData.number + 1} / {Math.max(pageData.totalPages, 1)}</span>
        <button disabled={pageData.last} onClick={() => setPage(page + 1)}>다음</button>
      </div>
    </main>
  );
}
