import React from "react";
import { Route, Switch } from "react-router-dom";
import PollDetails from "./PollDetails/PollDetails";
import { PollList } from "./PollList/PollList";
import { NewPoll } from "./NewPoll/NewPoll";

const Polls = () => (
  <Switch>
    <Route exact path="/polls/new" component={NewPoll} />
    <Route
      path={"/polls/:id"}
      render={({ match }) => <PollDetails pollId={match.params.id} />}
    />
    <Route component={PollList} />
  </Switch>
);

export default Polls;
