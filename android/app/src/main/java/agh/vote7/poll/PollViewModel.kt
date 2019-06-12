package agh.vote7.poll

import agh.vote7.data.PollService
import agh.vote7.data.model.PollId
import agh.vote7.data.model.QuestionId
import agh.vote7.data.model.QuestionStatus
import agh.vote7.utils.Event
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

class PollViewModel(
    private val pollId: PollId,
    private val pollService: PollService
) : ViewModel() {
    private var refreshJob: Job? = null

    val title = MutableLiveData<String>()
    val questionViewModels = MutableLiveData<List<QuestionViewModel>>()

    val showSnackbar = MutableLiveData<Event<String>>()
    val showConfirmationModal = MutableLiveData<Event<ConfirmationModal>>()
    val navigateToHome = MutableLiveData<Event<Unit>>()
    val isVoteButtonEnabled = MutableLiveData<Boolean>(false)

    fun onResume() {
        refreshJob = viewModelScope.launch {
            refreshDataPeriodically()
        }
    }

    fun onPause() {
        refreshJob?.cancel()
        refreshJob = null
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

    private suspend fun refreshDataPeriodically() {
        while (true) {
            loadData()
            delay(10_000)
        }
    }

    private suspend fun loadData() {
        try {
            val poll = pollService.getPoll(pollId)
            title.value = poll.name

            val questions = pollService.getPollQuestions(pollId)
            val votes = pollService.getPollVotes(pollId)

            val oldQuestionViewModels = this.questionViewModels.value.orEmpty().associateBy { it.id }

            questionViewModels.value = questions.map { question ->
                val vote = votes[question.id]

                val questionViewModel = oldQuestionViewModels[question.id] ?: QuestionViewModel(question, this)
                questionViewModel.apply {
                    setStatus(question.status)
                    setSubmittedAnswer(vote?.content)
                }
                questionViewModel
            }
        } catch (e: HttpException) {
            Timber.e(e, "Failed to load poll")
            showSnackbar.value = Event("Failed to load poll")
            navigateToHome.value = Event(Unit)
        }
    }

    private fun getQuestionViewModel(questionId: QuestionId) =
        questionViewModels.value!!.first { it.id == questionId }

    private suspend fun vote() {
        questionViewModels.value!!.forEach { questionViewModel ->
            val unsubmittedAnswer = questionViewModel.getUnsubmittedAnswer()
            if (unsubmittedAnswer != null) {

                try {
                    pollService.voteOnQuestion(questionViewModel.id, unsubmittedAnswer)
                } catch (e: HttpException) {
                    Timber.e(e, "Failed to vote")
                    showSnackbar.value = Event("Failed to vote")
                    return
                }

                questionViewModel.setSubmittedAnswer(unsubmittedAnswer)
            }
        }
        showSnackbar.value = Event("Your votes have been submitted")
    }

    private fun updateIsVoteButtonEnabled() {
        isVoteButtonEnabled.value =
            questionViewModels.value!!.any {
                it.getUnsubmittedAnswer() != null
            }
                    && questionViewModels.value!!.all {
                it.submittedAnswer.value != null
                        || it.status.value == QuestionStatus.CLOSED
                        || !it.getUnsubmittedAnswer().isNullOrBlank()
            }
    }
}

data class ConfirmationModal(
    val content: String,
    val onConfirmClicked: () -> Unit
)