package gilbert.assessment.movies.ui.mainActivity

import androidx.lifecycle.MutableLiveData
import gilbert.assessment.movies.base.BaseViewModel

class MainActivityViewModel: BaseViewModel<Any>() {

    val previousFragment = MutableLiveData<String>()
    val currentFragment = MutableLiveData<String>()
}