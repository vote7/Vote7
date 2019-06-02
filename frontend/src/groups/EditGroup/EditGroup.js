import React, { useContext, useEffect, useState } from "react";
import { RootContext } from "../../app/RootContext";
import { GroupApi } from "../../api/GroupApi";
import { FormGroup } from "../../shared/forms/FormGroup";
import { ChooseUserInput } from "../../shared/forms/ChooseUserInput";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { UserDesc } from "../../shared/UserDesc";
import { Redirect } from "react-router-dom";

const EditableUserList = ({ users, onAdd, onRemove }) => (
  <div className="list-group">
    {users.map(user => (
      <div
        className="list-group-item d-flex align-items-center py-2"
        key={user.id}
      >
        <UserDesc user={user} />
        <button
          onClick={() => onRemove(user.id)}
          className="btn btn-sm btn-link ml-auto"
        >
          <FontAwesomeIcon icon={faTimes} />
        </button>
      </div>
    ))}
    <div className="list-group-item p-2">
      <ChooseUserInput userId={null} onChange={onAdd} placeholder="Add user..." />
    </div>
  </div>
);

export const EditGroup = ({ groupId }) => {
  const { token } = useContext(RootContext);
  const [group, setGroup] = useState(null);
  const [done, setDone] = useState(false);

  const loadGroup = () => {
    GroupApi.get(token, groupId).then(setGroup);
  };

  useEffect(loadGroup, []);

  const deleteGroup = () => {
    if (window.confirm("Delete group?")) {
      GroupApi.delete(token, groupId).then(() => setDone(true));
    }
  };

  const addUser = userId => {
    GroupApi.addUser(token, groupId, userId).then(loadGroup);
  };

  const removeUser = userId => {
    GroupApi.removeUser(token, groupId, userId).then(loadGroup);
  };

  if (done) return <Redirect to="/groups" />;

  if (!group) return null;

  return (
    <>
      <div className="d-flex align-items-center mt-5 mb-3">
        <h2 className="m-0">Group "{group.name}"</h2>
        <button className="ml-auto btn btn-danger" onClick={deleteGroup}>
          Delete
        </button>
      </div>

      <FormGroup label="Name">
        <input
          type="text"
          className="form-control"
          readOnly
          value={group.name}
        />
      </FormGroup>
      <FormGroup label="Description">
        <input
          type="text"
          className="form-control"
          readOnly
          value={group.description}
        />
      </FormGroup>
      <FormGroup label="Members">
        <EditableUserList
          users={group.users}
          onAdd={addUser}
          onRemove={removeUser}
        />
      </FormGroup>
    </>
  );
};
