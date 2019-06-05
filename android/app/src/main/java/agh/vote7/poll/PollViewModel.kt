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
    val isVoteButtonEnabled = MutableLiveData<Boolean>(false)

    init {
        viewModelScope.launch {
            loadData()
        }
    }

    fun onAnswerChanged(questionId: QuestionId, answer: String?) {
        getQuestionViewModel(questionId).currentAnswer.value = answer
        updateIsVoteButtonEnabled()
    }

    fun onVoteClicked() {
        if (!isVoteButtonEnabled.value!!) {
            return
        }

        showConfirmationModal.value = Event(ConfirmationModal(
            content = "Do you want to submit your answers?",
            onConfirmClicked = {
                viewModelScope.launch {
                    vote()
                }
            }
        ))
    }

    private suspend fun loadData() {
        try {
            val (poll, answeredQuestions) = pollService.getPollWithAnswers(pollId)
            val questions = pollService.getPollQuestions(pollId)

            this.title.value = poll.name
            this.questions.value = questions.map { question ->
                QuestionViewModel(question, this).apply {
                    currentAnswer.value = answeredQuestions
                        .find { it.question.id == question.id }
                        ?.answers
                        ?.firstOrNull()
                        ?.content
                    isEditable.value = currentAnswer.value.isNullOrEmpty()
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to load poll")
            showSnackbar.value = Event("Failed to load poll")
            navigateToHome.value = Event(Unit)
        }
    }

    private fun getQuestionViewModel(questionId: QuestionId) =
        questions.value!!.first { it.id == questionId }

    private suspend fun vote() {
        questions.value!!.forEach { questionViewModel ->
            try {
                pollService.voteOnQuestion(questionViewModel.id, questionViewModel.currentAnswer.value!!)
            } catch (e: Exception) {
                Timber.e(e, "Failed to vote")
                showSnackbar.value = Event("Failed to vote")
                return
            }

            questionViewModel.isEditable.value = false
        }
        showSnackbar.value = Event("Your votes have been submitted")
    }

    private fun updateIsVoteButtonEnabled() {
        isVoteButtonEnabled.value =
            questions.value!!.any { it.isEditable.value!! }
                    && questions.value!!.none { it.isEditable.value!! && it.currentAnswer.value.isNullOrEmpty() }
    }
}

data class ConfirmationModal(
    val content: String,
    val onConfirmClicked: () -> Unit
)