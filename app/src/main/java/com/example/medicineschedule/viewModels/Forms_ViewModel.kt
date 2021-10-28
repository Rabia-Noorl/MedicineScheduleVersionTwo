package com.example.medicineschedule.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicineschedule.BR
import com.example.medicineschedule.R
import com.example.medicineschedule.models.AlterBrandModel
import com.example.medicineschedule.models.FormModel
import com.fraggjkee.recycleradapter.RecyclerItem
import java.util.ArrayList

class Forms_ViewModel:   ViewModel() {

    private val _recyclerItems = MutableLiveData<List<RecyclerItem>>()
    val recyclerItems: LiveData<List<RecyclerItem>> = _recyclerItems

    init {
        _recyclerItems.value = dataGenerter().map { it.toRecyclerItem() }
    }


    private fun FormModel.toRecyclerItem() = RecyclerItem(
        data = this,
        layoutId = R.layout.view_of_availabale_form,
        variableId = BR.formModel
    )
    fun dataGenerter(): ArrayList<FormModel> {
        val list: ArrayList<FormModel> = ArrayList()
        list.add(FormModel("Tablets", "20x10's", "Mefenamic Acid:250mg", "175.00"))
        list.add(FormModel("Tablets", "20x10's", "Mefenamic Acid:500mg", "320.00"))
        return list
    }
}