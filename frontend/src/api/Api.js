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

  getPolls: token =>
    request({
      method: "GET",
      url: "/polls/",
      params: {token}
    })
};

export default Api;
