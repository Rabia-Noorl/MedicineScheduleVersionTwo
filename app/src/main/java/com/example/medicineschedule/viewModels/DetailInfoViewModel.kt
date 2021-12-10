package com.example.medicineschedule.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.medicineschedule.BR
import com.example.medicineschedule.DictionaryActivity
import com.example.medicineschedule.R
import com.example.medicineschedule.database.FirebaseServiceDrug
import com.example.medicineschedule.models.Drug
import com.example.medicineschedule.models.DummyRecModel
import com.fraggjkee.recycleradapter.RecyclerItem
import kotlinx.coroutines.launch
import java.util.*

open class DetailInfoViewModel(application: Application) : AndroidViewModel(application)  {

    var list = ArrayList<String>()
    private var _drugRecode = MutableLiveData<List<Drug>?>()
    val drugRecord: MutableLiveData<List<Drug>?> = _drugRecode

    private val _recyclerItems = MutableLiveData<List<RecyclerItem>>()
    val recyclerItems: LiveData<List<RecyclerItem>> = _recyclerItems

    // Real-world apps should use SingleLiveData instead. RxJava / Coroutines could also work
    // better for one-time event streams.
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    init {
        viewModelScope.launch {
            var drug: Drug? = DictionaryActivity.drugName?.let {
                FirebaseServiceDrug.FirebaseProfileService.getDrugeData(
                    it
                )
            }
            if (drug != null) {
                _drugRecode.postValue(listOf(drug))
            }
        }
    }

    fun ResValue(list: List<Drug>) {
        _recyclerItems.value = list
            ?.map {
                createUserItemViewModel(it)
            }
            ?.map {
                it.toRecyclerItem()
            }

    }

    private fun createUserItemViewModel(drug: Drug): DrugItemViewModel {
        return DrugItemViewModel(drug).apply {
            itemClickHandler = { drug -> showClickMessage(drug) }
        }
    }

    private fun showClickMessage(drug: Drug) {
        _toastMessage.postValue(
            "${drug.name}  is clicked"
        )
    }
    private fun DrugItemViewModel.toRecyclerItem() = RecyclerItem(
        data = this,
        layoutId = R.layout.detail_info_recyclerview_view,
        variableId = BR.model
    )
}