package com.samuelcabezas.rmoviesapp.models.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.os.Parcelable
import com.samuelcabezas.rmoviesapp.utils.Converters
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
@TypeConverters(Converters::class)
data class Movie(
    @field:PrimaryKey(autoGenerate = true)
    var movie_id: Int,
    var vote_count: Int?,
    var id: Int?,
    var video: Boolean?,
    var vote_average: Float?,
    var title: String?,
    var popularity: Float?,
    var poster_path: String,
    var original_language: String?,
    var original_title: String?,
    var genre_ids: List<Int>,
    var backdrop_path: String,
    var adult: Boolean?,
    var overview: String?,
    var release_date: String?
): Parcelable



