package com.example.raullino

import android.content.Context
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class jsonParse(val context: Context) {

    //Function to get the number of objects in the json file
    fun get_number(): Int {
        var json: String? = null
        try {
            val inputStream: InputStream = context.assets.open("data.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (e: IOException) {
        }
        val json_object = JSONObject(json)
        val json_array = json_object.getJSONArray("dados")
        val number = json_array.length()
        return number
    }
}