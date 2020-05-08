package br.com.alanecher.desafioandroid.domain

import java.io.Serializable

class Character(
    val id: Int?, //The unique ID of the character resource.,
    val name: String?, //The name of the character.,
    val description: String?, //A short bio or description of the character.,
    //val modified: String?, //The date the resource was most recently modified.,
    //val resourceURI: String?, //The canonical URL identifier for this resource.,
    //val urls: List<Url>?, //A set of public web site URLs for the resource.,
    val thumbnail: Image? // representative image for this character.,
    //val comics: ComicList?, //A resource list containing comics which feature this character.,
    //val stories: StoryList?, //A resource list of stories in which this character appears.,
    //val events: EventList?, //A resource list of events in which this character appears.,
    //val series: SeriesList? // resource list of series in which this character appears.
):Serializable{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Character

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}