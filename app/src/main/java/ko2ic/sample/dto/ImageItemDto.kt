package ko2ic.sample.dto

import kotlinx.serialization.Serializable


@Serializable
data class ImageItemDto(
    val url: String,
)