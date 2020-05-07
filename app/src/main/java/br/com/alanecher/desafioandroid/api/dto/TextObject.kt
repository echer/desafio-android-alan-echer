package br.com.alanecher.desafioandroid.api.dto

class TextObject (
    val type: String?, //The canonical type of the text object (e.g. solicit text, preview text, etc.).,
    val language: String?, //The IETF language tag denoting the language the text object is written in.,
    val text: String? //The text.
)