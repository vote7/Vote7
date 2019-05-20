const ApiMocks = {
  getPolls: async () => [
    {
      id: 1,
      name: "History quiz",
    },
    {
      id: 2,
      name: "Should the UK leave the EU?",
    },
  ],

  getPoll: async pollId => ({
    id: 1,
    name: "History quiz",
  }),

  getQuestions: async pollId => [
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
  ],
};

export default ApiMocks;
