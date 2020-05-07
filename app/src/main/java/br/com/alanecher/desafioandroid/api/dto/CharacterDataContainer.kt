package br.com.alanecher.desafioandroid.api.dto

class CharacterDataContainer(
    val offset: Int?, //The requested offset (number of skipped results) of the call.,
    val limit: Int?, //The requested result limit.,
    val total: Int?, //The total number of resources available given the current filter set.,
    val count: Int?, //The total number of results returned by this call.,
    val results: List<Character>? //The list of characters returned by the call.
)
