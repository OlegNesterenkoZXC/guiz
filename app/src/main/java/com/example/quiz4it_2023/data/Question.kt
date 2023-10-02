package com.example.quiz4it_2023.data

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean)
