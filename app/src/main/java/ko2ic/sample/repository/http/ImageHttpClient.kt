package ko2ic.sample.repository.http

import ko2ic.sample.dto.ImageItemDto
import retrofit2.http.GET

interface ImageHttpClient {

    @GET("photos")
    suspend fun findImages(): List<ImageItemDto>

}