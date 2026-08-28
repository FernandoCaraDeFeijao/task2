package com.fernando.myapplication.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task (
    val id: String,
    val description: String,
    //Por padrão, toda nova tarefa e criada com o status TODO.
    val status: Status = Status.TODO
): Parcelable