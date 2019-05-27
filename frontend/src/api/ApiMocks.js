let pollId = 3
let order = 4
let polls =  [
  {
    id: 1,
    name: "History quiz"
  },
  {
    id: 2,
    name: "Should the UK leave the EU?"
  },
]
let questions = [
  {
    content: "In which year did WW2 started?",
    open: true,
    order: 1
  },
  {
    content: "Which leader murdered more people",
    open: false,
    answers: ["Adolf Hitler", "Joseph Stalin", "Mao Zedong"],
    order: 2
  },
  {
    content: "Who was the first president of the United States?",
    open: true,
    order: 3
  },
]
const ApiMocks = {
  getPolls: async () => polls,

  getPoll: async pollId => ({
    id: 1,
    name: "History quiz",
  }),

  getQuestions: async pollId => questions,

  addPolls: async poll => {
    poll["id"] = pollId;
    polls.push(poll)
    pollId = pollId + 1
  },

  addQuestion: async question => {
      question["order"] = order
      order += 1
      questions.push(question)
  },

  editPollName: async (pollId, newName) => {
    polls = polls.map(({id, name}) => id === pollId ? {id: id, name: newName} : {id: id, name: name})
  },

  setQuestion: async ({pollId: id, content, questionOpen: open, order}) => {
      questions.push({content: content, questionOpen: open, order: order})
  },

  removeQuestion: async ({pollId: id, content, questionOpen: open, order}) => {
    questions = questions.filter(question => question.order !== order)
}
};

export default ApiMocks;
