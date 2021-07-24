package com.etwicaksono.iscan.utils

import android.content.Context
import android.content.SharedPreferences
import com.etwicaksono.iscan.data.TokoEntity
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class UserPref(val context: Context) {

    companion object {
        const val USER_PREF = "USER_PREF"
    }

    var sharedPreferences = context.getSharedPreferences(USER_PREF , 0)

    fun setValue(key: String , value: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key , value)
        editor.apply()
    }

    fun getValues(key: String): String? {
        return sharedPreferences.getString(key , "")
    }

    fun updateRecentToko(data: TokoEntity?) {
        getValues("recent_store")?.let {
            val id = data?.id
            val gson = GsonBuilder().create()
            var recentToko = gson.fromJson<ArrayList<String>>(it ,
                object : TypeToken<ArrayList<String>>() {}.type
            )

            if (recentToko.isNullOrEmpty()) recentToko = arrayListOf()

            val index = recentToko.indexOf(id)
            if (index >= 0) {
                recentToko.removeAt(index)
            }

            if (id != null) {
                recentToko.add(id)
            }
            UserPref(context).setValue("recent_store" , JSONArray(recentToko).toString())
        }
    }

/*
* LIST sharedPreferences
* 1. recent_store
* */
}