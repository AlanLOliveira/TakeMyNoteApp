package com.example.takemynote.helper

import com.example.takemynote.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FirebaseHelper {

    companion object{

        //Tratar as referencia do banco de dados e atualizar cada operação do banco de dados
        fun getDatabase() = FirebaseDatabase.getInstance().reference

        //Recuparar a instancia do Usuario autenticado
        private fun getAuth() = FirebaseAuth.getInstance()

        //Recuperar Id do usuario que está logado
        fun getIdUser() = getAuth().uid

        //Verificar se usuario está logado no app
        fun isAutenticated() = getAuth().currentUser != null

        //Exiber os erros no firebase
        fun validError(error: String) : Int {
            return when {
                error.contains(" There is no user record corresponding to this identifier") -> {
                    R.string.There_is_no_user_record_corresponding_to_this_identifier
                }
                error.contains("The email address is badly formatted") ->{
                    R.string.The_email_address_is_badly_formatted
                }
                error.contains("The password is invalid or the user does not have a password") ->{
                    R.string.The_password_is_invalid
                }
                error.contains("The email address is already in use by another account") ->{
                    R.string.The_email_address_is_already
                }
                error.contains("Password should be at least 6 characters")->{
                    R.string.Password_should_be_at_least_6_characters
                }
                else -> {
                    R.string.error_generic
                }
            }
        }

    }
}