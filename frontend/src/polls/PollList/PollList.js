import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import ApiMocks from "../../api/ApiMocks";
import NewPoll from "../NewPoll/NewPoll"

const ListElement = ({ poll }) => (
  <div className="border-bottom py-2">
    <Link to={"/polls/" + poll.id}>{poll.name}</Link>
  </div>
);

const List = ({ polls }) =>
  polls.map(poll => <ListElement key={poll.id} poll={poll} />);

const PollList = () => {
  const [polls, setPolls] = useState([]);
  const [showList, setShowList] = useState(true);

  useEffect(() => {
    ApiMocks.getPolls().then(setPolls);
  }, []);

  return (
    showList ?
    <>
      <div className="d-flex align-items-center mt-5 mb-3">
        <h1 className="m-0">Polls</h1>
        <button className="ml-auto btn btn-primary" onClick={() => setShowList(false)}>New poll</button>
      </div>
      <List polls={polls} />
    </>
    :
    <>
      <NewPoll hide={() => setShowList(true)}/>
    </>
  );
};

export default PollList;
