import { Link } from "react-router-dom";
import React from "react";

export const SimpleList = ({
  items,
  linkFunc,
  titleFunc,
  descriptionFunc,
  keyFunc,
}) => (
  <div className="list-group">
    {items.map(item => (
      <Link
        key={keyFunc(item)}
        to={linkFunc(item)}
        className="list-group-item list-group-item-action"
      >
        <strong>{titleFunc(item)}</strong>
        <br />
        {descriptionFunc && descriptionFunc(item)}
      </Link>
    ))}
  </div>
);
