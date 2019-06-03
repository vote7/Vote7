import React from "react";
import { Route, Switch } from "react-router-dom";
import GroupList from "./Groups/GroupList";
import { NewGroup } from "./NewGroup/NewGroup";
import { EditGroup } from "./EditGroup/EditGroup";

const Groups = () => (
  <Switch>
    <Route exact path="/groups/new" component={NewGroup} />
    <Route
      exact
      path={"/groups/:id"}
      render={({ match }) => <EditGroup groupId={match.params.id} />}
    />
    <Route component={GroupList} />
  </Switch>
);

export default Groups;
