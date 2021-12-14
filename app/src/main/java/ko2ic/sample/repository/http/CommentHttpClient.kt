package ko2ic.sample.repository.http

import ko2ic.sample.dto.CommentItemDto
import retrofit2.http.GET

interface CommentHttpClient {

    @GET("comments")
    suspend fun findComments() : List<CommentItemDto>

}