import React, { createContext, useState, useEffect } from "react";
import Api from "./Api";

export const RootContext = createContext(undefined);

const useToken = () => {
  const initialToken = () => window.localStorage.getItem("token") || null;
  const [token, setToken] = useState(initialToken);

  useEffect(() => {
    window.localStorage.setItem("token", token || "");
  }, [token]);

  return [token, setToken];
};

const getUser = token => {
  const [user, setUser] = useState(null);

  useEffect(() => {
    if (token) {
      Api.getCurrentUser(token).then(setUser, () => setUser(null));
    } else {
      setUser(null);
    }
  }, [token]);

  return user;
};

export const RootContextProvider = ({ children }) => {
  const [token, setToken] = useToken();
  const user = getUser(token);

  const isAuthenticated = user != null;

  const authenticateWithToken = setToken;

  const logout = () => {
    setToken(null);
  };

  const context = {
    token,
    user,
    isAuthenticated,
    authenticateWithToken,
    logout,
  };

  return (
    <RootContext.Provider value={context}>{children}</RootContext.Provider>
  );
};
