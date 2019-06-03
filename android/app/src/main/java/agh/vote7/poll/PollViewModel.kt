package agh.vote7.poll

import agh.vote7.data.PollService
import agh.vote7.data.model.PollId
import agh.vote7.data.model.QuestionId
import agh.vote7.utils.Event
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber

class PollViewModel(
    private val pollId: PollId,
    private val pollService: PollService
) : ViewModel() {

    val title = MutableLiveData<String>()
    val questions = MutableLiveData<List<QuestionViewModel>>()

    val showSnackbar = MutableLiveData<Event<String>>()
    val showConfirmationModal = MutableLiveData<Event<ConfirmationModal>>()
    val navigateToHome = MutableLiveData<Event<Unit>>()

    init {
        viewModelScope.launch {
            loadData()
        }
    }

    fun onClosedAnswerClicked(questionId: QuestionId, answer: String) = onAnswerChosen(questionId, answer)
    fun onOpenAnswerSubmitted(questionId: QuestionId, answer: String) = onAnswerChosen(questionId, answer)

    private suspend fun loadData() {
        try {
            val poll = pollService.getPoll(pollId)
            val questions = pollService.getPollQuestions(pollId)

            this.title.value = poll.name
            this.questions.value = questions.map { question ->
                QuestionViewModel(question, this)
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to load user profile")
            showSnackbar.value = Event("Failed to load user profile")
            navigateToHome.value = Event(Unit)
        }
    }

    private fun getQuestionViewModel(questionId: QuestionId) =
        questions.value!!.first { it.id == questionId }

    private fun onAnswerChosen(questionId: QuestionId, answer: String) {
        val questionViewModel = getQuestionViewModel(questionId)

        if (!questionViewModel.isEditable.value!!) {
            return
        }

        showConfirmationModal.value = Event(ConfirmationModal(
            content = "Do you want to vote for \"$answer\"?",
            onConfirmClicked = {
                viewModelScope.launch {
                    voteForAnswer(questionId, answer)
                }
            }
        ))
    }

    private suspend fun voteForAnswer(questionId: QuestionId, answer: String) {
        val questionViewModel = getQuestionViewModel(questionId)

        try {
            pollService.voteOnQuestion(questionId, answer)
        } catch (e: Exception) {
            Timber.e(e, "Failed to vote")
            showSnackbar.value = Event("Failed to vote")
            return
        }

        questionViewModel.selectedClosedAnswer.value = answer
        questionViewModel.isEditable.value = false
        showSnackbar.value = Event("You voted for \"$answer\"")
    }
}

data class ConfirmationModal(
    val content: String,
    val onConfirmClicked: () -> Unit
)