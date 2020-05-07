package br.com.alanecher.desafioandroid.api.dto

class SeriesList(
    val available: Int?, //The number of total available series in this list. Will always be greater than or equal to the "returned" value.,
    val returned: Int?, //The number of series returned in this collection (up to 20).,
    val collectionURI: String?, //The path to the full list of series in this collection.,
    val items: List<SeriesSummary>? //The list of returned series in this collection.
)