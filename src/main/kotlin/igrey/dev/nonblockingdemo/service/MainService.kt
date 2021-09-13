package igrey.dev.nonblockingdemo.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.springframework.stereotype.Service

@Service
class MainService {

    suspend fun reverse(count: Int, value: String): String = coroutineScope {
        MutableList(count) { value }
            .map {
                async(Dispatchers.Default) {
                 //   println(Thread.currentThread().name)
                    reverse(value)
                }
            }
            .awaitAll()
            .reduce { a, b -> "$a\n$b" }
    }

    private suspend fun reverse(value: String): String {
        delay(15000)
        return value.reversed()
    }
}