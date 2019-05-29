const baseUrl = "http://localhost:8080";

function parseJSON(response) {
  if (response.status === 204 || response.status === 205) {
    return null;
  }
  return response.json();
}

function checkStatus(response) {
  if (response.status >= 200 && response.status < 300) {
    return response;
  }

  const error = new Error(response.statusText);
  error.response = response;
  try {
    error.code = response.json().code;
  } catch {}
  throw error;
}

function buildUrl(url, params) {
  const urlBuilder = new URL(url);
  if (params) {
    urlBuilder.search = new URLSearchParams(params).toString();
  }
  return urlBuilder.toString();
}

function request({ url, method, params, body }) {
  const fullUrl = buildUrl(baseUrl + url, params);
  const options = {
    method,
    headers: { "content-type": "application/json" },
    body: body ? JSON.stringify(body) : body,
  };

  return fetch(fullUrl, options)
    .then(checkStatus)
    .then(parseJSON);
}

const Api = {
  login: async credentials => {
    const { token } = await request({
      method: "POST",
      url: "/users/login",
      body: credentials,
    });
    return token;
  },

  register: async user => {
    const { token } = await request({
      method: "POST",
      url: "/users/register",
      body: user,
    });
    return token;
  },

  getCurrentUser: token =>
    request({
      method: "GET",
      url: "/users/me",
      params: { token },
    }),

  getPolls: async (token, uid) =>
    request({
      method: "GET",
      url: "/polls/user/" + uid,
      params: { token }
    }),
  
  createPoll: async (token, body) => 
    request({
      method: "POST",
      url: "/groups/" + body.groupId + "/poll",
      body: body,
      params: {token}
    }),
  
  getQuestions: async(token, pollId) =>
    request({
      method: "GET",
      url: "/polls/" + pollId + "/question",
      params: {token}
    }),

  getPoll: async(token, pollId) =>
    request({
      method: "GET",
      url: "/polls/" + pollId,
      params: {token}
    }),
  addQuestion: async (token, pollId, question) => 
    request({
      method: "POST",
      url: "/polls/" + pollId + "/question",
      params: {token},
      body: {content: question.content, open: question.open}
    }),
  addAnswer: async (token, questionId, content) =>
    request({
      method: "PATCH",
      url: "/questions/" + questionId + "/answer",
      params: {token},
      body: {content}
    }),
  removeQuestion: async (token, questionId) =>
    request({
      method: "DELETE",
      url: "/questions/" + questionId,
      params: {token}
    }),
  removeAnswer: async (token, questionId, answerId) =>
    request({
      method: "DELETE",
      url: "/questions/" + questionId + "/answer/" + answerId,
      params: {token}
    }),
};

export default Api;
