package com.example.medicineschedule.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicineschedule.BR
import com.example.medicineschedule.R
import com.example.medicineschedule.models.AlterBrandModel
import com.example.medicineschedule.models.DummyRecModel
import com.fraggjkee.recycleradapter.RecyclerItem
import java.util.ArrayList

class AlternateBrand_ViewModel: ViewModel() {

    private val _recyclerItems = MutableLiveData<List<RecyclerItem>>()
    val recyclerItems: LiveData<List<RecyclerItem>> = _recyclerItems

    init {
        _recyclerItems.value = dataGenerter().map { it.toRecyclerItem() }
    }


    private fun AlterBrandModel.toRecyclerItem() = RecyclerItem(
        data = this,
        layoutId = R.layout.view_of_alternat_brands,
        variableId = BR.model
    )
    fun dataGenerter():ArrayList<AlterBrandModel>{
        val list: ArrayList<AlterBrandModel> = ArrayList()
        list.add(AlterBrandModel("ZEGESIC 250mg Tablet 200s"))
        list.add(AlterBrandModel("ZOPAN 250mg Tablet 10x10s"))
        list.add(AlterBrandModel("WENAMIC 250mg Tablet 10x10s"))
        list.add(AlterBrandModel("WILSTAN 250mg Tablet 20x10s"))
        return list
    }
}