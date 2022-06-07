package gilbert.assessment.movies.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job

open class BaseViewModel<N>: ViewModel() {

    var job: Job? = null
    var errorMessage = MutableLiveData<String?>()

    var exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onCallAPIError(throwable.localizedMessage)
    }

    private fun onCallAPIError(message: String?) {
        errorMessage.postValue(message)
    }
}