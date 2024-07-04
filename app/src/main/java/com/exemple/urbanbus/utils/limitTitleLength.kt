package com.exemple.urbanbus.utils

// limita o nome que vai ser mostrado na tool bar em 15 caracteres
fun String.limitTitleLength(maxLength: Int): String {
    return if (this.length > maxLength) {
        this.substring(0, maxLength) + "..."
    } else {
        this
    }
}