package igrey.dev.nonblockingdemo.external.response

data class MovieStatResponse(
    val year2Count: Any,
    val genre2Count: Any
) {
    companion object {
        fun init(movies: List<MovieResponse>) =
            MovieStatResponse(
                movies.map { it.decade() }
                    .groupingBy { it }.eachCount().toList().sortedByDescending { (_, value) -> value },
                movies.map { it.genres() }.flatten().groupingBy { it }.eachCount().toList()
                    .sortedByDescending { (_, value) -> value }
            )
    }
}