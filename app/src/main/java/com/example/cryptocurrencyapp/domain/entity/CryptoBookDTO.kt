package com.example.cryptocurrencyapp.domain.entity

import android.os.Parcel
import android.os.Parcelable
import com.example.cryptocurrencyapp.utils.CryptoConstants

data class CryptoBookDTO(
    val book: String = "",
    val minPrice: String = "",
    val maxPrice: String = "",
    val logo: Int = 0,
    val name: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: ""
    ) 

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(book)
        parcel.writeString(minPrice)
        parcel.writeString(maxPrice)
        parcel.writeInt(logo)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CryptoBookDTO> {
        override fun createFromParcel(parcel: Parcel): CryptoBookDTO {
            return CryptoBookDTO(parcel)
        }

        override fun newArray(size: Int): Array<CryptoBookDTO?> {
            return arrayOfNulls(size)
        }
    }
}

fun List<CryptoBookDTO>.toFilterWCCryptoBookDTO(): List<CryptoBookDTO> {
    return this.filter { coin ->
        coin.book.contains(CryptoConstants.MXN)
    }.map {
        CryptoBookDTO(
            book = it.book,
            minPrice = it.minPrice,
            maxPrice = it.maxPrice,
            name = it.name,
            logo = it.logo
        )
    }
}
