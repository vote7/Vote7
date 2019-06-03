import React from "react";

export const UserDesc = ({ user }) => (
  <div>
    {user.name} {user.surname}{" "}
    <span style={{ color: "grey" }}>{user.email}</span>
  </div>
);
