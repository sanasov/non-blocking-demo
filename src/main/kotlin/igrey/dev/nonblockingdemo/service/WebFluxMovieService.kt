package igrey.dev.nonblockingdemo.service

import igrey.dev.nonblockingdemo.const.Constants.IMDB_TOP_250
import igrey.dev.nonblockingdemo.external.OmdbWebClient
import igrey.dev.nonblockingdemo.external.response.MovieResponse
import igrey.dev.nonblockingdemo.external.response.MovieStatResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class WebFluxMovieService(
    val omdbWebClient: OmdbWebClient,
) {

    fun getMovie(title: String) = omdbWebClient.getMovie(title)

    fun getMovies() =
        Flux.fromIterable(IMDB_TOP_250)
            .flatMap { title ->
                getMovie(title)
                    .onErrorResume {
                        print(it.message)
                        Mono.empty()
                    }
            }.collectList()
            .map { MovieStatResponse.init(it) }

    fun getProxyMovie(title: String): Mono<String> = omdbWebClient.proxyClient(title)
}