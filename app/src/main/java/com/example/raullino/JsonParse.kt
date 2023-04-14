package com.example.raullino

import android.content.Context
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

// Classe para aceder ao repositório de informação sobre os edifícios
class JsonParse(val context: Context) {

    //Abrir o ficheiro json previamente para não ter de o abrir sempre que for necessário
    val json: String? = try {
        val inputStream: InputStream = context.assets.open("data.json")
        inputStream.bufferedReader().use { it.readText() }
    } catch (e: IOException) {
        null
    }

    //Função que calcula o número de objetos no ficheiro json dentro do array "dados"
    //Retorna Inteiro
    fun get_number(): Int {
        val json_object = JSONObject(json)
        val json_array = json_object.getJSONArray("dados")
        val number = json_array.length()
        return number
    }

    //Função que dado o id do edifício procura o título
    //Retorna String
    fun get_title(id: String): String {
        val json_object = JSONObject(json)
        val json_array = json_object.getJSONArray("dados")
        val number = json_array.length()
        var title = ""
        for (i in 0 until number) {
            val json_object_aux = json_array.getJSONObject(i)
            val id_aux = json_object_aux.getString("id")
            if (id_aux == id) {
                title = json_object_aux.getString("titulo")
            }
        }
        return title
    }

    //Função que dado o id do edifício procura o ano
    //Retorna String
    fun get_year(id: String): String {
        val json_object = JSONObject(json)
        val json_array = json_object.getJSONArray("dados")
        val number = json_array.length()
        var year = ""
        for (i in 0 until number) {
            val json_object_aux = json_array.getJSONObject(i)
            val id_aux = json_object_aux.getString("id")
            if (id_aux == id) {
                year = json_object_aux.getString("ano")
            }
        }
        return year
    }

    //Função que dado o id do edifício procura a localização descritiva
    //Retorna String
    fun get_location(id: String): String {
        val json_object = JSONObject(json)
        val json_array = json_object.getJSONArray("dados")
        val number = json_array.length()
        var location = ""
        for (i in 0 until number) {
            val json_object_aux = json_array.getJSONObject(i)
            val id_aux = json_object_aux.getString("id")
            if (id_aux == id) {
                location = json_object_aux.getString("localizacao")
            }
        }
        return location
    }

    //Função que dado o id do edifício procura a localização em coordenadas (dado que estava em array, agrupei as coordenadas separadas por uma "," numa única String)
    //Retorna String
    fun get_coordinates(id: String): String {
        val json_object = JSONObject(json)
        val json_array = json_object.getJSONArray("dados")
        val number = json_array.length()
        var coordinates = ""
        for (i in 0 until number) {
            val json_object_aux = json_array.getJSONObject(i)
            val id_aux = json_object_aux.getString("id")
            if (id_aux == id) {
                val json_array_aux = json_object_aux.getJSONArray("coordenadas")
                val latitude = json_array_aux.getString(0)
                val longitude = json_array_aux.getString(1)
                coordinates = latitude + "," + longitude
            }
        }
        return coordinates
    }


    //Função que dado o id do edifício procura a tipologia
    //Retorna String
    fun get_typology(id: String): String {
        val json_object = JSONObject(json)
        val json_array = json_object.getJSONArray("dados")
        val number = json_array.length()
        var typology = ""
        for (i in 0 until number) {
            val json_object_aux = json_array.getJSONObject(i)
            val id_aux = json_object_aux.getString("id")
            if (id_aux == id) {
                typology = json_object_aux.getString("tipologia")
            }
        }
        return typology
    }

    //Função que dado o id do edifício procura a informação
    //Retorna String
    fun get_info(id: String): String {
        val json_object = JSONObject(json)
        val json_array = json_object.getJSONArray("dados")
        val number = json_array.length()
        var info = ""
        for (i in 0 until number) {
            val json_object_aux = json_array.getJSONObject(i)
            val id_aux = json_object_aux.getString("id")
            if (id_aux == id) {
                info = json_object_aux.getString("info")
            }
        }
        return info
    }

    //Função que dado o id do edifício procura todas as suas imagens
    //Retorna Array<String>
    fun get_image(id: String): Array<String> {
        val json_object = JSONObject(json)
        val json_array = json_object.getJSONArray("dados")
        val number = json_array.length()
        var image = arrayOfNulls<String>(0)
        for (i in 0 until number) {
            val json_object_aux = json_array.getJSONObject(i)
            val id_aux = json_object_aux.getString("id")
            if (id_aux == id) {
                val json_array_aux = json_object_aux.getJSONArray("imagens")
                val number_aux = json_array_aux.length()
                image = arrayOfNulls<String>(number_aux)
                for (j in 0 until number_aux) {
                    image[j] = json_array_aux.getString(j)
                }
            }
        }
        return image as Array<String>
    }

    //Função que retorna todos o Id, Título e Coordenadas de todos os objetos. Cada objeto é um array de 3 itens
    //Retorna Array<Array<String>>
    fun get_buildings(): Array<Array<String>> {
        val json_object = JSONObject(json)
        val json_array = json_object.getJSONArray("dados")
        val number = json_array.length()
        val array_group = arrayOfNulls<Array<String>>(number)
        for (i in 0 until number) {
            val json_object_aux = json_array.getJSONObject(i)
            val id = json_object_aux.getString("id")
            val title = json_object_aux.getString("titulo")
            val coordinates = get_coordinates(id)
            array_group[i] = arrayOf(id, title, coordinates)
        }
        return array_group as Array<Array<String>>
    }

}