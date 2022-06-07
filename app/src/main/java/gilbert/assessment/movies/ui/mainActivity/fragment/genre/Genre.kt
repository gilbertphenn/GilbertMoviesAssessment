package gilbert.assessment.movies.ui.mainActivity.fragment.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import gilbert.assessment.movies.R
import gilbert.assessment.movies.base.BaseFragment
import gilbert.assessment.movies.databinding.FragmentGenreBinding
import gilbert.assessment.movies.ui.mainActivity.fragment.genre.adapter.GenreAdapter

class Genre: BaseFragment() {

    private lateinit var binding: FragmentGenreBinding
    private val viewModel: GenreViewModel by lazy { ViewModelProvider(requireActivity())[GenreViewModel::class.java] }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewRoot = layoutInflater.inflate(R.layout.fragment_genre, container, false)
        binding = FragmentGenreBinding.bind(viewRoot)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getGenreList()
    }

    private fun observable() {
        viewModel.genreListData.observe(this) {
            if (it?.isNotEmpty()!!) {
                val adapter = GenreAdapter(requireActivity(), it)
                binding.rvGenre.adapter = adapter
            }
            binding.iLoading.llcLoadingLayout.hideView()
        }
    }

    override fun onResume() {
        super.onResume()
        observable()
    }
}