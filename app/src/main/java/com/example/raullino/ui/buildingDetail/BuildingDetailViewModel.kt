package com.example.raullino.ui.buildingDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BuildingDetailViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Detail Fragment"
    }
    val text: LiveData<String> = _text
}