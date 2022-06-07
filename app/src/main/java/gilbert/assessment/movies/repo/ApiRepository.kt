package gilbert.assessment.movies.repo

import gilbert.assessment.movies.io.RestClient

object ApiRepository {

    suspend fun getUpComingMovies(apiKey: String, language: String) = RestClient.getClient().getUpComingMovies(ParamsHistory.movieParams(apiKey, language))
    suspend fun getPopularMovies(apiKey: String, language: String) = RestClient.getClient().getPopularMovies(ParamsHistory.movieParams(apiKey, language))
    suspend fun getTopRatedMovies(apiKey: String, language: String) = RestClient.getClient().getTopRatedMovies(ParamsHistory.movieParams(apiKey, language))
    suspend fun getGenreList(apiKey: String, language: String) = RestClient.getClient().getGenreList(ParamsHistory.movieParams(apiKey, language))
}