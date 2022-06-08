package gilbert.assessment.movies.ui.review

import android.util.Log
import androidx.lifecycle.MutableLiveData
import gilbert.assessment.movies.base.BaseViewModel
import gilbert.assessment.movies.model.ReviewData
import gilbert.assessment.movies.repo.ApiRepository
import gilbert.assessment.movies.utils.Config
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReviewViewModel: BaseViewModel<Any>() {

    var page: Long = 1
    var totalPages: Long = 2
    val id = MutableLiveData<Long>()
    val data = MutableLiveData<MutableList<ReviewData>?>()

    fun getIntentExtra(activity: Review) {
        id.value = activity.intent.getLongExtra("id", 0)
        getMoviesReviews()
    }

    fun getMoviesReviews() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = ApiRepository.getMoviesReviews(id.value ?: 0, Config.API_KEY, Config.LANGUAGE, page)

            withContext(Dispatchers.Main) {
                when {
                    response.isSuccessful -> {
                        page = response.body()?.page ?: 0
                        totalPages = response.body()?.total_pages ?: 0
                        data.value = response.body()?.results as MutableList<ReviewData>
                    }
                    else -> Log.e("errorRunAPI", response.message())
                }
            }
        }
    }
}