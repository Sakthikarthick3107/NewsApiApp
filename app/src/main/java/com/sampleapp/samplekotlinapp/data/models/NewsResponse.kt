package com.sampleapp.samplekotlinapp.data.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

data class NewsResponse(
    val articles : List<Article>
)

data class Article(
    val title : String,
    val description : String?,
    val url : String,
    val urlToImage : String?,
    val publishedAt : String,
    val source : Source,

    )


data class Source(
    val name : String
)

@RequiresApi(Build.VERSION_CODES.O)
fun formatPublishedDate(dateString: String?): String {
    return try {
        val zonedDateTime = ZonedDateTime.parse(dateString)
        zonedDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(
            Locale.getDefault()))
    } catch (e: Exception) {
        "Unknown date"
    }
}