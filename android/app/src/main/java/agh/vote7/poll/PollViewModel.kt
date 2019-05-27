package agh.vote7.poll

import agh.vote7.data.PollService
import agh.vote7.data.model.PollId
import agh.vote7.data.model.Question
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
    val questions = MutableLiveData<List<Question>>()

    val showSnackbar = MutableLiveData<Event<String>>()
    val navigateToHome = MutableLiveData<Event<Unit>>()

    init {
        viewModelScope.launch {
            loadData()
        }
    }

    private suspend fun loadData() {
        try {
            val poll = pollService.getPoll(pollId)
            val questions = pollService.getPollQuestions(pollId)

            this.title.value = poll.name
            this.questions.value = questions
        } catch (e: Exception) {
            Timber.e(e, "Failed to load user profile")
            showSnackbar.value = Event("Failed to load user profile")
            navigateToHome.value = Event(Unit)
        }
    }
}