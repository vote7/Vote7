import React, { useContext, useEffect, useState } from "react";
import Api from "../../api/Api";
import { RootContext } from "../../app/RootContext";
import { Link } from "react-router-dom";
import { SimpleList } from "../../shared/SimpleList";

const PollList = () => {
  const { user, token } = useContext(RootContext);
  const [polls, setPolls] = useState([]);

  useEffect(() => {
    Api.getUserPolls(token, user.id).then(setPolls);
  }, []);

  return (
    <>
      <div className="d-flex align-items-center mt-5 mb-3">
        <h1 className="m-0">Polls</h1>
        <Link className="ml-auto btn btn-primary" to="/polls/new">
          New poll
        </Link>
      </div>
      <SimpleList
        elements={polls}
        keyFunc={poll => poll.id}
        titleFunc={poll => poll.name}
        descriptionFunc={poll => poll.description}
        linkFunc={poll => `/polls/${poll.id}`}
      />
    </>
  );
};

export default PollList;
