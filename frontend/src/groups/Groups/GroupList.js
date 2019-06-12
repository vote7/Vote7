import React, { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { RootContext } from "../../app/RootContext";
import { GroupApi } from "../../api/GroupApi";
import { SimpleList } from "../../shared/SimpleList";

const GroupList = () => {
  const [groups, setGroups] = useState([]);
  const { user, token } = useContext(RootContext);

  useEffect(() => {
    GroupApi.getUserGroups(token, user.id).then(setGroups);
  }, []);

  return (
    <>
      <div className="d-flex align-items-center mt-5 mb-3">
        <h2 className="m-0">Groups</h2>
        <Link to="/groups/new" className="ml-auto btn btn-primary">
          New group
        </Link>
      </div>
      <SimpleList
        items={groups}
        keyFunc={group => group.id}
        titleFunc={group => group.name}
        descriptionFunc={group => group.description}
        linkFunc={group => `/groups/${group.id}`}
      />
    </>
  );
};

export default GroupList;
