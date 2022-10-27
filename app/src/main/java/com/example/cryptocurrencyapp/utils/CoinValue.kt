package com.example.cryptocurrencyapp.utils

fun String?.toBookName(): String  = when(this) {
    CoinType.BITCOIN.value -> "Bitcoin"
    CoinType.ETHEREUM.value -> "Ethereum"
    CoinType.XRP.value -> "XRP"
    CoinType.LITECOIN.value -> "Litecoin"
    CoinType.BITCOIN_CASH.value -> "Bitcoin Cash"
    CoinType.TRUEUSD.value -> "True USD"
    CoinType.DECETRALAND.value -> "Decentraland"
    CoinType.BASIC_ATENTION_TOKEN.value -> "Basic Attention Token"
    CoinType.DAI.value -> "Dai"
    CoinType.USD_COIN.value -> "USD coin"
    else -> ""
}

enum class CoinType(val value: String){
    BITCOIN("btc_mxn"),
    ETHEREUM("eth_mxn"),
    XRP("xrp_mxn"),
    LITECOIN("ltc_mxn"),
    BITCOIN_CASH("bch_mxn"),
    TRUEUSD ("tusd_mxn"),
    DECETRALAND ("mana_mxn"),
    BASIC_ATENTION_TOKEN("bat_mxn"),
    DAI("dai_mxn"),
    USD_COIN("usd_mxn")
}