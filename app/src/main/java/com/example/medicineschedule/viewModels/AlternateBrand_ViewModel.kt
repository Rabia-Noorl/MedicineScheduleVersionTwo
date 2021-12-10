package com.example.medicineschedule.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.medicineschedule.BR
import com.example.medicineschedule.DictionaryActivity
import com.example.medicineschedule.R
import com.example.medicineschedule.database.FirebaseServiceDrug
import com.example.medicineschedule.models.Drug
import com.fraggjkee.recycleradapter.RecyclerItem
import kotlinx.coroutines.launch
import java.util.ArrayList

class AlternateBrand_ViewModel(application: Application) : AndroidViewModel(application)  {

    var list = ArrayList<String>()
        private var _drugRecode = MutableLiveData<List<String>?>()
        val drugRecord: MutableLiveData<List<String>?> = _drugRecode

    private val _recyclerItems = MutableLiveData<List<RecyclerItem>>()
    val recyclerItems: LiveData<List<RecyclerItem>> = _recyclerItems

    // Real-world apps should use SingleLiveData instead. RxJava / Coroutines could also work
    // better for one-time event streams.
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

   init {
       viewModelScope.launch {
           var drug:Drug? = DictionaryActivity.drugName?.let {
               FirebaseServiceDrug.FirebaseProfileService.getDrugeData(
                   it
               )
           }
           if (drug != null) {
               list.add(drug.ab1)
               list.add(drug.ab2)
               list.add(drug.ab3)
               list.add(drug.ab4)
               list.add(drug.ab5)
               _drugRecode.postValue(list)
           }
       }
    }

    fun ResValue(list: List<String>) {
        _recyclerItems.value = list
            ?.map {
                createUserItemViewModel(it)
            }
            ?.map {
                it.toRecyclerItem()
            }

    }

    private fun createUserItemViewModel(alterBrand:String): AlterBrandItemViewModel {
        return AlterBrandItemViewModel(alterBrand).apply {
            itemClickHandler = { drug -> showClickMessage(alterBrand) }
        }
    }

    private fun showClickMessage(alterBrand:String) {
        _toastMessage.postValue(
            "${alterBrand}  is clicked"
        )
    }
        fun AlterBrandItemViewModel.toRecyclerItem() = RecyclerItem(
        data = this,
        layoutId = R.layout.view_of_alternat_brands,
        variableId = BR.model
    )
}