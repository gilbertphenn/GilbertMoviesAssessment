package gilbert.assessment.movies.repo

import gilbert.assessment.movies.io.RestClient

object ApiRepository {

    suspend fun getUpComingMovies(apiKey: String, language: String) = RestClient.getClient().getUpComingMovies(ParamsHistory.movieParams(apiKey, language, ))
    suspend fun getPopularMovies(apiKey: String, language: String, page: Long?= null) = RestClient.getClient().getPopularMovies(ParamsHistory.movieParams(apiKey, language))
    suspend fun getTopRatedMovies(apiKey: String, language: String, page: Long?= null) = RestClient.getClient().getTopRatedMovies(ParamsHistory.movieParams(apiKey, language))
    suspend fun getGenreList(apiKey: String, language: String, page: Long?= null) = RestClient.getClient().getGenreList(ParamsHistory.movieParams(apiKey, language))
    suspend fun getMoviesByGenre(apiKey: String, language: String, page: Long?= null, genre: Long? = null) = RestClient.getClient().getMoviesByGenre(ParamsHistory.movieParams(apiKey, language, page, genre))
}