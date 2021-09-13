package igrey.dev.nonblockingdemo.controller

import igrey.dev.nonblockingdemo.const.Constants.IMDB_TOP_250
import igrey.dev.nonblockingdemo.external.response.MovieResponse
import igrey.dev.nonblockingdemo.external.response.MovieStatResponse
import igrey.dev.nonblockingdemo.service.BlockingMovieService
import igrey.dev.nonblockingdemo.service.ConcurrentMovieService
import igrey.dev.nonblockingdemo.service.WebFluxMovieService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@RestController
@RequestMapping("movies")
class MovieController(
    val blockingMovieService: BlockingMovieService,
    val webFluxMovieService: WebFluxMovieService,
    val concurrentMovieService: ConcurrentMovieService
) {
    @GetMapping("titles")
    fun getMovieTitles() = Mono.just(IMDB_TOP_250)

    @GetMapping("webflux/{title}")
    fun getMovieWebFlux(@PathVariable title: String): Mono<String> {
        println(LocalDateTime.now().toString() + " " + Thread.currentThread().name)
        return webFluxMovieService.getFakeMovie(title)
    }

    @GetMapping("blocking/{title}")
    fun getMovieBlocking(@PathVariable title: String): MovieResponse {
        println(LocalDateTime.now().toString() + " " + Thread.currentThread().name)
        return blockingMovieService.getMovie(title)
    }

    @GetMapping("concurrent/{title}")
    suspend fun getMovieConcurrent(@PathVariable title: String): MovieResponse {
        println(LocalDateTime.now().toString() + " " + Thread.currentThread().name)
        return concurrentMovieService.getMovie(title)
    }

    @GetMapping("webflux")
    fun getMoviesWebFlux(): Mono<MovieStatResponse> {
        println(LocalDateTime.now().toString() + " " + Thread.currentThread().name)
        return webFluxMovieService.getMovies()
    }

    @GetMapping("blocking")
    fun getMoviesBlocking(): MovieStatResponse {
        println(LocalDateTime.now().toString() + " " + Thread.currentThread().name)
        return blockingMovieService.getMovies()
    }

    @GetMapping("concurrent")
    suspend fun getMoviesConcurrent(): MovieStatResponse {
        println(LocalDateTime.now().toString() + " " + Thread.currentThread().name)
        return concurrentMovieService.getMovies()
    }
}