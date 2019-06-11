import React from "react";
import { Route, Switch } from "react-router-dom";
import PollDetails from "./PollDetails/PollDetails";
import { PollList } from "./PollList/PollList";
import { NewPoll } from "./NewPoll/NewPoll";
import PollResult from "./PollResult/PollResult";

const Polls = () => (
  <Switch>
    <Route 
      path={"/polls/:id/results"}
      render={({ match }) => <PollResult pollId={match.params.id} />} 
    />
    <Route exact path="/polls/new" component={NewPoll} />
    <Route
      path={"/polls/:id"}
      render={({ match }) => <PollDetails pollId={match.params.id} />}
    />
    <Route component={PollList} />
  </Switch>
);

export default Polls;
