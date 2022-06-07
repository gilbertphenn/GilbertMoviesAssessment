package gilbert.assessment.movies.ui.mainActivity.fragment.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import gilbert.assessment.movies.R
import gilbert.assessment.movies.base.BaseFragment
import gilbert.assessment.movies.databinding.FragmentMoviesBinding
import gilbert.assessment.movies.ui.mainActivity.fragment.movies.adapter.BannerMoviesAdapter
import gilbert.assessment.movies.ui.mainActivity.fragment.movies.adapter.HorizontalMoviesAdapter

class Movies: BaseFragment() {

    private lateinit var binding: FragmentMoviesBinding
    private val viewModel: MoviesViewModel by lazy { ViewModelProvider(requireActivity())[MoviesViewModel::class.java] }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewRoot = layoutInflater.inflate(R.layout.fragment_movies, container, false)
        binding = FragmentMoviesBinding.bind(viewRoot)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initController()
        observable()
    }

    private fun initController() {
        viewModel.getUpcomingMovies()
    }

    private fun observable() {
        viewModel.upComingMovies.observe(requireActivity()) {
            if (it?.isNotEmpty()!!) {
                val adapter = BannerMoviesAdapter(requireActivity(), it)
                binding.rvBanner.adapter = adapter
            }
            viewModel.getPopularMovies()
        }

        viewModel.popularMovies.observe(requireActivity()) {
            if (it?.isNotEmpty()!!) {
                val adapter = HorizontalMoviesAdapter(requireActivity(), it)
                binding.rvPopular.adapter = adapter
            }
            viewModel.getTopRatedMovies()
        }

        viewModel.topRatedMovies.observe(requireActivity()) {
            if (it?.isNotEmpty()!!) {
                val adapter = HorizontalMoviesAdapter(requireActivity(), it)
                binding.rvTopRated.adapter = adapter
            }
            binding.iLoading.llcLoadingLayout.hideView()
        }
    }

    override fun onResume() {
        super.onResume()
        observable()
    }
}