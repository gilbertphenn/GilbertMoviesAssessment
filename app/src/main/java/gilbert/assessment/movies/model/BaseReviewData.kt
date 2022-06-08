package gilbert.assessment.movies.model

class BaseReviewData {

    var id: Long? = null
    var page: Long? = null
    var total_pages: Long? = null
    var results: MutableList<ReviewData>? = null
}