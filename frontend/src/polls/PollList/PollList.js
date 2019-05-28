import React, { useEffect, useState, useContext } from "react";
import { Link } from "react-router-dom";
import ApiMocks from "../../api/ApiMocks";
import NewPoll from "../NewPoll/NewPoll"
import Api from "../../api/Api";
import { RootContext } from "../../app/RootContext";

const ListElement = ({ poll }) => (
  <div className="border-bottom py-2">
    <Link to={"/polls/" + poll.id}>{poll.name}</Link>
  </div>
);

const List = ({ polls }) =>{
  return polls.map(poll => <ListElement key={poll} poll={poll.poll} />);
}
  

const PollList = () => {
  const [polls, setPolls] = useState([]);
  const [showList, setShowList] = useState(true);
  const { user, token } = useContext(RootContext)
  
  useEffect(() => {
    Api.getPolls(token, user.id).then(setPolls);
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
