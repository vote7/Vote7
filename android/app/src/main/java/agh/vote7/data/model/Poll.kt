package agh.vote7.data.model

import com.google.gson.annotations.SerializedName

typealias PollId = Long

data class Poll(
    val id: PollId,
    val name: String,
    val description: String?,
    @SerializedName("underway") val isOngoing: Boolean = false
)
