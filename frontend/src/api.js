import axios from 'axios';

const api = axios.create({
  baseURL: '/api',
  withCredentials: true,
});

export function unwrap(promise) {
  return promise.then((res) => {
    const body = res.data;
    if (!body.success) {
      throw new Error(body.error?.message || '요청 실패');
    }
    return body.data;
  });
}

export const boardApi = {
  findAll: () => unwrap(api.get('/boards')),
  findById: (id) => unwrap(api.get(`/boards/${id}`)),
  create: (data) => unwrap(api.post('/boards', data)),
  update: (id, data) => unwrap(api.put(`/boards/${id}`, data)),
  remove: (id) => unwrap(api.delete(`/boards/${id}`)),
};

export const articleApi = {
  findAllByBoard: (boardId, page = 0, size = 10) =>
    unwrap(api.get(`/boards/${boardId}/articles`, { params: { page, size, sort: 'createdAt,desc' } })),
  findById: (id) => unwrap(api.get(`/articles/${id}`)),
  create: (boardId, data) => unwrap(api.post(`/boards/${boardId}/articles`, data)),
  update: (id, data) => unwrap(api.put(`/articles/${id}`, data)),
  remove: (id) => unwrap(api.delete(`/articles/${id}`)),
};

export const commentApi = {
  findAllByArticle: (articleId) => unwrap(api.get(`/articles/${articleId}/comments`)),
  create: (articleId, data) => unwrap(api.post(`/articles/${articleId}/comments`, data)),
  update: (id, data) => unwrap(api.put(`/comments/${id}`, data)),
  remove: (id) => unwrap(api.delete(`/comments/${id}`)),
};

export const userApi = {
  signup: (data) => unwrap(api.post('/users/signup', data)),
  login: (data) => unwrap(api.post('/users/login', data)),
  logout: () => unwrap(api.post('/users/logout')),
  me: () => unwrap(api.get('/users/me')),
};

export default api;
