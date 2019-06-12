import React, { useEffect, useState, useContext } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit, faTimes, faPlusCircle, faMinusCircle } from "@fortawesome/free-solid-svg-icons";
import {
  SortableContainer,
  SortableElement,
  SortableHandle,
} from "react-sortable-hoc";
import { faGripLines } from "@fortawesome/free-solid-svg-icons/faGripLines";
import ApiMocks from "../../api/ApiMocks";
import arrayMove from "array-move";
import Form from "react-bootstrap/Form";
import { RootContext } from "../../app/RootContext";
import Api from "../../api/Api"
import Button from "react-bootstrap/Button";
import CenteredFormContainer from "../../shared/forms/CenteredFormContainer";

const DragHandle = SortableHandle(() => (
  <span className="btn btn-link" style={{ cursor: "ns-resize" }}>
    <FontAwesomeIcon icon={faGripLines} />
  </span>
));

const Question = ({ question, questionIndex, removeQuestion, removeAnswer, openQuestion, closeQuestion, pollStatus, setQuestion, secretary, chairman}) => {
  const [content, setContent] = useState(question.content)
  const [questionOpen, setQuestionOpen] = useState(question.open)
  const [editable, setEditable] = useState(false)
  
  const editClick = () => {
    if(editable) {
      setQuestion({content: content, order: question.order, questionOpen: questionOpen})
    }
    setEditable(!editable)
  }

  const changeQuestionStatus = () => {
    if(question.status === "OPEN") {
      closeQuestion()
    } else {
      openQuestion()
    }
  }

  const removeClick = () => {
    removeQuestion()
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
                : `${content}(${question.status})`}
            </div>
            {pollStatus === "DRAFT" && secretary && question.status == "DRAFT" ?
              <button onClick={editClick} className="ml-auto btn btn-sm btn-link">
                <FontAwesomeIcon icon={faEdit} />
              </button>
              : <></>
            }
            {
              pollStatus !== "CLOSED" && question.status === "DRAFT" && secretary ?
                <button onClick={removeClick} className="btn btn-sm btn-link">
                  <FontAwesomeIcon icon={faTimes} />
                </button> : <></>
            }
            { (question.status === "CLOSED" || pollStatus === "CLOSED" || !chairman) ?
                <></> :
                <button className="btn btn-primary" onClick={() => changeQuestionStatus()}>{question.status === "DRAFT" ? "Open" : "Close"}</button>
            }
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
                <li key={answerIndex}>{answer.content}
                {pollStatus === "DRAFT" && question.status === "DRAFT" && secretary ?
                  <button onClick={() => removeAnswer(answer.id)} className="btn btn-sm">
                    <FontAwesomeIcon icon={faMinusCircle} />
                  </button>
                  : <></>
                }
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>
    </div>
  )
}

const SortableQuestion = SortableElement(Question);

const SortableQuestionList = SortableContainer(({ questions, removeQuestion, removeAnswer, openQuestion, closeQuestion, pollStatus, setQuestion, secretary, chairman }) => (
  <div>
    
    {questions.sort((q1, q2) => q1.order > q2.order).map((question, questionIndex) => (
      <SortableQuestion
        key={`item-${questionIndex}`}
        index={questionIndex}
        questionIndex={questionIndex}
        question={question}
        removeQuestion={() => removeQuestion(question.id)}
        removeAnswer={(aid) => removeAnswer(question.id, aid)}
        openQuestion={() => openQuestion(question.id)}
        closeQuestion={()=> closeQuestion(question.id)}
        pollStatus={pollStatus}
        setQuestion={(content) => setQuestion(question.id, content)}
        secretary = {secretary}
        children = {chairman}
      />
    ))}
  </div>
));

const Questions = ({pollId, pollStatus, draft, secretary, chairman}) => {
  const [questions, setQuestions] = useState([]);
  const {token} = useContext(RootContext)

  useEffect(() => {
    Api.getQuestions(token, pollId).then((newQuestions) => setQuestions(newQuestions.filter((q) => (draft && q.status === "DRAFT") || (!draft && q.status !== "DRAFT"))));
  }, [pollId]);

  const onSortEnd = ({ oldIndex, newIndex }) => {
    
  };

  return (
    <SortableQuestionList
      questions={questions}
      onSortEnd={onSortEnd}
      useDragHandle
      removeQuestion = {(qid) => {
        Api.removeQuestion(token, qid).then(() => Api.getQuestions(token, pollId)).then((res) => setQuestions(res))
      }}
      removeAnswer = {(qid, aid) => Api.removeAnswer(token, qid, aid)}
      openQuestion = {(qid) => Api.openQuestion(token, qid)}
      closeQuestion = {(qid) => Api.closeQuestion(token, qid)}
      pollStatus = {pollStatus}
      setAnswer = {(qid, content) => {}}
      secretary = {secretary}
      children = {chairman}
    />
  );
};

