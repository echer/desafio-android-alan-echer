package br.com.alanecher.desafioandroid.domain

class StoryList(
    val available: Int?, //The number of total available stories in this list. Will always be greater than or equal to the "returned" value.,
    val returned: Int?, //The number of stories returned in this collection (up to 20).,
    val collectionURI: String?, //The path to the full list of stories in this collection.,
    val items: List<StorySummary>? //The list of returned stories in this collection.
)