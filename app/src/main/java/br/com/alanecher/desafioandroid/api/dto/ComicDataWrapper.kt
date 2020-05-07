package br.com.alanecher.desafioandroid.api.dto

class ComicDataWrapper(
    val code: Int?, //The HTTP status code of the returned result.,
    val status: String?, //A string description of the call status.,
    val copyright: String?, //The copyright notice for the returned result.,
    val attributionText: String?, //The attribution notice for this result. Please display either this notice or the contents of the attributionHTML field on all screens which contain data from the Marvel Comics API.,
    val attributionHTML: String?, //An HTML representation of the attribution notice for this result. Please display either this notice or the contents of the attributionText field on all screens which contain data from the Marvel Comics API.,
    val data: ComicDataContainer?, //The results returned by the call.,
    val etag: String? //A digest value of the content returned by the call.
)