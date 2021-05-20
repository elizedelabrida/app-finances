package com.elize.finances.extension


fun String.limitLength(charQuantity: Int): String {
    if (this.length > charQuantity) {
        val firstIndex = 0
        return "${this.substring(firstIndex, charQuantity)}..."
    }
    return this
}