package gilbert.assessment.movies.ui.moviesList.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gilbert.assessment.movies.R
import gilbert.assessment.movies.interfaces.RecyclerViewOnLoadMoreListener
import gilbert.assessment.movies.interfaces.RecyclerViewRetryListener
import gilbert.assessment.movies.model.MoviesData
import gilbert.assessment.movies.ui.moviesList.MoviesList
import gilbert.assessment.movies.utils.Config

class MoviesListAdapter(private var activity: MoviesList, private var recyclerView: RecyclerView): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoading = false
    private var data: MutableList<MoviesData> = mutableListOf()
    private var scrollListener: RecyclerView.OnScrollListener? = null
    private var loadMoreListener: RecyclerViewOnLoadMoreListener? = null
    private var retryListener: RecyclerViewRetryListener? = null

    init {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager

        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastItemVisible = layoutManager.findLastVisibleItemPosition()
                val firstItemVisible = layoutManager.findFirstVisibleItemPosition()
                val topRowVerticalPosition = if (recyclerView.childCount == 0) 0 else recyclerView.getChildAt( 0 ).top
                if (!isLoading && lastItemVisible > (itemCount - 5) && dy != 0) {
                    loadMoreListener?.onLoadMore()
                    isLoading = true
                }
            }
        }

        recyclerView.addOnScrollListener(scrollListener!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            Config.RECYCLERVIEW_SUCCESS -> Item(LayoutInflater.from(activity).inflate(R.layout.adapter_movies_banner, parent, false))
            Config.RECYCLERVIEW_FAILED -> Retry(LayoutInflater.from(activity).inflate(R.layout.view_recyclerview_retry, parent, false))
            else -> Loading(LayoutInflater.from(activity).inflate(R.layout.view_recyclerview_loading, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Item) {
            val imagesUrl = "${Config.IMAGE_URL}${data[position].backdrop_path}"
            Glide.with(activity).load(imagesUrl).into(holder.ivBanner)
        }
    }

    override fun getItemCount(): Int = data.size

    private inner class Item(view: View): RecyclerView.ViewHolder(view) {
        val ivBanner: ImageView = view.findViewById(R.id.ivBanner)
    }

    private inner class Loading(view: View): RecyclerView.ViewHolder(view)

    private inner class Retry(view: View): RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                retryListener?.onRetryClicked()
            }
        }
    }

    fun setLoadMoreListener(listener: RecyclerViewOnLoadMoreListener) {
        this.loadMoreListener = listener
    }

    fun setRetryListener(listener: RecyclerViewRetryListener) {
        this.retryListener = listener
    }

    fun addData(data: MutableList<MoviesData>) {
        this.data.addAll(data)
        Handler(Looper.getMainLooper()).post {
            notifyDataSetChanged()
        }
    }

    fun addLoadingData() {
        this.data.add(MoviesData())
        recyclerView.post {
            notifyDataSetChanged()
        }
    }

    fun resetData() {
        this.data.clear()
        notifyDataSetChanged()
    }

    fun removeLastItem() {
        if (itemCount != 0) {
            this.data.removeAt(itemCount-1)

            Handler(Looper.getMainLooper()).post {
                notifyDataSetChanged()
            }
        }
    }

    fun changeIsLoading(value: Boolean) {
        isLoading = value
    }
}