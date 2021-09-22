package igrey.dev.nonblockingdemo.controller

import igrey.dev.nonblockingdemo.external.response.MovieStatResponse
import igrey.dev.nonblockingdemo.service.BlockingMovieService
import igrey.dev.nonblockingdemo.service.CoroutineMovieService
import igrey.dev.nonblockingdemo.service.WebFluxMovieService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("movies/statistics")
class MovieStatisticsController(
    val blockingMovieService: BlockingMovieService,
    val webFluxMovieService: WebFluxMovieService,
    val coroutineMovieService: CoroutineMovieService
) {
    @GetMapping("webflux")
    fun getMoviesWebFlux(): Mono<MovieStatResponse> {
        return webFluxMovieService.getMovies()
    }

    @GetMapping("blocking")
    fun getMoviesBlocking(): MovieStatResponse {
        return blockingMovieService.getMovies()
    }

    @GetMapping("blocking-parallel")
    fun getMoviesBlockingParallel(): MovieStatResponse {
        return blockingMovieService.getMoviesParallel()
    }

    @GetMapping("coroutine")
    suspend fun getMoviesCoroutine(): MovieStatResponse {
        return coroutineMovieService.getMovies()
    }
}