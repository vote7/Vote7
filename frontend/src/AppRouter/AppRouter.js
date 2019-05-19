import React from "react";
import { Redirect, Route, Router, Switch } from "react-router-dom";
import HomePage from "../pages/HomePage/HomePage";
import LoginPage from "../pages/LoginPage/LoginPage";
import RegisterPage from "../pages/RegisterPage/RegisterPage";
import history from "./history";
import {
  AuthenticatedRoute,
  UnauthenticatedRoute,
} from "../components/ConditionalRoutes";
import NavBar from "../components/NavBar/NavBar";

const NotFound = () => <Redirect to="/" />;

const AppRouter = () => (
  <Router history={history}>
    <NavBar />
    <main role="main" className="container">
      <Switch>
        <UnauthenticatedRoute path="/login" render={() => <LoginPage />} />
        <UnauthenticatedRoute
          path="/register"
          render={() => <RegisterPage />}
        />
        <AuthenticatedRoute exact path="/" render={() => <HomePage />} />
        <Route render={() => <NotFound />} />
      </Switch>
    </main>
  </Router>
);

export default AppRouter;