const PollDetails = ({ pollId }) => {
  const [poll, setPoll] = useState(null);
  const [editName, setEditName] = useState(true);
  const {token, user} = useContext(RootContext)
  const [newQuestion, setNewQuestion] = useState(false)
  const [pollStatus, setPollStatus] = useState(null)
  const [chairman, setChairman] = useState(false)
  const [secretary, setSecretary] = useState(false)


  useEffect(() => {
    Api.getPoll(token, pollId).then((newPoll) => {setPoll(newPoll); setPollStatus(newPoll.status); setChairman(user.id === newPoll.chairmanID); setSecretary(user.id === newPoll.secretaryID);});
  }, [pollId]);

  if (!poll) return null;
  console.log(chairman)

  const hideEditName = () => {
    if(editName) {
      ApiMocks.editPollName(poll.id, poll.name)
    }
    setEditName(!editName)
  }

  const addQuestion = (question) => {
    Api.addQuestion(token, pollId, {content: question.content, open: question.open})
        //.then(() => question.answers.map(answer => Api.addAnswer(token, question.id, answer)))
        .then(Api.getPoll(token, pollId).then((newPoll) => {setPoll(newPoll); setPollStatus(newPoll.status)}));
  }

  const changePollState = () => {
    if(pollStatus === "DRAFT") {
      Api.startPoll(token, pollId).then(() => setPollStatus("OPEN"));
    } else {
      Api.stopPoll(token, pollId).then(() => setPollStatus("CLOSED"));
    }
  }

  return (
    <>
      <div className="d-flex align-items-center mt-5 mb-3">
        {
          editName ?
            <h1 className="m-0">{poll.name}<small>{`(${pollStatus})`}</small></h1>
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
        <div className="ml-auto">
          <button className="mr-3 btn btn-primary" onClick={() => setNewQuestion(!newQuestion)}>{newQuestion ? "Close":"New Question"}</button>
          {pollStatus === "CLOSED" || !chairman? 
            <></>
            :
            <button className="mr-3 btn btn-primary" onClick={() => changePollState()}>{pollStatus === "DRAFT" ? "Open" : "Close"}</button>
          }
          
        </div>
        
      </div>
      {newQuestion && secretary ?
      <NewQuestion addQuestion={addQuestion} hideForm={() => setNewQuestion(false)} />
      :
      <div className="d-flex">
        <div className="col-6">
          <Questions pollId={pollId} pollStatus={pollStatus} draft={false} secretary={secretary} chairman={chairman}/>
        </div>
        <div className="col-6">
          <Questions pollId={pollId} pollStatus={pollStatus} draft={true} secretary={secretary} chairman={chairman}/>
        </div>
      </div>
      }
    </>
  );
};

const NewQuestion = ({addQuestion, hideForm}) => {
  const [content, setContent] = useState("")
  const [open, setOpen] = useState(false)
  const [answers, setAnswers] = useState([])

  const Answer = ({answer, setAnswer}) => {
    const [content, setContent] = useState(answer)

    return (
      <>
        <Form.Control 
              type="text"  
              placeholder="Answer"
              value={content}
              onChange={event => setContent(event.target.value)}
              onMouseLeave={event => {if(answer !== content) setAnswer(content)}}
        />
      </>
    )
  }

  const onSubmit = (event) => {
    event.preventDefault();
    addQuestion({content: content, open: open, answers: answers})
    hideForm()
  }

  return  (
    <>
      <CenteredFormContainer>
        <div className="d-flex align-items-center mt-5 mb-3">
          <Form onSubmit={onSubmit} className="w-100">
            <Form.Group controlId="newQuestionContent">
              <Form.Control 
                type="text"
                placeholder="Content"  
                value={content}
                onChange={event => setContent(event.target.value)}
              />
              Open Question:
              <Form.Control 
                type="checkbox"  
                checked={open}
                placeholder="Open Question"
                onChange={event => setOpen(!open)}
              />

              {
                open ?
                <></>
                :
                <>
                Answers:
                {
                  answers.map((answer, index) => {
                    const setAnswer = (newAnswer) => {
                      const newAnswers = answers.map((a, i) =>{ 
                        if(i === index) return newAnswer
                        else return a 
                      })

                      setAnswers(newAnswers)
                    }
                    return <Answer key={index} answer={answer} setAnswer={setAnswer} />
                  })
                }
                <button type="button" onClick={() => {setAnswers(answers.concat([""]))}} className="ml-auto btn btn-sm btn-link">
                  <FontAwesomeIcon icon={faPlusCircle} />
                </button>
                <button type="button" onClick={() => {setAnswers(answers.slice(0, answers.length-1))}} className="ml-auto btn btn-sm btn-link">
                  <FontAwesomeIcon icon={faMinusCircle} />
                </button>
                </>
              }
            </Form.Group>
            <Button className="w-100" size="lg" variant="primary" type="submit">
              Add
            </Button>
          </Form>
        </div>
      </CenteredFormContainer>
    </>
  )
}

export default PollDetails;
