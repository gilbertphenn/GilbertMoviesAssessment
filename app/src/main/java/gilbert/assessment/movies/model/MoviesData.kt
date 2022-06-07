package gilbert.assessment.movies.model

class MoviesData {

    var id: Long? = null
    var title: String? = null
    var overview: String? = null
    var poster_path: String? = null
    var backdrop_path: String? = null
    var release_date: String? = null
    var page: Long? = null
    var total_pages: Long? = null
    var results: MutableList<MoviesData>? = null
}