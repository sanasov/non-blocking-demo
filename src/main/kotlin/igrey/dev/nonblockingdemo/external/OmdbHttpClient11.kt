package igrey.dev.nonblockingdemo.external

import com.fasterxml.jackson.databind.ObjectMapper
import igrey.dev.nonblockingdemo.config.WebClientConfig.Companion.OMDB_URL
import igrey.dev.nonblockingdemo.const.Secrets.API_KEY
import igrey.dev.nonblockingdemo.external.response.MovieResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.future.await
import org.springframework.stereotype.Component
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.util.concurrent.CompletableFuture
import kotlin.reflect.KClass

@Component
class OmdbHttpClient11(
    val objectMapper: ObjectMapper
) {

    private val client: HttpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .followRedirects(HttpClient.Redirect.NEVER)
        .connectTimeout(Duration.ofSeconds(20))
        .build()

    fun getMovie(title: String): MovieResponse {
        val encodedTile = URLEncoder.encode(title, StandardCharsets.UTF_8.name())
        val request = HttpRequest.newBuilder()
            .timeout(Duration.ofSeconds(20))
            .uri(
                URI("$OMDB_URL?apikey=$API_KEY&t=$encodedTile")
            )
            .GET()
            .build()
        return toObject(
            client.send(request, HttpResponse.BodyHandlers.ofString()).body(),
            MovieResponse::class.java
        )
    }

    suspend fun getMovieAsync(title: String): MovieResponse {
        val encodedTile = URLEncoder.encode(title, StandardCharsets.UTF_8.name())
        val request = HttpRequest.newBuilder()
            .timeout(Duration.ofSeconds(20))
            .uri(URI.create(OMDB_URL + "?apikey=$API_KEY&t=${encodedTile}"))
            .GET()
            .build()
        val response: CompletableFuture<HttpResponse<String>> =
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        return toObject(response.await().body(), MovieResponse::class.java)
    }

    private fun <T : Any> toObject(json: String, clazz: KClass<T>): T = toObject(json, clazz.java)

    private fun <T : Any> toObject(json: String, clazz: Class<T>): T =
        try {
            objectMapper.readValue(json, clazz)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
}