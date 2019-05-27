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
  const [description, setDescription] = useState("");
  const [secretaryId, setSecretaryId] = useState(1);
  const [chairmanId, setChairmanId] = useState(1);
  const [groupId, setGroupId] = useState(1);

  const onSubmit = event => {
    event.preventDefault();
    addPoll({name: pollName,
              description: description,
              secretaryId: secretaryId,
              chairmanId: chairmanId,
              mutable: true,
              groupId: groupId})
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
        <Form.Control
          type="text"
          placeholder="description"
          size="lg"
          value={description}
          onChange={event => setDescription(event.target.value)}
        />
        <Form.Control
          type="text"
          required
          placeholder="Secretary id"
          size="lg"
          value={secretaryId}
          onChange={event => setSecretaryId(event.target.value)}
        />
        <Form.Control
          type="text"
          required
          placeholder="Chairman id"
          size="lg"
          value={chairmanId}
          onChange={event => setChairmanId(event.target.value)}
        />
        <Form.Control
          type="text"
          required
          placeholder="Group id"
          size="lg"
          value={groupId}
          onChange={event => setGroupId(event.target.value)}
        />
      </Form.Group>
      <Button className="w-100" size="lg" variant="primary" type="submit">
        Add
      </Button>
    </Form>
  );
};

const NewPoll = ({hide}) => {
  const { token } = useContext(RootContext) 
  const addPoll = poll => {
    Api.createPoll(token, poll).then(hide)
  };

  return (
    <CenteredFormContainer title="New Poll">
      <NewPollForm addPoll={addPoll} />
    </CenteredFormContainer>
  );
};

export default NewPoll;
