import React, { useContext } from "react";
import { RootContext } from "../RootContext";
import { Redirect, Route } from "react-router-dom";

const ConditionalRoute = ({ condition, elseRedirectTo, render, ...rest }) => (
  <Route
    {...rest}
    render={props =>
      condition ? render(props) : <Redirect to={elseRedirectTo} />
    }
  />
);

export const AuthenticatedRoute = ({ ...rest }) => {
  const { isAuthenticated } = useContext(RootContext);
  return (
    <ConditionalRoute
      condition={isAuthenticated}
      elseRedirectTo="/login"
      {...rest}
    />
  );
};

export const UnauthenticatedRoute = ({ ...rest }) => {
  const { isAuthenticated } = useContext(RootContext);
  return (
    <ConditionalRoute
      condition={!isAuthenticated}
      elseRedirectTo="/"
      {...rest}
    />
  );
};
