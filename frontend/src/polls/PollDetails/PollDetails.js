import React, { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import {
  arrayMove,
  SortableContainer,
  SortableElement,
  SortableHandle,
} from "react-sortable-hoc";
import { faGripLines } from "@fortawesome/free-solid-svg-icons/faGripLines";
import ApiMocks from "../../api/ApiMocks";

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
            <FontAwesomeIcon icon={faTimes} />
          </button>
        </div>
        <div className="small">
          {question.open ? "(open question)" : "(closed question)"}
        </div>
        {!question.open && (
          <ul className="mt-3 mb-0">
            {question.answers.map(answer => (
              <li>{answer}</li>
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

  useEffect(() => {
    ApiMocks.getPoll(pollId).then(setPoll);
  }, [pollId]);

  if (!poll) return null;

  return (
    <>
      <div className="d-flex align-items-center mt-5 mb-3">
        <h1 className="m-0">{poll.name}</h1>
      </div>
      <Questions pollId={pollId} />
    </>
  );
};

export default PollDetails;
