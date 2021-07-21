package com.etwicaksono.iscan.utils

import android.content.Context
import android.content.SharedPreferences

class UserPref(val context: Context) {

    companion object{
        const val USER_PREF="USER_PREF"
    }

    var sharedPreferences=context.getSharedPreferences(USER_PREF,0)

    fun setValue(key:String,value:String){
        val editor: SharedPreferences.Editor=sharedPreferences.edit()
        editor.putString(key,value)
        editor.apply()
    }

    fun getValues(key: String):String?{
        return sharedPreferences.getString(key,"")
    }

/*
* LIST sharedPreferences
* 1. recent_store
* */
}