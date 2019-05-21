import React, { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit, faTimes } from "@fortawesome/free-solid-svg-icons";
import {
  SortableContainer,
  SortableElement,
  SortableHandle,
} from "react-sortable-hoc";
import { faGripLines } from "@fortawesome/free-solid-svg-icons/faGripLines";
import ApiMocks from "../../api/ApiMocks";
import arrayMove from "array-move";
import Form from "react-bootstrap/Form";

const DragHandle = SortableHandle(() => (
  <span className="btn btn-link" style={{ cursor: "ns-resize" }}>
    <FontAwesomeIcon icon={faGripLines} />
  </span>
));

const Question = ({ question, questionIndex }) => (
  <div className="border-bottom py-4 bg-white">
    <div className="d-flex">
      <DragHandle />
      <div className="flex-grow-1">
        <div className="d-flex flex-grow-1 align-items-center">
          <div className="font-weight-bold">
            {questionIndex + 1}. {question.content}
          </div>
          <button className="ml-auto btn btn-sm btn-link">
            <FontAwesomeIcon icon={faEdit} />
          </button>
          <button className="btn btn-sm btn-link">
            <FontAwesomeIcon icon={faTimes} />
          </button>
        </div>
        <div className="small">
          {question.open ? "(open question)" : "(closed question)"}
        </div>
        {!question.open && (
          <ul className="mt-3 mb-0">
            {question.answers.map((answer, answerIndex) => (
              <li key={answerIndex}>{answer}</li>
            ))}
          </ul>
        )}
      </div>
    </div>
  </div>
);

const SortableQuestion = SortableElement(Question);

const SortableQuestionList = SortableContainer(({ questions }) => (
  <div>
    {questions.map((question, questionIndex) => (
      <SortableQuestion
        key={`item-${questionIndex}`}
        index={questionIndex}
        questionIndex={questionIndex}
        question={question}
      />
    ))}
  </div>
));

const Questions = ({ pollId }) => {
  const [questions, setQuestions] = useState([]);

  useEffect(() => {
    ApiMocks.getQuestions(pollId).then(setQuestions);
  }, [pollId]);

  const onSortEnd = ({ oldIndex, newIndex }) => {
    setQuestions(arrayMove(questions, oldIndex, newIndex));
    console.log(questions);
  };

  return (
    <SortableQuestionList
      questions={questions}
      onSortEnd={onSortEnd}
      useDragHandle
    />
  );
};

const PollDetails = ({ pollId }) => {
  const [poll, setPoll] = useState(null);
  const [editName, setEditName] = useState(true);

  useEffect(() => {
    ApiMocks.getPoll(pollId).then(setPoll);
  }, [pollId]);

  if (!poll) return null;

  const hideEditName = () => {
    if(editName) {
      ApiMocks.editPollName(poll.id, poll.name)
    }
    setEditName(!editName)
  }

  return (
    <>
      <div className="d-flex align-items-center mt-5 mb-3">
        {
          editName ?
            <h1 className="m-0">{poll.name}</h1>
            :
            <Form>
              <Form.Group controlId="pollNameEdit">
                <Form.Control 
                  type="text"  
                  value={poll.name}
                  onChange={event => setPoll({name: event.target.value})}
                />
              </Form.Group>
            </Form>
        }
        
        <button className="ml-2 btn btn-link" onClick={() => hideEditName()}>
          <FontAwesomeIcon icon={faEdit} />
        </button>
      </div>
      <Questions pollId={pollId} />
    </>
  );
};

export default PollDetails;
