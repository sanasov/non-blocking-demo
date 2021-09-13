package igrey.dev.nonblockingdemo.service

import igrey.dev.nonblockingdemo.const.Constants.IMDB_TOP_250
import igrey.dev.nonblockingdemo.external.OmdbHttpClient11
import igrey.dev.nonblockingdemo.external.response.MovieStatResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.springframework.stereotype.Service

@Service
class ConcurrentMovieService(
    val client: OmdbHttpClient11,
) {

    suspend fun getMovie(title: String) = client.getMovieAsync(title)

    suspend fun getMovies() = coroutineScope {
        val list = IMDB_TOP_250.map { title ->
            async(Dispatchers.Default) {
                client.getMovieAsync(title)
            }
        }.awaitAll()
        MovieStatResponse.init(list)
    }
}