package com.david.haru.myandroidtemplate.network

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Movies(
    val page: Int,
    val results: ArrayList<MovieItem>,
    val total_pages: Int,
    val total_results: Int
)


@Parcelize
data class MovieItem(
    val id: Int = 0,
    val overview: String = "",
    val popularity: Float = 0f,
    val poster_path: String = "",
    val release_date: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Float = 0f,
    val vote_count: Int = 0
) : Parcelable


fun MovieItem.isEmpty() = (this.id == 0 && this.overview.isEmpty() && this.title.isEmpty())

fun MovieItem.getTransitionName() = "transitionName_${id}"

fun MovieItem.getImageUrl() = Uri.parse("https://image.tmdb.org/t/p/w500${poster_path}")!!
