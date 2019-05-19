const baseUrl = "http://localhost:8080/";

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

function buildUrl(url, queryParams) {
  const urlBuilder = new URL(url);
  if (queryParams) {
    urlBuilder.search = new URLSearchParams(queryParams).toString();
  }
  return urlBuilder.toString();
}

function request(url, { queryParams, ...options }) {
  return fetch(buildUrl(url, queryParams), options)
    .then(checkStatus)
    .then(parseJSON);
}

const Api = {
  async login(credentials) {
    const { token } = await request(baseUrl + "users/login", {
      method: "POST",
      headers: { "content-type": "application/json" },
      body: JSON.stringify(credentials),
    });
    return token;
  },

  async register(user) {
    const { token } = await request(baseUrl + "users/register", {
      method: "POST",
      headers: { "content-type": "application/json" },
      body: JSON.stringify(user),
    });
    return token;
  },

  getCurrentUser(token) {
    return request(baseUrl + "users/me?", {
      method: "GET",
      queryParams: { token },
      headers: { "content-type": "application/json" },
    });
  },
};

export default Api;
