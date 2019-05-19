import React, { createContext, useState, useEffect } from "react";
import Api from "../api/Api";

export const RootContext = createContext(undefined);

const useToken = () => {
  const initialToken = () => window.localStorage.getItem("token") || null;
  const [token, setToken] = useState(initialToken);

  useEffect(() => {
    window.localStorage.setItem("token", token || "");
  }, [token]);

  return [token, setToken];
};

const useUser = token => {
  const [user, setUser] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    if (token) {
      Api.getCurrentUser(token).then(
        u => {
          setUser(u);
          setIsLoading(false);
        },
        error => {
          if (error.code === "USER_NOT_LOGGED") {
            setUser(null);
            setIsLoading(false);
          }
        },
      );
    } else {
      setUser(null);
      setIsLoading(false);
    }
  }, [token]);

  return { isLoading, user };
};

export const RootContextProvider = ({ children }) => {
  const [token, setToken] = useToken();
  const { isLoading, user } = useUser(token);

  const isAuthenticated = user != null;

  const authenticateWithToken = setToken;

  const logout = () => {
    setToken(null);
  };

  const context = {
    token,
    isLoading,
    user,
    isAuthenticated,
    authenticateWithToken,
    logout,
  };

  return (
    <RootContext.Provider value={context}>{children}</RootContext.Provider>
  );
};
