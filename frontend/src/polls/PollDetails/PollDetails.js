import React, { useContext, useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  SortableContainer,
  SortableElement,
  SortableHandle,
} from "react-sortable-hoc";
import { faGripLines } from "@fortawesome/free-solid-svg-icons/faGripLines";
import Form from "react-bootstrap/Form";
import { RootContext } from "../../app/RootContext";
import Api from "../../api/Api";
import Button from "react-bootstrap/Button";
import CenteredFormContainer from "../../shared/forms/CenteredFormContainer";
import { Col, Row } from "react-bootstrap";
import { Link } from "react-router-dom";

const DragHandle = SortableHandle(() => (
  <span className="btn btn-link" style={{ cursor: "ns-resize" }}>
    <FontAwesomeIcon icon={faGripLines} />
  </span>
));

const statusDescriptions = {
  OPEN: "Ongoing",
  CLOSED: "Finished",
  DRAFT: "Draft",
};

const Question = ({
  question,
  questionIndex,
  removeQuestion,
  openQuestion,
  closeQuestion,
  pollStatus,
  secretary,
  chairman,
}) => (
  <div className="list-group-item p-3 bg-white">
    <div className="d-flex">
      {question.status === "DRAFT" && <DragHandle />}
      <div className="flex-grow-1">
        <div className="d-flex align-items-start">
          <div className="mr-1 mt-1">{questionIndex + 1}.</div>
          <div className="mt-1 font-weight-bold mr-auto">
            {question.content}
          </div>
          <div className="mr-1 mt-1">{statusDescriptions[question.status]}</div>
          {question.status === "DRAFT" && pollStatus === "OPEN" && chairman && (
            <button
              className="btn btn-sm btn-primary ml-1"
              onClick={openQuestion}
            >
              Start
            </button>
          )}
          {question.status === "OPEN" && pollStatus === "OPEN" && chairman && (
            <button
              className="btn btn-sm btn-primary ml-1"
              onClick={closeQuestion}
            >
              Finish
            </button>
          )}
          {question.status === "DRAFT" && pollStatus !== "CLOSED" && secretary && (
            <button
              className="btn btn-sm btn-secondary ml-1"
              onClick={removeQuestion}
            >
              Delete
            </button>
          )}
        </div>
        {question.open ? (
          <small>Open question</small>
        ) : (
          <ul className="mt-3 mb-0">
            {question.answers.map((answer, answerIndex) => (
              <li key={answerIndex}>{answer.content}</li>
            ))}
          </ul>
        )}
      </div>
    </div>
  </div>
);

const SortableQuestion = SortableElement(Question);

const SortableQuestionList = SortableContainer(
  ({
    questions,
    removeQuestion,
    openQuestion,
    closeQuestion,
    pollStatus,
    secretary,
    chairman,
  }) => (
    <div className="list-group">
      {questions
        .sort((q1, q2) => q1.order > q2.order)
        .map((question, questionIndex) => (
          <SortableQuestion
            key={`item-${questionIndex}`}
            index={questionIndex}
            questionIndex={questionIndex}
            question={question}
            removeQuestion={() => removeQuestion(question.id)}
            openQuestion={() => openQuestion(question.id)}
            closeQuestion={() => closeQuestion(question.id)}
            pollStatus={pollStatus}
            secretary={secretary}
            chairman={chairman}
          />
        ))}
    </div>
  ),
);

const Questions = ({
  pollId,
  pollStatus,
  statusFilter,
  secretary,
  chairman,
  rerender,
}) => {
  const [questions, setQuestions] = useState([]);
  const { token } = useContext(RootContext);

  useEffect(() => {
    Api.getQuestions(token, pollId).then(newQuestions =>
      setQuestions(newQuestions.filter(q => statusFilter.includes(q.status))),
    );
  }, [pollId]);

  const onSortEnd = ({ oldIndex, newIndex }) => {};

  return (
    <SortableQuestionList
      questions={questions}
      onSortEnd={onSortEnd}
      useDragHandle
      removeQuestion={qid => {
        Api.removeQuestion(token, qid)
          .then(() => Api.getQuestions(token, pollId))
          .then(res => setQuestions(res))
          .then(() => rerender());
      }}
      openQuestion={qid => Api.openQuestion(token, qid).then(() => rerender())}
      closeQuestion={qid =>
        Api.closeQuestion(token, qid).then(() => rerender())
      }
      pollStatus={pollStatus}
      secretary={secretary}
      chairman={chairman}
    />
  );
};

