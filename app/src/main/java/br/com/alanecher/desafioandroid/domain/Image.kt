package br.com.alanecher.desafioandroid.domain

import java.io.Serializable

class Image(
    val path: String?, //The directory path of to the image.,
    val extension: String? //The file extension for the image.
):Serializable