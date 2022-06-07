package gilbert.assessment.movies.repo

object ParamsHistory {

    fun movieParams(apiKey: String, language: String, page: Long ?= null, genre: Long ?= null): HashMap<String, Any> {
        val params: HashMap<String, Any> = HashMap()
        params["api_key"] = apiKey
        params["language"] = language

        if (page != null) {
            params["page"] = page
        }

        if (genre != null) {
            params["with_genres"] = genre
        }
        return params
    }
}