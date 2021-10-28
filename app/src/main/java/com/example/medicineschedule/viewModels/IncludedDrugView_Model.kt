package com.example.medicineschedule.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicineschedule.BR
import com.example.medicineschedule.R
import com.example.medicineschedule.models.IncludedDrudModel
import com.fraggjkee.recycleradapter.RecyclerItem
import java.util.ArrayList

class IncludedDrugView_Model:  ViewModel()  {

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage


    private val _recyclerItems = MutableLiveData<List<RecyclerItem>>()
    val recyclerItems: LiveData<List<RecyclerItem>> = _recyclerItems

    init {
        _recyclerItems.value = dataGenerter().map { it.toRecyclerItem() }
    }
    private fun createUserItemViewModel(incdudedDrug:IncludedDrudModel): DrugRecode_Viewmode {
        return DrugRecode_Viewmode(incdudedDrug).apply {
            itemClickHandler = { incdudedDrug -> showClickMessage(incdudedDrug) }
        }
    }
    private fun showClickMessage(user: IncludedDrudModel) {
        _toastMessage.postValue(
            "$user is clicked"
        )
    }
    
    private fun IncludedDrudModel.toRecyclerItem() = RecyclerItem(
        data = this,
        layoutId = R.layout.view_included_drugs,
        variableId = BR.includedDrugModel
    )
    fun dataGenerter(): ArrayList<IncludedDrudModel> {
        val list: ArrayList<IncludedDrudModel> = ArrayList()
        list.add(IncludedDrudModel("ZEGESIC 250mg Tablet 200s"))
        return list
    }
}