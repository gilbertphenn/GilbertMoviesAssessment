package gilbert.assessment.movies.ui.mainActivity.fragment.movies

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import gilbert.assessment.movies.base.BaseViewModel
import gilbert.assessment.movies.model.MoviesData
import gilbert.assessment.movies.repo.ApiRepository
import gilbert.assessment.movies.ui.moviesList.MoviesList
import gilbert.assessment.movies.utils.Config
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel : BaseViewModel<Any>() {

    val upComingMovies = MutableLiveData<MutableList<MoviesData>?>()
    val popularMovies = MutableLiveData<MutableList<MoviesData>?>()
    val topRatedMovies = MutableLiveData<MutableList<MoviesData>?>()

    fun getUpcomingMovies() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = ApiRepository.getUpComingMovies(Config.API_KEY, Config.LANGUAGE)
            withContext(Dispatchers.Main) {
                when {
                    response.isSuccessful -> {
                        val data = response.body()?.results
                        upComingMovies.value = data
                    }
                    else -> Log.e("errorRunAPI", response.message())
                }
            }
        }

    }

    fun getPopularMovies() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = ApiRepository.getPopularMovies(Config.API_KEY, Config.LANGUAGE)
            withContext(Dispatchers.Main) {
                when {
                    response.isSuccessful -> {
                        val data = response.body()?.results
                        popularMovies.value = data
                    }
                    else -> Log.e("errorRunAPI", response.message())
                }
            }
        }
    }

    fun getTopRatedMovies() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = ApiRepository.getTopRatedMovies(Config.API_KEY, Config.LANGUAGE)
            withContext(Dispatchers.Main) {
                when {
                    response.isSuccessful -> {
                        val data = response.body()?.results
                        topRatedMovies.value = data
                    }
                    else -> Log.e("errorRunAPI", response.message())
                }
            }
        }
    }

    fun seeAllPopularOnClick(view: View) {
        Intent(view.context, MoviesList::class.java).also {
            it.putExtra("intentFrom", "popular")
            view.context.startActivity(it)
        }
    }

    fun seeAllTopRatedOnClick(view: View) {
        Intent(view.context, MoviesList::class.java).also {
            it.putExtra("intentFrom", "topRated")
            view.context.startActivity(it)
        }
    }
}