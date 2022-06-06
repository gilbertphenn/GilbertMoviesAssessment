package gilbert.assessment.movies.ui.splashScreen

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import gilbert.assessment.movies.R
import gilbert.assessment.movies.base.BaseActivity
import gilbert.assessment.movies.databinding.ActivitySplashscreenBinding

class SplashScreen: BaseActivity() {

    private val binding: ActivitySplashscreenBinding by activityBinding(R.layout.activity_splashscreen)
    private val viewModel: SplashScreenViewModel by lazy { ViewModelProvider(this)[SplashScreenViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
    }
}