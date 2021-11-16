package com.example.medicineschedule.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.medicineschedule.BR
import com.example.medicineschedule.R
import com.example.medicineschedule.database.ReminderTracker
import com.fraggjkee.recycleradapter.RecyclerItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MedicineRecViewModel(application: Application) : AndroidViewModel(application) {

  //  val allRemiders: LiveData<List<ReminderTracker>>
    val allRec = ArrayList<ReminderTracker>()


    private val _recyclerItems = MutableLiveData<List<RecyclerItem>>()
    val recyclerItems: LiveData<List<RecyclerItem>> = _recyclerItems

    // Real-world apps should use SingleLiveData instead. RxJava / Coroutines could also work
    // better for one-time event streams.
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage


    init {
//        repository = Repository(dao)
     //    allRemiders = getMedication()
      //  allRemiders = allRec
        _recyclerItems.value = allRec
            ?.map { creatReminderItemViewModel(it) }
            ?.map { it.toRecyclerItem() }
    }


    fun creatReminderItemViewModel(remineder:ReminderTracker): ReminderItemViewModel {
        return ReminderItemViewModel(remineder).apply {
            itemClickHandler = { remineder -> showClickMessage(remineder) }
        }
    }
    private fun showClickMessage(remineder: ReminderTracker) {
        _toastMessage.postValue(
            "${remineder.names} is clicked"
        )
    }

    fun addFun(remineder:ReminderTracker){
       // allRec.clear()
        allRec.add(remineder)
       // _recyclerItems.value = ArrayList()
        val itr = allRec.iterator()
        while (itr.hasNext()){
            val newItem = creatReminderItemViewModel(itr.next()).toRecyclerItem()
            _recyclerItems.value = recyclerItems.value.orEmpty() + newItem
        }
    }

    private fun ReminderItemViewModel.toRecyclerItem() = RecyclerItem(
        data = this,
        layoutId = R.layout.view_of_measurment_rv,
        variableId = BR.measurementModel

    )

}