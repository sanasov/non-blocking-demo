package igrey.dev.nonblockingdemo.external

import igrey.dev.nonblockingdemo.external.response.MovieResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import igrey.dev.nonblockingdemo.const.Secrets.API_KEY
import org.springframework.web.reactive.function.client.WebClient

@Component
class OmdbWebClient(
    @Qualifier("omdb") private val client: WebClient,
    @Qualifier("fakeOmdb") private val fakeClient: WebClient
) {
    fun getMovie(title: String) =
        client.get()
            .uri("?apikey=${API_KEY}&t={title}", title)
            .retrieve()
            .bodyToMono(MovieResponse::class.java)

    fun fakeClient(title: String) =
        fakeClient.get()
            .uri("movies/webflux/${title}")
            .retrieve()
            .bodyToMono(String::class.java)
}

