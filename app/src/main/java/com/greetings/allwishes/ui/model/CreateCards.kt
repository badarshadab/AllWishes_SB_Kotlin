package com.greetings.allwishes.ui.model

import androidx.annotation.Keep


@Keep
data class CreateCards(
    var icon: String ? = null,
    var name: String ? = null
)