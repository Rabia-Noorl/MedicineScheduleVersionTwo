package com.example.medicineschedule.viewModels

import com.example.medicineschedule.models.Drug

class DrugItemViewModel(val drug:Drug) {
    lateinit var itemClickHandler: (drug:Drug) -> Unit
    lateinit var deleteBtnClickHandler: (drug:Drug) -> Unit
    lateinit var addDrugClickHandler: (drug:Drug) -> Unit

    fun onItemClick() {
        itemClickHandler(drug)
    }

    fun onDeleteButtonClick() {
        deleteBtnClickHandler(drug)
    }
    fun addButtonClickd() {
        addDrugClickHandler(drug)
    }
}