package igrey.dev.nonblockingdemo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(
    private val webClientBuilder: WebClient.Builder
) {
    @Bean("omdb")
    fun omdbClient() =
        webClientBuilder.clone()
            .baseUrl(OMDB_URL)
            .build()

    @Bean("fakeOmdb")
    fun fakeOmdbClient() =
        webClientBuilder.clone()
            .baseUrl("http://localhost:8081")
            .build()

    companion object {
        const val OMDB_URL = "http://omdbapi.com"
    }
}