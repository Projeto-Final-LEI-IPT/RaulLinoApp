package com.example.raullino

import android.content.Context
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

/* Classe para aceder ao repositório de informação sobre os edifícios
Instruções para usar:
    val jsonParser = JsonParse(context) //context é o contexto da atividade onde estão a chamar a classe
    jsonParser será o vosso objeto que possui diversos métodos para aceder aos dados
Lista de métodos:
    jsonParser.get_number()             - Número de edificio
    jsonParser.get_title(id)            - Título do edificio
    jsonParser.get_year(id)             - Ano do edificio
    jsonParser.get_location(id)         - Morada do edificio
    jsonParser.get_coordinates(id)      - Coordenadas do edificio
    jsonParser.get_typology(id)         - Tipologia do edificio
    jsonParser.get_info(id)             - Informação acerca do edificio
    jsonParser.get_image(id)            - Caminho das imagens dos edificios
 */
class JsonParse(val context: Context) {

    //Função que calcula o número de objetos no ficheiro json dentro do array "dados"
    //Retorna Inteiro
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

    //Função que dado o id do objeto procura o título
    //Retorna String
    fun get_title(id: Int): String {
        var json : String? = null
        try {
            val inputStream: InputStream = context.assets.open("data.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (e: IOException) {
        }
        val json_object = JSONObject(json)
        val json_array = json_object.getJSONArray("dados")
        val json_object2 = json_array.getJSONObject(id)
        val title = json_object2.getString("titulo")
        return title
    }

    //Função que dado o id do objeto procura o ano
    //Retorna String
    fun get_year(id: Int): String {
        var json : String? = null
        try {
            val inputStream: InputStream = context.assets.open("data.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (e: IOException) {
        }
        val json_object = JSONObject(json)
        val json_array = json_object.getJSONArray("dados")
        val json_object2 = json_array.getJSONObject(id)
        val year = json_object2.getString("ano")
        return year
    }

    //Função que dado o id do objeto procura a localização descritiva
    //Retorna String
    fun get_location(id: Int): String {
        var json : String? = null
        try {
            val inputStream: InputStream = context.assets.open("data.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (e: IOException) {
        }
        val json_object = JSONObject(json)
        val json_array = json_object.getJSONArray("dados")
        val json_object2 = json_array.getJSONObject(id)
        val location = json_object2.getString("localizacao")
        return location
    }

    //Função que dado o id do objeto procura a localização em coordenadas (dado que estava em array, agrupei as coordenadas separadas por uma "," numa única String)
    //Retorna String
    fun get_coordinates(id: Int): String {
        var json : String? = null
        try {
            val inputStream: InputStream = context.assets.open("data.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (e: IOException) {
        }
        val json_object = JSONObject(json)
        val json_array = json_object.getJSONArray("dados")
        val json_object2 = json_array.getJSONObject(id)
        val json_array2 = json_object2.getJSONArray("coordenadas")
        val latitude = json_array2.getString(0)
        val longitude = json_array2.getString(1)
        val coordinates = latitude + "," + longitude
        return coordinates
    }


    //Função que dado o id do objeto procura a tipologia
    //Retorna String
    fun get_typology(id: Int): String {
        var json : String? = null
        try {
            val inputStream: InputStream = context.assets.open("data.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (e: IOException) {
        }
        val json_object = JSONObject(json)
        val json_array = json_object.getJSONArray("dados")
        val json_object2 = json_array.getJSONObject(id)
        val typology = json_object2.getString("tipologia")
        return typology
    }

    //Função que dado o id do objeto procura a informação
    //Retorna String
    fun get_info(id: Int): String {
        var json : String? = null
        try {
            val inputStream: InputStream = context.assets.open("data.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (e: IOException) {
        }
        val json_object = JSONObject(json)
        val json_array = json_object.getJSONArray("dados")
        val json_object2 = json_array.getJSONObject(id)
        val info = json_object2.getString("info")
        return info
    }

    //Função que dado o id do objeto procura todas as suas imagens
    //Retorna Array<String>
    fun get_image(id: Int): Array<String> {
        var json : String? = null
        try {
            val inputStream: InputStream = context.assets.open("data.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (e: IOException) {
        }
        val json_object = JSONObject(json)
        val json_array = json_object.getJSONArray("dados")
        val json_object2 = json_array.getJSONObject(id)
        val json_array2 = json_object2.getJSONArray("imagens")
        val number = json_array2.length()
        val image = arrayOfNulls<String>(number)
        for (i in 0 until number) {
            image[i] = json_array2.getString(i)
        }
        return image as Array<String>
    }

}