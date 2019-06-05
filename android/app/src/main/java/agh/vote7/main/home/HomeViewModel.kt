package agh.vote7.main.home

import agh.vote7.data.PollService
import agh.vote7.data.model.Poll
import agh.vote7.data.model.PollId
import agh.vote7.utils.Event
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val pollService: PollService
) : ViewModel() {
    val polls = MutableLiveData<List<Poll>>()

    val showSnackbar = MutableLiveData<Event<String>>()
    val navigateToPollView = MutableLiveData<Event<PollId>>()

    init {
        viewModelScope.launch {
            loadPolls()
        }
    }

    fun onPollClicked(poll: Poll) {
        navigateToPollView.value = Event(poll.id)
    }

    private suspend fun loadPolls() {
        try {
            polls.value = pollService.getOngoingPolls()
        } catch (e: Exception) {
            Timber.e(e, "Failed to load polls")
            showSnackbar.value = Event("Failed to load polls")
        }
    }
}
