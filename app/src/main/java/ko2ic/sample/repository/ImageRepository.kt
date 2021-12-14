package ko2ic.sample.repository

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import ko2ic.sample.dto.ImageItemDto
import ko2ic.sample.repository.http.ImageHttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class ImageRepository {

    fun fetchImages(): Flow<List<ImageItemDto>> {
        val contentType = "application/json".toMediaType()
        val format = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(format.asConverterFactory(contentType))
            .build()
        val client = retrofit.create(ImageHttpClient::class.java)

        return flow<List<ImageItemDto>> {
            kotlin.runCatching {
                val dto = client.findImages()
                emit(dto)
            }.onFailure {
                throw it
            }
        }.flowOn(Dispatchers.IO)
    }

}