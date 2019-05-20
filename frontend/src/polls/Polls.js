import React from "react";
import { Route } from "react-router-dom";
import PollList from "./PollList/PollList";
import PollDetails from "./PollDetails/PollDetails";

const Polls = () => (
  <>
    <Route exact path="/polls" component={PollList} />
    <Route
      path={"/polls/:id"}
      render={({ match }) => <PollDetails pollId={match.params.id} />}
    />
  </>
);

export default Polls;
