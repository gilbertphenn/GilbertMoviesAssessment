package gilbert.assessment.movies.ui.review

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import gilbert.assessment.movies.R
import gilbert.assessment.movies.base.BaseActivity
import gilbert.assessment.movies.databinding.ActivityReviewBinding
import gilbert.assessment.movies.interfaces.RecyclerViewOnLoadMoreListener
import gilbert.assessment.movies.interfaces.RecyclerViewRetryListener
import gilbert.assessment.movies.model.ReviewData
import gilbert.assessment.movies.ui.review.adapter.ReviewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Review: BaseActivity() {

    val binding: ActivityReviewBinding by activityBinding(R.layout.activity_review)
    private val adapter: ReviewAdapter by lazy { ReviewAdapter(this, binding.rvReview) }
    val viewModel: ReviewViewModel by lazy { ViewModelProvider(this)[ReviewViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initController()
    }

    private fun initController() {
        binding.iToolbar.tvTitle.text = getString(R.string.review)
        viewModel.getIntentExtra(this)
        initListener()
    }

    private fun initListener() {
        binding.iToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun observable() {
        Log.d("observable", "run")
        viewModel.data.observe(this) {
            Log.d("reviewData", "run")
            if (it?.isNotEmpty()!!) {
                addAdapterData(it)
                if (viewModel.page.toInt() == 1) {
                    binding.rvReview.adapter = adapter
                    adapter.setRetryListener(retryListener)
                    adapter.setLoadMoreListener(object : RecyclerViewOnLoadMoreListener {
                        override fun onLoadMore() {
                            if (viewModel.page.toInt() >= viewModel.totalPages.toInt()){

                            }else{
                                viewModel.page++
                                adapter.addLoadingData()
                                CoroutineScope(Dispatchers.Main).launch {
                                    viewModel.getMoviesReviews()
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

    private fun addAdapterData(data: MutableList<ReviewData>) {
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
}