package ru.nsu.fit.asbooster.saved.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EffectInfo(
    val id: String,
    val force: Int
) : Parcelable