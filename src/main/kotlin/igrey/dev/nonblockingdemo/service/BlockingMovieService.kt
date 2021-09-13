package igrey.dev.nonblockingdemo.service

import igrey.dev.nonblockingdemo.const.Constants.IMDB_TOP_250
import igrey.dev.nonblockingdemo.external.OmdbHttpClient11
import igrey.dev.nonblockingdemo.external.OmdbWebClient
import igrey.dev.nonblockingdemo.external.response.MovieResponse
import igrey.dev.nonblockingdemo.external.response.MovieStatResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toMono

@Service
class BlockingMovieService(
    val httpClient11: OmdbHttpClient11
) {
    fun getMovie(title: String) = httpClient11.getMovie(title)

    fun getMovies(): MovieStatResponse = MovieStatResponse.init(
        IMDB_TOP_250.map { httpClient11.getMovie(it) }
    )
}