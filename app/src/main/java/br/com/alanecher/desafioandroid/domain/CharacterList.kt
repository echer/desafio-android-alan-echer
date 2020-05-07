package br.com.alanecher.desafioandroid.domain

class CharacterList (
    val available: Int?, //The number of total available characters in this list. Will always be greater than or equal to the "returned" value.,
    val returned: Int?, //The number of characters returned in this collection (up to 20).,
    val collectionURI: String?, //The path to the full list of characters in this collection.,
    val items: List<CharacterSummary>? //The list of returned characters in this collection.
)