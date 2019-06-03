import React, { useContext, useEffect, useState } from "react";
import { UserApi } from "../../api/UserApi";
import { RootContext } from "../../app/RootContext";
import { UserDesc } from "../UserDesc";
import { ChooseInput } from "./ChooseInput";

export const ChooseUserInput = props => {
  const { token } = useContext(RootContext);
  const [allUsers, setAllUsers] = useState([]);

  const filterUser = (user, search) => {
    const fullName = (user.name + " " + user.surname).toLowerCase();
    const email = user.email.toLowerCase();

    return (
      fullName.includes(search.toLowerCase()) ||
      email.includes(search.toLowerCase())
    );
  };

  useEffect(() => {
    UserApi.getAll(token).then(setAllUsers);
  }, []);

  return (
    <ChooseInput
      renderItem={item => <UserDesc user={item} />}
      filterItem={filterUser}
      items={allUsers}
      {...props}
    />
  );
};
