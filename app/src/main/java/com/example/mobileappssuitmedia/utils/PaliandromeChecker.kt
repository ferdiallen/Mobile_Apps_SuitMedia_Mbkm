package com.example.mobileappssuitmedia.utils

object PaliandromeChecker {
    fun paliandromeCheck(data: String): Boolean {
        val tempConcatenate = StringBuilder()
        for (i in data.length - 1 downTo 0) {
            tempConcatenate.append(data[i].lowercaseChar())
        }
        return tempConcatenate.toString() == data.lowercase()
    }
}