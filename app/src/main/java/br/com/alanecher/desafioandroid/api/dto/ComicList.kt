package br.com.alanecher.desafioandroid.api.dto

class ComicList(
    val available: Int?, //The number of total available issues in this list. Will always be greater than or equal to the "returned" value.,
    val returned: Int?, //The number of issues returned in this collection (up to 20).,
    val collectionURI: String?, //The path to the full list of issues in this collection.,
    val items: List<ComicSummary>? //The list of returned issues in this collection.
)