package br.com.alanecher.desafioandroid.api.dto

class EventList(
    val available: Int?, //The number of total available events in this list. Will always be greater than or equal to the "returned" value.,
    val returned: Int?, //The number of events returned in this collection (up to 20).,
    val collectionURI: String?, //The path to the full list of events in this collection.,
    val items: List<EventSummary>? //The list of returned events in this collection.
)