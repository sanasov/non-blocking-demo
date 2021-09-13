package igrey.dev.nonblockingdemo.external.response

import com.fasterxml.jackson.annotation.JsonProperty

data class MovieResponse(
    @JsonProperty("Genre")
    val genre: String,
    @JsonProperty("Title")
    val title: String,
    @JsonProperty("Year")
    val year: String
) {
    fun genres(): List<String> = genre.split(",").map { it.trim() }

    fun decade(): String = year.replace("–","").dropLast(1) + "0x"
}

//{"Title":"The Godfather","Year":"1972","Rated":"R","Released":"24 Mar 1972","Runtime":"175 min","Genre":"Crime, Drama","Director":"Francis Ford Coppola","Writer":"Mario Puzo, Francis Ford Coppola","Actors":"Marlon Brando, Al Pacino, James Caan","Plot":"An organized crime dynasty's aging patriarch transfers control of his clandestine empire to his reluctant son.","Language":"English, Italian, Latin","Country":"United States","Awards":"Won 3 Oscars. 31 wins & 30 nominations total","Poster":"https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg","Ratings":[{"Source":"Internet Movie Database","Value":"9.2/10"},{"Source":"Metacritic","Value":"100/100"}],"Metascore":"100","imdbRating":"9.2","imdbVotes":"1,694,603","imdbID":"tt0068646","Type":"movie","DVD":"11 May 2004","BoxOffice":"$134,966,411","Production":"Paramount Pictures","Website":"N/A","Response":"True"}