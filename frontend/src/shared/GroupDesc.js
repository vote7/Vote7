import React from "react";

export const GroupDesc = ({ group }) => (
  <div>
    {group.name} <span style={{ color: "grey" }}>({group.users.length})</span>
  </div>
);
