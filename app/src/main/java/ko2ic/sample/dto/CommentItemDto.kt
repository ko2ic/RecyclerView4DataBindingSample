package ko2ic.sample.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommentItemDto (
    val postId: Long,
    val id: Long,
    val name: String,
    val email: String,
    val body: String
)
