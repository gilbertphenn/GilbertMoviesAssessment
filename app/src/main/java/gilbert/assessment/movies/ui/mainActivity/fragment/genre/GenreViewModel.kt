package gilbert.assessment.movies.ui.mainActivity.fragment.genre

import android.util.Log
import androidx.lifecycle.MutableLiveData
import gilbert.assessment.movies.base.BaseViewModel
import gilbert.assessment.movies.model.GenreData
import gilbert.assessment.movies.repo.ApiRepository
import gilbert.assessment.movies.utils.Config
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GenreViewModel: BaseViewModel<Any>() {

    val genreListData = MutableLiveData<MutableList<GenreData>?>()

    fun getGenreList() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = ApiRepository.getGenreList(Config.API_KEY, Config.LANGUAGE)

            withContext(Dispatchers.Main) {
                when {
                    response.isSuccessful -> genreListData.value = response.body()?.genres
                    else -> Log.e("errorRunAPI", "${response.message()}")
                }
            }
        }
    }
}