package gilbert.assessment.movies.ui.mainActivity.fragment.genre.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import gilbert.assessment.movies.R
import gilbert.assessment.movies.model.GenreData
import gilbert.assessment.movies.ui.moviesList.MoviesList

class GenreAdapter(private var activity: Activity, private var data: MutableList<GenreData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = Item(LayoutInflater.from(activity).inflate(R.layout.adapter_genre, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Item) {
            val genre = data[position].name
            holder.tvGenre.text = genre
        }
    }

    override fun getItemCount(): Int = data.size

    inner class Item(view: View): RecyclerView.ViewHolder(view) {
        val cvMain: CardView = view.findViewById(R.id.cvMain)
        val tvGenre: TextView = view.findViewById(R.id.tvGenre)

        init {
            cvMain.setOnClickListener {
                val i = Intent(activity, MoviesList::class.java)
                i.putExtra("intentFrom", "genre")
                i.putExtra("genreId", data[layoutPosition].id)
                i.putExtra("titleName", data[layoutPosition].name)
                activity.startActivity(i)
            }
        }
    }
}