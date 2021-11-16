package com.example.medicineschedule.viewModels

import com.example.medicineschedule.models.Drug

class MedicineItemViewModel(var drug:Drug){
    lateinit var itemClickHandler: (drug: Drug) -> Unit

    fun onItemClick() {
        itemClickHandler(drug)
    }
}
