package gilbert.assessment.movies.ui.mainActivity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import gilbert.assessment.movies.R
import gilbert.assessment.movies.base.BaseActivity
import gilbert.assessment.movies.databinding.ActivityMainActivityBinding
import gilbert.assessment.movies.ui.mainActivity.fragment.genre.Genre
import gilbert.assessment.movies.ui.mainActivity.fragment.movies.Movies
import gilbert.assessment.movies.ui.mainActivity.fragment.profile.Profile

class MainActivity: BaseActivity() {

    private val binding: ActivityMainActivityBinding by activityBinding(R.layout.activity_main_activity)
    private val viewModel: MainActivityViewModel by lazy { ViewModelProvider(this)[MainActivityViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initController()
    }

    private fun initController() {
        setUpBottomNavigation()
        initListener()
    }

    private fun setUpBottomNavigation() {
        Log.d("movies", "RUN")
        showFragment("movies", Movies())
        binding.bottomNavigationView.inflateMenu(R.menu.main_menu)
    }

    private fun initListener() {
       binding.bottomNavigationView.setOnItemSelectedListener { item ->
           viewModel.currentFragment.value = when(item.itemId) {
               R.id.iMovies -> "movies"
               R.id.iGenre -> "genre"
               else -> "profile"
           }
           true
       }
    }

    private fun observable() {
        viewModel.currentFragment.observe(this) {
            changeFragment(it)
        }
    }

    private fun changeFragment(tag: String?) {
        hideFragment(viewModel.previousFragment.value)
        when (tag) {
            "movies" -> showFragment(tag, Movies())
            "genre" -> showFragment(tag, Genre())
            else -> showFragment(tag, Profile())
        }
    }

    private fun showFragment(tag: String?, fragment: Fragment?) {
        viewModel.previousFragment.value = tag ?: ""

        if (fManager.findFragmentByTag(tag) == null) {
            fManager.beginTransaction().add(R.id.fl, fragment!!, tag).commitAllowingStateLoss()
            return
        }
        fManager.beginTransaction().show(fManager.findFragmentByTag(tag)!!).commitAllowingStateLoss()
    }

    private fun hideFragment(tag: String?) {
        if (checkStringOrNot(tag)) {
            if (fManager.findFragmentByTag(tag) != null) {
                fManager.beginTransaction().hide(fManager.findFragmentByTag(tag)!!).commitAllowingStateLoss()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        observable()
    }
}