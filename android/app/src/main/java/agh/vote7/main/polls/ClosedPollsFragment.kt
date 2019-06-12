package agh.vote7.main.polls

import agh.vote7.data.model.PollStatus

class ClosedPollsFragment : PollsFragment() {
    override val pollStatus: PollStatus = PollStatus.CLOSED
}