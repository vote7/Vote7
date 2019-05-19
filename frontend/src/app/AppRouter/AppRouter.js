import React from "react";
import { Redirect, Route, Router, Switch } from "react-router-dom";
import { AuthenticatedRoute, UnauthenticatedRoute } from "./ConditionalRoutes";
import NavBar from "../NavBar/NavBar";
import Login from "../../login/Login";
import Home from "../../home/Home";
import Register from "../../registration/Register";
import { createBrowserHistory } from "history";

const history = createBrowserHistory();

const NotFound = () => <Redirect to="/" />;

const AppRouter = () => (
  <Router history={history}>
    <NavBar />
    <main role="main" className="container">
      <Switch>
        <AuthenticatedRoute exact path="/" component={Home} />
        <UnauthenticatedRoute path="/login" component={Login} />
        <UnauthenticatedRoute path="/register" component={Register} />
        <Route component={NotFound} />
      </Switch>
    </main>
  </Router>
);

export default AppRouter;
