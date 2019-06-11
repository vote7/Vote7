package agh.vote7.main.polls

import agh.vote7.data.PollService
import agh.vote7.data.model.Poll
import agh.vote7.data.model.PollId
import agh.vote7.data.model.PollStatus
import agh.vote7.utils.Event
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class PollsViewModel(
    private val pollService: PollService,
    private val pollStatus: PollStatus
) : ViewModel() {
    private var refreshJob: Job? = null

    val polls = MutableLiveData<List<Poll>>()
    val showSnackbar = MutableLiveData<Event<String>>()
    val navigateToPollView = MutableLiveData<Event<PollId>>()

    fun onResume() {
        refreshJob = viewModelScope.launch {
            refreshPollsPeriodically()
        }
    }

    fun onPause() {
        refreshJob?.cancel()
        refreshJob = null
    }

    fun onPollClicked(poll: Poll) {
        navigateToPollView.value = Event(poll.id)
    }

    private suspend fun refreshPollsPeriodically() {
        while (true) {
            loadPolls()
            delay(10_000)
        }
    }

    private suspend fun loadPolls() {
        try {
            polls.value = pollService.getPolls().filter { it.status == pollStatus }
        } catch (e: Exception) {
            Timber.e(e, "Failed to load polls")
            showSnackbar.value = Event("Failed to load polls")
        }
    }
}
