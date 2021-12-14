package ko2ic.sample.repository

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import ko2ic.sample.dto.CommentItemDto
import ko2ic.sample.repository.http.CommentHttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class CommentRepository {

    fun fetchComments(): Flow<List<CommentItemDto>> {
        val contentType = "application/json".toMediaType()
        val format = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(format.asConverterFactory(contentType))
            .build()
        val client = retrofit.create(CommentHttpClient::class.java)

        return flow<List<CommentItemDto>> {
            kotlin.runCatching {
                val dto = client.findComments()
                emit(dto)
            }.onFailure {
                throw it
            }
        }.flowOn(Dispatchers.IO)
    }

}