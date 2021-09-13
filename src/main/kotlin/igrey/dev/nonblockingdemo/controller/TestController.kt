package igrey.dev.nonblockingdemo.controller

import igrey.dev.nonblockingdemo.service.MainService
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

@RestController
@RequestMapping("test")
class TestController(
    val mainService: MainService
) {
    @GetMapping("block/{value}")
    fun get(
        @PathVariable value: String,
        @RequestParam(required = false, defaultValue = "6") count: Int
    ): String = runBlocking {
        println(LocalDateTime.now().toString() + " " + Thread.currentThread().name)
        mainService.reverse(count, value)
    }

    @GetMapping("async/{value}")
    suspend fun getAsync(
        @PathVariable value: String,
        @RequestParam(required = false, defaultValue = "6") count: Int
    ): String {
        println(LocalDateTime.now().toString() + " " + Thread.currentThread().name)
        return mainService.reverse(count, value)
    }

    @GetMapping("mono/{value}")
    fun getMono(
        @PathVariable value: String,
        @RequestParam(required = false, defaultValue = "6") count: Int
    ): Mono<String> {
        println(Thread.currentThread().name)
        return Mono.just(value)
            .map {
                println(LocalDateTime.now().toString() + " " + Thread.currentThread().name)
                it
            }
            .delayElement(Duration.ofMillis(15000))
            .map {
                println(Thread.currentThread().name)
                it.reversed()
            }
    }

    @GetMapping("await/{value}")
    suspend fun getAwait(
        @PathVariable value: String,
        @RequestParam(required = false, defaultValue = "6") count: Int
    ): String {
        println(LocalDateTime.now().toString() + " " + Thread.currentThread().name)
        return calculateAsync(value).await()
    }

    @Throws(InterruptedException::class)
    fun calculateAsync(value: String): CompletableFuture<String> {
        val completableFuture = CompletableFuture<String>()
        Executors.newCachedThreadPool().submit<Any?> {
            Thread.sleep(10000)
            completableFuture.complete(value)
        }
        return completableFuture
    }
}
