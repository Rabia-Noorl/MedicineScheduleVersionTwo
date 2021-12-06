package com.example.medicineschedule.viewModels

import com.example.medicineschedule.models.Drug

class AlterBrandItemViewModel(var alterBrand:String) {

        lateinit var itemClickHandler: (alterBrand:String) -> Unit

        fun onItemClick() {
            itemClickHandler(alterBrand)
        }
}