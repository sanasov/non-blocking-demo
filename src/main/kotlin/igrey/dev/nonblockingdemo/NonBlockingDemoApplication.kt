package igrey.dev.nonblockingdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NonBlockingDemoApplication

fun main(args: Array<String>) {
    runApplication<NonBlockingDemoApplication>(*args)
    Thread.getAllStackTraces().keys.sortedBy { it.name }.forEach { println(it.name) }
}
