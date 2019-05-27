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

const Question = ({ question, questionIndex, setQuestion, removeQuestion}) => {
  const [content, setContent] = useState(question.content)
  const [questionOpen, setQuestionOpen] = useState(question.open)
  const [editable, setEditable] = useState(false)
  
  const editClick = () => {
    if(editable) {
      setQuestion({content: content, order: question.order, questionOpen: questionOpen})
    }
    setEditable(!editable)
  }
  
  const removeClick = () => {
    ApiMocks.removeQuestion({order: questionIndex + 1})
  }

  return (
    <div className="border-bottom py-4 bg-white">
      <div className="d-flex">
        <DragHandle />
        <div className="flex-grow-1">
          <div className="d-flex flex-grow-1 align-items-center">
            <div className="font-weight-bold">
              {questionIndex + 1}. 
              {editable ?
                <Form>
                  <Form.Group controlId="pollNameEdit">
                    <Form.Control 
                      type="text"  
                      value={content}
                      onChange={event => setContent(event.target.value)}
                    />
                  </Form.Group>
                </Form>
                : content}
            </div>
            <button onClick={editClick} className="ml-auto btn btn-sm btn-link">
              <FontAwesomeIcon icon={faEdit} />
            </button>
            <button onClick={removeClick} className="btn btn-sm btn-link">
              <FontAwesomeIcon icon={faTimes} />
            </button>
          </div>
          <div className="small">
            {
              editable ?
              <Form>
                <Form.Group controlId="pollNameEdit">
                  Open question: <Form.Check
                    type="checkbox"  
                    checked={questionOpen}
                    onChange={event => setQuestionOpen(!questionOpen)}
                  />
                </Form.Group>
              </Form>
              : questionOpen ? "(open question)" : "(closed question)"
            }
            {}
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
  )
}

const SortableQuestion = SortableElement(Question);

const SortableQuestionList = SortableContainer(({ questions, setQuestion, removeQuestion }) => (
  <div>
    {questions.sort((q1, q2) => q1.order > q2.order).map((question, questionIndex) => (
      <SortableQuestion
        key={`item-${questionIndex}`}
        index={questionIndex}
        questionIndex={questionIndex}
        question={question}
        setQuestion={setQuestion}
        removeQuestion={removeQuestion}
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
    
  };

  return (
    <SortableQuestionList
      questions={questions}
      onSortEnd={onSortEnd}
      useDragHandle
      setQuestion={(question) => ApiMocks.setQuestion(Object.assign(question, {pollId: pollId}))}
      removeQuestion={(question) => ApiMocks.removeQuestion(Object.assign(question, {pollId: pollId}))}
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
