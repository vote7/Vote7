package agh.vote7.poll

import agh.vote7.data.model.Question
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuestionViewModel(
    question: Question,
    private val pollViewModel: PollViewModel
) : ViewModel() {

    val id = question.id
    val content = question.content
    val isOpen = question.open
    val closedAnswers: List<String> = question.answers.map { it.content }

    val isEditable = MutableLiveData<Boolean>(true)
    val currentAnswer = MutableLiveData<String?>(null)

    fun onAnswerChanged(answer: String?) {
        pollViewModel.onAnswerChanged(id, answer)
    }
}