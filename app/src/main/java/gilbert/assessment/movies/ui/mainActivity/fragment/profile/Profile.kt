package gilbert.assessment.movies.ui.mainActivity.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import gilbert.assessment.movies.R
import gilbert.assessment.movies.base.BaseFragment
import gilbert.assessment.movies.databinding.FragmentProfileBinding

class Profile: BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by lazy { ViewModelProvider(requireActivity())[ProfileViewModel::class.java] }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewRoot = layoutInflater.inflate(R.layout.fragment_profile, container, false)
        binding = FragmentProfileBinding.bind(viewRoot)
        binding.vm = viewModel
        return binding.root
    }
}