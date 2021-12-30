package com.example.medicineschedule.viewModels

import com.example.medicineschedule.models.Drug

class DrugItemViewModel(var drug:Drug) {

    lateinit var itemClickHandler: (drug:Drug) -> Unit

    fun onItemClick() {
        itemClickHandler(drug)
    }

}