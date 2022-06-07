package gilbert.assessment.movies.ui.moviesList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import gilbert.assessment.movies.base.BaseViewModel
import gilbert.assessment.movies.model.MoviesData
import gilbert.assessment.movies.repo.ApiRepository
import gilbert.assessment.movies.utils.Config
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesListViewModel: BaseViewModel<Any>() {

    var page: Long = 1
    var totalPages: Long = 2
    val intentFrom = MutableLiveData<String>()
    val titleName = MutableLiveData<String>()
    val genreId = MutableLiveData<Long>()
    val filmData = MutableLiveData<MutableList<MoviesData>?>()

    fun setParcelableData(activity: MoviesList) {
        intentFrom.value = activity.intent.getStringExtra("intentFrom")
        genreId.value = activity.intent.getLongExtra("genreId", 0)
        titleName.value = activity.intent.getStringExtra("titleName") ?: ""
    }

    fun getPopularMovies(page: Long?) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = ApiRepository.getPopularMovies(Config.API_KEY, Config.LANGUAGE, page)
            withContext(Dispatchers.Main) {
                when {
                    response.isSuccessful -> {
                        val data = response.body()?.results
                        filmData.value = data
                        totalPages = response.body()?.total_pages ?: 0
                    }
                    else -> Log.e("errorRunAPI", response.message())
                }
            }
        }
    }

    fun getTopRatedMovies(page: Long?) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = ApiRepository.getTopRatedMovies(Config.API_KEY, Config.LANGUAGE, page)
            withContext(Dispatchers.Main) {
                when {
                    response.isSuccessful -> {
                        val data = response.body()?.results
                        filmData.value = data
                        totalPages = response.body()?.total_pages ?: 0
                    }
                    else -> Log.e("errorRunAPI", response.message())
                }
            }
        }
    }

    fun getMoviesByGenre(page: Long?, genre: Long?) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = ApiRepository.getMoviesByGenre(Config.API_KEY, Config.LANGUAGE, page, genre)
            withContext(Dispatchers.Main) {
                when {
                    response.isSuccessful -> {
                        val data = response.body()?.results
                        filmData.value = data
                        totalPages = response.body()?.total_pages ?: 0
                    }
                    else -> Log.e("errorRunAPI", response.message())
                }
            }
        }
    }
}