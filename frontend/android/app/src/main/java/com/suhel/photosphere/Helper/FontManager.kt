package com.suhel.photosphere.Helper

val FONT_SANS_LIGHT = "lato-light.ttf"
val FONT_SANS_REGULAR = "lato-regular.ttf"
val FONT_SANS_MEDIUM = "lato-medium.ttf"
val FONT_SANS_BOLD = "lato-bold.ttf"
val FONT_SANS_ALT_LIGHT = "sourcesanspro-light.ttf"
val FONT_SANS_ALT_REGULAR = "sourcesanspro-regular.ttf"
val FONT_SANS_ALT_BOLD = "sourcesanspro-bold.ttf"

fun fontName(byValue: Int?): String = when (byValue) {
    0 -> FONT_SANS_LIGHT
    1 -> FONT_SANS_REGULAR
    2 -> FONT_SANS_MEDIUM
    3 -> FONT_SANS_BOLD
    4 -> FONT_SANS_ALT_LIGHT
    5 -> FONT_SANS_ALT_REGULAR
    6 -> FONT_SANS_ALT_BOLD
    else -> FONT_SANS_REGULAR
}