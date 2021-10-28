package com.example.medicineschedule.viewModels

import com.example.medicineschedule.models.IncludedDrudModel

class DrugRecode_Viewmode(val drug_item: IncludedDrudModel) {

    lateinit var itemClickHandler: (user: IncludedDrudModel) -> Unit

    fun onItemClick() {
        itemClickHandler(drug_item)
    }
}