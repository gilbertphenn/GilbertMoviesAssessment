package gilbert.assessment.movies.base

import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

open class BaseActivity: AppCompatActivity() {

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
}