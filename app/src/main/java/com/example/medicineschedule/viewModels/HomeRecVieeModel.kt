package com.example.medicineschedule.viewModels

import android.app.Application
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicineschedule.AddDose
import com.example.medicineschedule.R
import com.example.medicineschedule.BR
import com.example.medicineschedule.database.ReminderDatabase
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.database.Repository
import com.example.medicineschedule.models.Drug
import com.example.medicineschedule.models.Drug_List
import com.fraggjkee.recycleradapter.RecyclerItem


class HomeRecVieeModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    val allRemiders: LiveData<List<ReminderTracker>>

    private val _recyclerItems = MutableLiveData<List<RecyclerItem>>()
    val recyclerItems: LiveData<List<RecyclerItem>> = _recyclerItems

    // Real-world apps should use SingleLiveData instead. RxJava / Coroutines could also work
    // better for one-time event streams.
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage


    init {
        val dao = ReminderDatabase.getDatabase(application).getReminderDatabase()
        repository = Repository(dao)
        allRemiders = repository.allReminder

        _recyclerItems.value = loadUsers()
            .map { CreatDrugItemViewModel(it) }
            .map { it.toRecyclerItem() }
    }

     private fun CreatDrugItemViewModel(drug:Drug): DrugItemViewModel {
        return DrugItemViewModel(drug).apply {
            itemClickHandler = { drug -> showClickMessage(drug) }
            deleteBtnClickHandler = { drug -> deletDrug(drug) }
            addDrugClickHandler = { drug -> onAddClick()}
        }
    }
    private fun showClickMessage(drug: Drug) {
        _toastMessage.postValue(
            "${drug.name} is clicked"
        )
    }
    private fun deletDrug(drug: Drug) {
        val items = recyclerItems.value.orEmpty()
        val index = items.map { it.data }
            .filterIsInstance<DrugItemViewModel>()
            .indexOfFirst { it.drug == drug }
        if (index != -1) {
            _recyclerItems.value = items.toMutableList().apply { removeAt(index) }
        }
    }
    private fun loadUsers(): List<Drug> = Drug_List

    fun onAddClick() {

        fun generateNewDrug(): Drug {
            val id = recyclerItems.value.orEmpty().size.inc()
            // item add in recView
            return Drug("new","dose","status","type")
        }

        val drug = generateNewDrug()
        val newItem = CreatDrugItemViewModel(drug ).toRecyclerItem()
        _recyclerItems.value = recyclerItems.value.orEmpty() + newItem
    }

    fun onShuffleClick() {
        _recyclerItems.value = recyclerItems.value.orEmpty().shuffled()
    }

    private fun DrugItemViewModel.toRecyclerItem() = RecyclerItem(
        data = this,
        layoutId = R.layout.view_of_dashboard_rv,
        variableId = BR.dashBmodel
    )
}

