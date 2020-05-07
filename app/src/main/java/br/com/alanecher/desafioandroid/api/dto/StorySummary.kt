package br.com.alanecher.desafioandroid.api.dto

class StorySummary(
    val resourceURI: String?, //The path to the individual story resource.,
    val name: String?, //The canonical name of the story.,
    val type: String? //The type of the story (interior or cover).
)