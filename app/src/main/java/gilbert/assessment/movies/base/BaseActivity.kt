package gilbert.assessment.movies.base

import android.annotation.SuppressLint
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat

open class BaseActivity: AppCompatActivity() {

    val fManager by lazy { supportFragmentManager }
    protected inline fun <reified  T: ViewDataBinding> activityBinding(@LayoutRes resId: Int): Lazy<T> = lazy { DataBindingUtil.setContentView(this, resId) }

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
}