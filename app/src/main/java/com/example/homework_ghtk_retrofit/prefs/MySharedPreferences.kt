package com.pro.foodorder.prefs

import android.content.Context

class MySharedPreferences {
    companion object {
        private const val POKEMON_PREFERENCES = "MY_PREFERENCES"
    }

    private var mContext: Context? = null

    private constructor() {}

    constructor(mContext: Context?) {
        this.mContext = mContext
    }


    fun putStringValue(key: String?, s: String?) {
        val pref = mContext!!.getSharedPreferences(
            POKEMON_PREFERENCES, 0)
        val editor = pref.edit()
        editor.putString(key, s)
        editor.apply()
    }


    fun getStringValue(key: String?, defaultValue: String?): String? {
        val pref = mContext!!.getSharedPreferences(
            POKEMON_PREFERENCES, 0)
        return pref.getString(key, defaultValue)
    }



}