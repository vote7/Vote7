import React, { useContext, useEffect, useState } from "react";
import { RootContext } from "../app/RootContext";
import { GroupApi } from "../api/GroupApi";
import { ChooseInput } from "./ChooseInput";
import { GroupDesc } from "./GroupDesc";

export const ChooseGroupInput = props => {
  const { token } = useContext(RootContext);
  const [groups, setGroups] = useState([]);

  const filterGroup = (group, search) =>
    group.name.toLowerCase().includes(search.toLowerCase());

  useEffect(() => {
    GroupApi.getAll(token).then(setGroups);
  }, []);

  return (
    <ChooseInput
      items={groups}
      filterItem={filterGroup}
      renderItem={item => <GroupDesc group={item} />}
      {...props}
    />
  );
};
