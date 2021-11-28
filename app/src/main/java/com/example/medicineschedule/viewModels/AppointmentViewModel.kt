package com.example.medicineschedule.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.medicineschedule.BR
import com.example.medicineschedule.R
import com.example.medicineschedule.database.ReminderDatabase
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.database.Repository
import com.fraggjkee.recycleradapter.RecyclerItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppointmentViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    val allRemiders: LiveData<List<ReminderTracker>>
    val dao = ReminderDatabase.getDatabase(application).getReminderDao()
    val allRec = ArrayList<ReminderTracker>()


    private val _recyclerItems = MutableLiveData<List<RecyclerItem>>()
    val recyclerItems: LiveData<List<RecyclerItem>> = _recyclerItems

    // Real-world apps should use SingleLiveData instead. RxJava / Coroutines could also work
    // better for one-time event streams.
    private val _reminder = MutableLiveData<ReminderTracker>()
    val reminder: LiveData<ReminderTracker> = _reminder


    init {
        repository = Repository(dao)
        allRemiders = repository.allReminder

        _recyclerItems.value = allRemiders.value
            ?.map { creatReminderItemViewModel(it) }
            ?.map { it.toRecyclerItem() }
    }

    fun creatReminderItemViewModel(remineder:ReminderTracker): ReminderItemViewModel {
        return ReminderItemViewModel(remineder).apply {
            itemClickHandler = { remineder -> getReminderRecord(remineder) }
            deleteBtnClickHandler = { remineder -> deletDrug(remineder) }
            addDrugClickHandler = { remineder -> onAddClick(remineder)}
        }
    }
    private fun getReminderRecord(remineder: ReminderTracker) {
        _reminder.postValue(
            remineder
        )
    }
    private fun deletDrug(remineder: ReminderTracker) {
        val items = recyclerItems.value.orEmpty()
        val index = items.map { it.data }
            .filterIsInstance<ReminderItemViewModel>()
            .indexOfFirst { it.reminderTracker == remineder }
        if (index != -1) {
            _recyclerItems.value = items.toMutableList().apply { removeAt(index) }
        }

    }

    fun onAddClick(remineder: ReminderTracker) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(remineder)
    }

    fun addFun(remineder: List<ReminderTracker>){
        allRec.clear()
        allRec.addAll(remineder)
        _recyclerItems.value = ArrayList()
        val itr = allRec.iterator()
        while (itr.hasNext()){
            val newItem = creatReminderItemViewModel(itr.next()).toRecyclerItem()
            _recyclerItems.value = recyclerItems.value.orEmpty() + newItem
        }
    }

    fun onShuffleClick() {
        _recyclerItems.value = recyclerItems.value.orEmpty().shuffled()
    }
    fun onDeletClick(reminder: ReminderTracker) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(reminder)
    }
    fun onEditClick(remineder: ReminderTracker) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(remineder)
    }

    private fun ReminderItemViewModel.toRecyclerItem() = RecyclerItem(
        data = this,
        layoutId = R.layout.view_of_appointments,
        variableId = BR.appointmentModel

    )
}