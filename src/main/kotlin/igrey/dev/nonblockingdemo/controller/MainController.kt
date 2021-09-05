package igrey.dev.nonblockingdemo.controller

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("test")
class MainController {
    @GetMapping("{value}")
    fun get(@PathVariable value: String): String = runBlocking {
        delay(1000)
        value
    }
}