package agh.vote7.dummy

data class Voting(val id: String, val title: String)

object VotingRepository {
    val items: MutableList<Voting> = mutableListOf()

    init {
        for (i in 1..15) {
            items.add(Voting(i.toString(), "Voting #$i"))
        }
    }
}

