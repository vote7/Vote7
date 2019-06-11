import { Link } from "react-router-dom";
import React from "react";

export const SimpleList = ({
  items = [],
  linkFunc,
  titleFunc,
  descriptionFunc = () => false,
  keyFunc,
  buttonName,
  buttonAction,
  buttonResult
}) => (
  <div className="list-group">
    {items.map(item => (
      <div className="d-flex flex-row">
        <Link
          key={keyFunc(item)}
          to={linkFunc(item)}
          className="list-group-item list-group-item-action"
        >
          <strong>{titleFunc(item)}</strong>
          <br />
          {descriptionFunc(item)}
        </Link>
        {buttonName ? 
          <Link className="m-1 btn btn-primary" to="/polls">{buttonName}</Link> 
          : ''}
        {buttonResult ? 
          <Link className="m-1 btn btn-primary" to={"/polls/" + keyFunc(item) +"/results"}>Result</Link> 
          : ''}
      </div>
    ))}
  </div>
);
