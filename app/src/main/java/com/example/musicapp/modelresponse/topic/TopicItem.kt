package com.example.musicapp.modelresponse.topic


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopicItem(
    @SerialName("idTopic")
    val idTopic: String?,
    @SerialName("topicImg")
    val topicImg: String?,
    @SerialName("topicName")
    val topicName: String?
)