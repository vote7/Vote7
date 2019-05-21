let pollId = 3
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
  },
  {
    content: "Who was the first president of the United States?",
    open: true,
  },
  {
    content: "Which leader murdered more people",
    open: false,
    answers: ["Adolf Hitler", "Joseph Stalin", "Mao Zedong"],
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
      questions.push(question)
  },

  editPollName: async (pollId, newName) => {
    polls = polls.map(({id, name}) => id === pollId ? {id: id, name: newName} : {id: id, name: name})
  }
};

export default ApiMocks;
