package br.com.alanecher.desafioandroid.domain

class Comic (
    val id: Int?, //The unique ID of the comic resource.,
    val digitalId: Int?, //The ID of the digital comic representation of this comic. Will be 0 if the comic is not available digitally.,
    val title: String?, //The canonical title of the comic.,
    val issueNumber: Double?, //(double, optional): The number of the issue in the series (will generally be 0 for collection formats).,
    val variantDescription: String?, //If the issue is a variant (e.g. an alternate cover, second printing, or director’s cut), a text description of the variant.,
    val description: String?, //The preferred description of the comic.,
    val modified: String?, //The date the resource was most recently modified.,
    val isbn: String?, //The ISBN for the comic (generally only populated for collection formats).,
    val upc: String?, //The UPC barcode number for the comic (generally only populated for periodical formats).,
    val diamondCode: String?, //The Diamond code for the comic.,
    val ean: String?, //The EAN barcode for the comic.,
    val issn: String?, //The ISSN barcode for the comic.,
    val format: String?, //The publication format of the comic e.g. comic, hardcover, trade paperback.,
    val pageCount: Int?, //The number of story pages in the comic.,
    val textObjects: List<TextObject>, //A set of descriptive text blurbs for the comic.,
    val resourceURI: String?, //The canonical URL identifier for this resource.,
    val urls: List<Url>?, //A set of public web site URLs for the resource.,
    val series: SeriesSummary?, //A summary representation of the series to which this comic belongs.,
    val variants: List<ComicSummary>?, //A list of variant issues for this comic (includes the "original" issue if the current issue is a variant).,
    val collections: List<ComicSummary>?, //A list of collections which include this comic (will generally be empty if the comic's format is a collection).,
    val collectedIssues: List<ComicSummary>?, //A list of issues collected in this comic (will generally be empty for periodical formats such as "comic" or "magazine").,
    val dates: List<ComicDate>?, //A list of key dates for this comic.,
    val prices: List<ComicPrice>?, //A list of prices for this comic.,
    val thumbnail: Image?, //The representative image for this comic.,
    val images: List<Image>?, //A list of promotional images associated with this comic.,
    val creators: CreatorList?, //A resource list containing the creators associated with this comic.,
    val characters: CharacterList?, //A resource list containing the characters which appear in this comic.,
    val stories: StoryList?, //A resource list containing the stories which appear in this comic.,
    val events: EventList? //A resource list containing the events in which this comic appears.
){
    var priceMax:ComicPrice? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Comic

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}