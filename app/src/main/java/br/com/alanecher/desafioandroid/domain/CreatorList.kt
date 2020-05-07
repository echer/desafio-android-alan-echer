package br.com.alanecher.desafioandroid.domain

class CreatorList (
    val available: Int?, //The number of total available creators in this list. Will always be greater than or equal to the "returned" value.,
    val returned: Int?, //The number of creators returned in this collection (up to 20).,
    val collectionURI: String?, //The path to the full list of creators in this collection.,
    val items: List<CreatorSummary>? //The list of returned creators in this collection.
)