package com.example.moneybook

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction (
    var id:Int,
    var ei:String,
    var moneyVal: Float,
    var reason: String,
    var date: String
) : Parcelable