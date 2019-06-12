package agh.vote7.poll

import agh.vote7.data.model.Question
import agh.vote7.data.model.QuestionStatus
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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

    val submittedAnswer = MutableLiveData<String?>()
    val status = MutableLiveData<QuestionStatus>()

    val isEditable = combineLiveData(status, submittedAnswer) { status, submittedAnswer ->
        status == QuestionStatus.OPEN && submittedAnswer == null
    }

    val currentAnswer = MutableLiveData<String?>(null)

    val statusDescription = combineLiveData(status, submittedAnswer) { status, submittedAnswer ->
        when {
            status == QuestionStatus.CLOSED && submittedAnswer != null ->
                "Voting finished. Your vote has been recorded."

            status == QuestionStatus.CLOSED && submittedAnswer == null ->
                "Voting finished. You didn't vote"

            status == QuestionStatus.OPEN && submittedAnswer != null ->
                "Ongoing voting. Your vote has been recorded."

            status == QuestionStatus.OPEN && submittedAnswer == null ->
                "Ongoing voting."

            else -> ""
        }
    }

    fun getUnsubmittedAnswer(): String? {
        return if (isEditable.value == true) {
            currentAnswer.value
        } else {
            null
        }
    }

    fun onAnswerChanged(answer: String?) {
        pollViewModel.onAnswerChanged(id, answer)
    }

    fun setSubmittedAnswer(value: String?) {
        submittedAnswer.value = value
        if (value != null) {
            currentAnswer.value = value
        }
    }

    fun setStatus(value: QuestionStatus) {
        status.value = value
    }
}

fun <A, B, R> combineLiveData(a: LiveData<A>, b: LiveData<B>, combine: (A, B) -> R): LiveData<R> {
    return MediatorLiveData<R>().apply {
        var aSet = false
        var bSet = false

        fun update() {
            if (aSet && bSet) {
                this.value = combine(a.value as A, b.value as B)
            }
        }

        addSource(a) {
            aSet = true
            update()
        }
        addSource(b) {
            bSet = true
            update()
        }
    }
}
