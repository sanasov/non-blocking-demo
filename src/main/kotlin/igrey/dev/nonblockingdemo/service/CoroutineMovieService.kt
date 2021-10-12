package igrey.dev.nonblockingdemo.service

import igrey.dev.nonblockingdemo.const.Constants.IMDB_TOP_250
import igrey.dev.nonblockingdemo.external.OmdbHttpClient11
import igrey.dev.nonblockingdemo.external.response.MovieStatResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service

@Service
class CoroutineMovieService(
    val client: OmdbHttpClient11,
) {

    suspend fun getMovieProxy(title: String) = client.getProxyMovieAsync(title)

    suspend fun getMovies() = coroutineScope {
        val list = async(Dispatchers.Default) {
            IMDB_TOP_250.map { title ->
                client.getMovieAsync(title)
            }
        }.await()
        MovieStatResponse.init(list)
    }
}