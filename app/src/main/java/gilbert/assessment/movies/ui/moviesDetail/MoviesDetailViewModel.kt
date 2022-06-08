package gilbert.assessment.movies.ui.moviesDetail

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import gilbert.assessment.movies.base.BaseViewModel
import gilbert.assessment.movies.model.MoviesData
import gilbert.assessment.movies.repo.ApiRepository
import gilbert.assessment.movies.ui.review.Review
import gilbert.assessment.movies.utils.Config
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class MoviesDetailViewModel: BaseViewModel<Any>() {

    val id = MutableLiveData<Long>()
    val title = MutableLiveData<String>()
    val publishYear = MutableLiveData<String>()
    val overview = MutableLiveData<String>()
    val data = MutableLiveData<MoviesData>()

    fun getIntentExtra(activity: MoviesDetail) {
        id.value = activity.intent.getLongExtra("id", 0)
        getMoviesDetail()
    }

    fun getMoviesDetail() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = ApiRepository.getMoviesDetail(id.value ?: 0, Config.API_KEY, Config.LANGUAGE)

            withContext(Dispatchers.Main) {
                when {
                    response.isSuccessful -> {
                        data.value = response.body()
                        title.value = data.value?.title
                        publishYear.value = convertDateFormat(data.value?.release_date ?: "", "yyyy-MM-dd", "dd MMM yyyy")
                        overview.value = data.value?.overview
                    }
                    else -> Log.e("errorRunAPI", response.message())
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateFormat(current: String, oldFormat: String, newFormat: String): String {
        return try {
            var dateFormat = SimpleDateFormat(oldFormat)
            val newDate = dateFormat.parse(current)
            dateFormat = SimpleDateFormat(newFormat)
            dateFormat.format(newDate!!)
        } catch (e: Exception) {
            ""
        }
    }

    fun seeReviewOnClick(view: View) {
        Intent(view.context, Review::class.java).also {
            it.putExtra("id", id.value)
            view.context.startActivity(it)
        }
    }
}