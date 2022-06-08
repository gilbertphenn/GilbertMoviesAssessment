package gilbert.assessment.movies.ui.moviesDetail

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import gilbert.assessment.movies.R
import gilbert.assessment.movies.base.BaseActivity
import gilbert.assessment.movies.databinding.ActivityMoviesDetailBinding
import gilbert.assessment.movies.utils.Config

class MoviesDetail: BaseActivity() {

    val binding: ActivityMoviesDetailBinding by activityBinding(R.layout.activity_movies_detail)
    val viewModel by lazy { ViewModelProvider(this)[MoviesDetailViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initController()
    }

    private fun initController() {
        viewModel.getIntentExtra(this)
    }

    private fun observable() {
        viewModel.data.observe(this) {
            val title = it.title
            val publishYear = convertDateFormat(it.release_date ?: "", "yyyy-MM-dd", "dd MMM yyyy")
            val overview = it.overview
            val banner = "${Config.IMAGE_URL}${it.backdrop_path}"
            val poster = "${Config.IMAGE_URL}${it.poster_path}"

            binding.iToolbar.tvTitle.text = title
            binding.tvTitle.text = title
            binding.tvPublishYear.text = publishYear
            binding.tvOverview.text = overview
            Glide.with(this).load(banner).into(binding.ivBanner)
            Glide.with(this).load(poster).into(binding.ivImage)
            binding.iLoadingLayout.llcLoadingLayout.hideView()
        }
    }

    override fun onResume() {
        super.onResume()
        observable()
    }
}