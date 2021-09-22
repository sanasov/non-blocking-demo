package igrey.dev.nonblockingdemo.controller

import igrey.dev.nonblockingdemo.service.BlockingMovieService
import igrey.dev.nonblockingdemo.service.CoroutineMovieService
import igrey.dev.nonblockingdemo.service.WebFluxMovieService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@RestController
@RequestMapping("movies")
class MovieController(
    val blockingMovieService: BlockingMovieService,
    val webFluxMovieService: WebFluxMovieService,
    val concurrentMovieService: CoroutineMovieService
) {
    @GetMapping("webflux-proxy/{title}")
    fun getMovieWebFluxProxy(@PathVariable title: String): Mono<String> {
        println(LocalDateTime.now().toString() + " " + Thread.currentThread().name)
        return webFluxMovieService.getProxyMovie(title)
    }

    @GetMapping("coroutine-proxy/{title}")
    suspend fun getMovieCoroutineProxy(@PathVariable title: String): String {
        println(LocalDateTime.now().toString() + " " + Thread.currentThread().name)
        return concurrentMovieService.getMovieProxy(title)
    }

    @GetMapping("blocking-proxy/{title}")
    fun getMovieBlockingProxy(@PathVariable title: String): Mono<String> {
        println(LocalDateTime.now().toString() + " " + Thread.currentThread().name)
        val result = blockingMovieService.getProxyMovie(title)
        return Mono.just(result)
    }
}