import React, { useContext } from "react";
import { Redirect, Route, Router, Switch } from "react-router-dom";
import { AuthenticatedRoute, UnauthenticatedRoute } from "./ConditionalRoutes";
import NavBar from "../NavBar/NavBar";
import Login from "../../login/Login";
import Register from "../../registration/Register";
import { createBrowserHistory } from "history";
import { RootContext } from "../RootContext";
import Polls from "../../polls/Polls";
import Groups from "../../groups/Groups";

const history = createBrowserHistory();

const NotFound = () => <Redirect to="/" />;
const Home = () => <Redirect to="/polls" />;

const Routes = () => (
  <Switch>
    <AuthenticatedRoute exact path="/" component={Home} />
    <AuthenticatedRoute path="/polls" component={Polls} />
    <AuthenticatedRoute path="/groups" component={Groups} />
    <UnauthenticatedRoute path="/login" component={Login} />
    <UnauthenticatedRoute path="/register" component={Register} />
    <Route component={NotFound} />
  </Switch>
);

const AppRouter = () => {
  const { isLoading } = useContext(RootContext);
  return (
    <Router history={history}>
      <NavBar />
      <main role="main" className="container">
        {!isLoading && <Routes />}
      </main>
    </Router>
  );
};

export default AppRouter;
