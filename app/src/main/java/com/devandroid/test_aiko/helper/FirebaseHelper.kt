package com.devandroid.workoutschedule.helper

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class FirebaseHelper {

    companion object{

        fun getDatabase() = FirebaseDatabase.getInstance().reference

        fun getIdUser() = getAuth().uid
        private fun getAuth() = FirebaseAuth.getInstance()

        fun isAuthenticated() = getAuth().currentUser != null
    }

}