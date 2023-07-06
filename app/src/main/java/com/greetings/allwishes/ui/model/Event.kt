package com.greetings.allwishes.ui.model

import androidx.annotation.Keep

@Keep
data class Event(
    var date: String? = null,
    var icon: String? = null,
    var name: String? = null,
    var udate: String? = null
)
