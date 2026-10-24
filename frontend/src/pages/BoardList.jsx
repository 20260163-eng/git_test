import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { boardApi } from '../api';

function BoardItem({ board, onReload }) {
  const [editing, setEditing] = useState(false);
  const [name, setName] = useState(board.name);
  const [description, setDescription] = useState(board.description || '');

  const update = () => {
    boardApi
      .update(board.id, { name, description })
      .then(() => setEditing(false))
      .then(onReload)
      .catch((err) => alert(err.message));
  };

  const remove = () => {
    if (!confirm('게시판을 삭제할까요?')) return;
    boardApi.remove(board.id).then(onReload).catch((err) => alert(err.message));
  };

  if (editing) {
    return (
      <li className="board-list-item">
        <div className="inline-form">
          <input value={name} onChange={(e) => setName(e.target.value)} required />
          <input value={description} onChange={(e) => setDescription(e.target.value)} />
          <button onClick={update}>저장</button>
          <button onClick={() => setEditing(false)}>취소</button>
        </div>
      </li>
    );
  }

  return (
    <li className="board-list-item">
      <Link to={`/boards/${board.id}`}>
        <strong>{board.name}</strong>
        <span>{board.description}</span>
      </Link>
      <div>
        <button onClick={() => setEditing(true)}>수정</button>
        <button onClick={remove}>삭제</button>
      </div>
    </li>
  );
}

export default function BoardList() {
  const [boards, setBoards] = useState([]);
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');


  const load = () => boardApi.findAll().then(setBoards).catch((e) => alert(e.message));

    useEffect(() => {
          load();
          return () => {
            console.log('컴포넌트가 언마운트되며 정리됩니다.');
          };
        }, []);

  const create = (e) => {
    e.preventDefault();
    boardApi
      .create({ name, description })
      .then(() => { setName(''); setDescription(''); load(); })
      .catch((err) => alert(err.message));
  };

  return (
    <main>
      <h1>게시판 목록</h1>
      <ul className="board-list">
        {boards.map((b) => (
          <BoardItem key={b.id} board={b} onReload={load} />
        ))}
        {boards.length === 0 && <li className="empty">게시판이 없습니다.</li>}
      </ul>

      <form onSubmit={create} className="form">
        <h2>게시판 생성</h2>
        <input value={name} onChange={(e) => setName(e.target.value)} placeholder="이름" required />
        <input value={description} onChange={(e) => setDescription(e.target.value)} placeholder="설명" />
        <button type="submit">생성</button>
      </form>
    </main>
  );
}
