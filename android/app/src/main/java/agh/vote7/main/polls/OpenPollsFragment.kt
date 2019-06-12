package agh.vote7.main.polls

import agh.vote7.data.model.PollStatus

class OpenPollsFragment : PollsFragment() {
    override val pollStatus: PollStatus = PollStatus.OPEN
}
