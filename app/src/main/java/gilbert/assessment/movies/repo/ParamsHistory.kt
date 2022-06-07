package gilbert.assessment.movies.repo

object ParamsHistory {

    fun movieParams(apiKey: String, language: String): HashMap<String, Any> {
        val params: HashMap<String, Any> = HashMap()
        params["api_key"] = apiKey
        params["language"] = language

        return params
    }
}