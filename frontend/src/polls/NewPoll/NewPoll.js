import React, { useContext, useState } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Api from "../../api/Api";
import { Link } from "react-router-dom";
import { RootContext } from "../../app/RootContext";
import CenteredFormContainer from "../../shared/CenteredFormContainer";
import ApiMocks from "../../api/ApiMocks";

const NewPollForm = ({addPoll}) => {
  const [pollName, setPollName] = useState("");

  const onSubmit = event => {
    event.preventDefault();
    addPoll({name: pollName})
  };

  return (
    <Form onSubmit={onSubmit}>
      <Form.Group>
        <Form.Control
          type="text"
          required
          placeholder="Name"
          size="lg"
          value={pollName}
          onChange={event => setPollName(event.target.value)}
        />
      </Form.Group>
      <Button className="w-100" size="lg" variant="primary" type="submit">
        Add
      </Button>
    </Form>
  );
};

const NewPoll = ({hide}) => {
  const addPoll = poll => {
    ApiMocks.addPolls(poll).then(hide)
  };

  return (
    <CenteredFormContainer title="Register">
      <NewPollForm addPoll={addPoll} />
    </CenteredFormContainer>
  );
};

export default NewPoll;
