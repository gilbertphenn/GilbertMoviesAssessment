package gilbert.assessment.movies.ui.moviesList

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import gilbert.assessment.movies.R
import gilbert.assessment.movies.base.BaseActivity
import gilbert.assessment.movies.databinding.ActivityMoviesListBinding
import gilbert.assessment.movies.interfaces.RecyclerViewOnLoadMoreListener
import gilbert.assessment.movies.interfaces.RecyclerViewRetryListener
import gilbert.assessment.movies.model.MoviesData
import gilbert.assessment.movies.ui.moviesList.adapter.MoviesListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesList: BaseActivity() {

    var page: Long = 1
    private val adapter: MoviesListAdapter by lazy { MoviesListAdapter(this, binding.rvMovie) }
    private val binding: ActivityMoviesListBinding by activityBinding(R.layout.activity_movies_list)
    private val viewModel by lazy { ViewModelProvider(this)[MoviesListViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initController()
    }

    private fun initController() {
        viewModel.setParcelableData(this)
        initListener()
    }

    private fun initListener() {
        binding.iToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun observable() {
        viewModel.intentFrom.observe(this) {
            when(it) {
                "popular" -> {
                    binding.iToolbar.tvTitle.text = getString(R.string.what_is_popular)
                    viewModel.getPopularMovies(1)
                }
                "topRated" -> {
                    binding.iToolbar.tvTitle.text = getString(R.string.top_rated)
                    viewModel.getTopRatedMovies(1)
                }
            }
        }

        viewModel.titleName.observe(this) {
            if (checkStringOrNot(it)) {
                binding.iToolbar.tvTitle.text = it
            }
        }

        viewModel.genreId.observe(this) {
            if (it != null && it > 0) {
                viewModel.getMoviesByGenre(1, it)
            }
        }

        viewModel.filmData.observe(this) {
            if (it?.isNotEmpty()!!) {
                addAdapterData(it)
                if (viewModel.page.toInt() == 1) {
                    binding.rvMovie.adapter = adapter
                    adapter.setRetryListener(retryListener)
                    adapter.setLoadMoreListener(object : RecyclerViewOnLoadMoreListener {
                        override fun onLoadMore() {
                            if (viewModel.page.toInt() >= viewModel.totalPages.toInt()){

                            }else{
                                viewModel.page++
                                adapter.addLoadingData()
                                CoroutineScope(Dispatchers.Main).launch {
                                    when(viewModel.intentFrom.value) {
                                        "popular" -> viewModel.getPopularMovies(viewModel.page)
                                        "topRated" -> viewModel.getTopRatedMovies(viewModel.page)
                                        else -> viewModel.getMoviesByGenre(viewModel.page, viewModel.genreId.value)
                                    }
                                }
                            }
                        }
                    })
                }
            }
            else{
                addAdapterData(it)
            }
        }
    }

    private fun addAdapterData(data: MutableList<MoviesData>) {
        if (viewModel.page > 1) {
            adapter.removeLastItem()
        } else {
            adapter.resetData()
        }
        adapter.changeIsLoading(false)
        adapter.addData(data)
    }

    private val retryListener = object : RecyclerViewRetryListener {
        override fun onRetryClicked() {
        }
    }

    override fun onResume() {
        super.onResume()
        observable()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}