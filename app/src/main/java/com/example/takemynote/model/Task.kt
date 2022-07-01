package com.example.takemynote.model

import android.os.Parcelable
import com.example.takemynote.helper.FirebaseHelper
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    var id: String= "",
    var description: String = "",
    var status: Int = 0
//salvar tarefa no firebase
) : Parcelable{
    init{
        this.id = FirebaseHelper.getDatabase().push().key ?: ""
    }

}
