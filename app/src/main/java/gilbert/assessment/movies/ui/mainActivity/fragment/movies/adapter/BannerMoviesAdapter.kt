package gilbert.assessment.movies.ui.mainActivity.fragment.movies.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gilbert.assessment.movies.R
import gilbert.assessment.movies.model.MoviesData
import gilbert.assessment.movies.ui.moviesDetail.MoviesDetail
import gilbert.assessment.movies.utils.Config

class BannerMoviesAdapter(private var activity: Activity, private var data: MutableList<MoviesData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = Item(LayoutInflater.from(activity).inflate(R.layout.adapter_movies_banner, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Item) {
            val imagesUrl = "${Config.IMAGE_URL}${data[position].backdrop_path}"
            Glide.with(activity).load(imagesUrl).into(holder.ivBanner)
        }
    }

    override fun getItemCount(): Int = data.size

    inner class Item(view: View): RecyclerView.ViewHolder(view) {
        val cvBanner: CardView = view.findViewById(R.id.cvBanner)
        val ivBanner: ImageView = view.findViewById(R.id.ivBanner)

        init {
            cvBanner.setOnClickListener {
                val i = Intent(activity, MoviesDetail::class.java)
                i.putExtra("id", data[layoutPosition].id)
                activity.startActivity(i)
            }
        }
    }
}