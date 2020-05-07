package br.com.alanecher.desafioandroid.domain

import java.io.Serializable

class Image(
    val path: String?, //The directory path of to the image.,
    val extension: String? //The file extension for the image.
):Serializable{
    fun buildImageThumb(): String {
        return "https${path!!.split("http")[1]}/standard_fantastic.${extension}"
    }

    fun buildImageDetail(): String {
        return "https${path!!.split("http")[1]}/detail.${extension}"
    }
}