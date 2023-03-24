package com.example.raullino.ui.biography

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BiographyViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Biography Fragment"
    }
    val text: LiveData<String> = _text
}