const PollDetails = ({ pollId }) => {
  const [poll, setPoll] = useState(null);
  const { token, user } = useContext(RootContext);
  const [newQuestion, setNewQuestion] = useState(false);
  const [isChairman, setIsChairman] = useState(false);
  const [isSecretary, setIsSecretary] = useState(false);

  const setPollStatus = status => setPoll({ ...poll, status });

  useEffect(() => {
    Api.getPoll(token, pollId).then(newPoll => {
      setPoll(newPoll);
      setIsChairman(user.id === newPoll.chairmanID);
      setIsSecretary(user.id === newPoll.secretaryID);
    });
  }, [pollId]);

  if (!poll) return null;

  const addQuestion = question => {
    Api.addQuestion(token, pollId, question).then(() => {
      Api.getPoll(token, pollId).then(setPoll);
    });
  };

  const rerender = () => {
    setPoll(null);
    Api.getPoll(token, pollId).then(newPoll => {
      setPoll(newPoll);
    });
  };

  const openPoll = () => {
    Api.startPoll(token, pollId).then(() => setPollStatus("OPEN"));
  };

  const closePoll = () => {
    Api.stopPoll(token, pollId).then(() => setPollStatus("CLOSED"));
  };

  return (
    <>
      <div className="d-flex align-items-center mt-5 mb-4">
        <h1 className="m-0">
          {poll.name}&nbsp;
          <small>({statusDescriptions[poll.status]})</small>
        </h1>

        <div className="ml-auto">
          {poll.status === "DRAFT" && isChairman && (
            <button className="mr-1 btn btn-primary" onClick={openPoll}>
              Start poll
            </button>
          )}
          {poll.status === "OPEN" && isChairman && (
            <button className="mr-1 btn btn-primary" onClick={closePoll}>
              Finish poll
            </button>
          )}
          {poll.status !== "DRAFT" && (
            <Link
              className="mr-1 btn btn-secondary"
              to={`/polls/${pollId}/results`}
            >
              Show results
            </Link>
          )}
        </div>
      </div>
      {newQuestion ? (
        <NewQuestion
          addQuestion={addQuestion}
          hideForm={() => setNewQuestion(false)}
        />
      ) : (
        <div className="row" style={{ minHeight: "50vh" }}>
          <div className="col-6 border-right">
            <h4 className="mb-3">Ongoing and finished</h4>
            <Questions
              pollId={pollId}
              pollStatus={poll.status}
              statusFilter={["OPEN", "CLOSED"]}
              secretary={isSecretary}
              chairman={isChairman}
              rerender={rerender}
            />
          </div>
          {poll.status !== "CLOSED" && (
            <div className="col-6">
              <div className="d-flex mb-3 align-items-center">
                <h4 className="mb-0">Drafts</h4>
                {isSecretary && (
                  <button
                    className="ml-auto btn btn-sm btn-primary"
                    onClick={() => setNewQuestion(true)}
                  >
                    Add Question
                  </button>
                )}
              </div>
              <Questions
                pollId={pollId}
                pollStatus={poll.status}
                statusFilter={["DRAFT"]}
                secretary={isSecretary}
                chairman={isChairman}
                rerender={rerender}
              />
            </div>
          )}
        </div>
      )}
    </>
  );
};

const EditableAnswerList = ({ answers, setAnswers }) => {
  const [internalAnswers, setInternalAnswers] = useState([...answers, ""]);

  const updateExternalAnswers = () => {
    setAnswers(internalAnswers.filter(a => a !== ""));
  };

  const setAnswer = (index, newAnswer) => {
    const newAnswers = [...internalAnswers];
    newAnswers[index] = newAnswer;
    if (index === newAnswers.length - 1 && newAnswer !== "") {
      newAnswers.push("");
    }
    setInternalAnswers(newAnswers);
    updateExternalAnswers();
  };

  const filterAnswers = () => {
    const newAnswers = [...internalAnswers.filter(a => a !== ""), ""];
    setInternalAnswers(newAnswers);
    updateExternalAnswers();
  };

  return (
    <div>
      {internalAnswers.map((answer, index) => (
        <Form.Control
          key={index}
          type="text"
          value={answer}
          onChange={event => setAnswer(index, event.target.value)}
          onBlur={filterAnswers}
        />
      ))}
    </div>
  );
};

const NewQuestion = ({ addQuestion, hideForm }) => {
  const [content, setContent] = useState("");
  const [open, setOpen] = useState(false);
  const [answers, setAnswers] = useState([]);

  const onSubmit = event => {
    event.preventDefault();
    if (content !== "" && (open || answers.length > 0)) {
      addQuestion({ content, open, answers: !open ? answers : [] });
      hideForm();
    }
  };

  return (
    <CenteredFormContainer>
      <h3 className="mt-5 mb-3">Add question</h3>
      <Form onSubmit={onSubmit}>
        <Form.Group>
          <div>Question</div>
          <Form.Control
            type="text"
            placeholder="Question"
            value={content}
            onChange={event => setContent(event.target.value)}
          />
        </Form.Group>
        <fieldset>
          <Form.Group as={Row}>
            <Col>
              <Form.Check
                type="radio"
                label="Open question"
                name="questionType"
                id="questionTypeOpen"
                checked={open}
                onChange={event => setOpen(event.target.value === "on")}
              />
              <Form.Check
                type="radio"
                label="Closed question"
                name="questionType"
                id="questionTypeClosed"
                checked={!open}
                onChange={event => setOpen(event.target.value !== "on")}
              />
            </Col>
          </Form.Group>
        </fieldset>
        {!open && (
          <Form.Group>
            Answers:
            <EditableAnswerList answers={answers} setAnswers={setAnswers} />
          </Form.Group>
        )}
        <Button variant="primary" type="submit">
          Save
        </Button>
        &nbsp;
        <Button variant="secondary" type="button" onClick={hideForm}>
          Cancel
        </Button>
      </Form>
    </CenteredFormContainer>
  );
};

export default PollDetails;
