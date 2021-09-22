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

    @Bean("proxy")
    fun fakeOmdbClient() =
        webClientBuilder.clone()
            .baseUrl(PROXY_URL)
            .build()

    companion object {
        const val OMDB_URL = "http://omdbapi.com"
        const val PROXY_URL = "http://localhost:8081"
    }
}