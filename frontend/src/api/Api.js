const baseUrl = "http://localhost:8080"; //process.env.REACT_APP_API_URL || "http://18.203.157.28/api";

async function parseResponse(response) {
  let json;
  try {
    json = await response.json();
  } catch {
    json = null;
  }

  if (response.status >= 200 && response.status < 300) {
    return json;
  } else {
    const error = new Error(response.statusText);
    error.response = response;
    if (json) {
      error.code = json.code;
    }
    throw error;
  }
}

function buildUrl(url, params) {
  const urlBuilder = new URL(url, window.location.href);
  if (params) {
    urlBuilder.search = new URLSearchParams(params).toString();
  }
  return urlBuilder.toString();
}

export function request({ url, method, params, body }) {
  const fullUrl = buildUrl(baseUrl + url, params);
  const options = {
    method,
    headers: { "content-type": "application/json" },
    body: body ? JSON.stringify(body) : body,
  };

  return fetch(fullUrl, options).then(parseResponse);
}

export const Api = {
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

  getUserPolls: async (token, userId) => {
    const pollsWithAnswers = await request({
      method: "GET",
      url: `/polls/user/${userId}`,
      params: { token },
    });
    return pollsWithAnswers.map(p => p.poll);
  },

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
  
  getResults: async (token, pollId) =>
    request({
      method: "GET",
      url: "/polls/" + pollId + "/result",
      params: {token}
    }),

  startPoll: async(token, pollId) =>
    request({
      method: "PATCH",
      url: `/polls/start/${pollId}`,
      params: {token}
    }),

  stopPoll: async(token, pollId) =>
    request({
      method: "PATCH",
      url: `/polls/stop/${pollId}`,
      params: {token}
    }),
  openQuestion: async(token, qid) =>
    request({
      method: "PATCH",
      url: `/questions/start/${qid}`,
      params: {token}
    }),

  closeQuestion: async(token, qid) =>
    request({
      method: "PATCH",
      url: `/questions/stop/${qid}`,
      params: {token}
    }),
  editQuestion: async(token, qid, content) =>
    request({
      method: "PATCH",
      url: `/questions/${qid}`,
      params: {token},
      body: {content}
    }),
};

export default Api;
