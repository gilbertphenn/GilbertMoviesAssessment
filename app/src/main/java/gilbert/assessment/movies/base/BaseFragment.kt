package gilbert.assessment.movies.base

import android.view.View
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    fun View.showView() {
        visibility = View.VISIBLE
    }

    fun View.hideView() {
        visibility = View.GONE
    }

    fun View.invisibleView() {
        visibility = View.INVISIBLE
    }

    fun checkStringOrNot(text: String?): Boolean = text != null && text.trim { it <= ' ' }.isNotBlank()
}