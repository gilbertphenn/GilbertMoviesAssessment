package gilbert.assessment.movies.io

import gilbert.assessment.movies.model.BaseReviewData
import gilbert.assessment.movies.model.GenreData
import gilbert.assessment.movies.model.MoviesData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface APIInterfaces {

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(@QueryMap params: HashMap<String, Any>): Response<MoviesData>

    @GET("movie/popular")
    suspend fun getPopularMovies(@QueryMap params: HashMap<String, Any>): Response<MoviesData>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@QueryMap params: HashMap<String, Any>): Response<MoviesData>

    @GET("genre/movie/list")
    suspend fun getGenreList(@QueryMap params: HashMap<String, Any>): Response<GenreData>

    @GET("discover/movie")
    suspend fun getMoviesByGenre(@QueryMap params: HashMap<String, Any>): Response<MoviesData>

    @GET("movie/{id}")
    suspend fun getMoviesDetail(@Path("id") id: Long, @QueryMap params: HashMap<String, Any>): Response<MoviesData>

    @GET("movie/{id}/reviews")
    suspend fun getMoviesReviews(@Path("id") id: Long, @QueryMap params: HashMap<String, Any>): Response<BaseReviewData>
}