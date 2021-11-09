package com.example.medicineschedule.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicineschedule.BR
import com.example.medicineschedule.R
import com.example.medicineschedule.models.AlterBrandModel
import com.example.medicineschedule.models.Drug
import com.example.medicineschedule.models.DummyRecModel
import com.fraggjkee.recycleradapter.RecyclerItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import java.util.ArrayList

class AlternateBrand_ViewModel: ViewModel() {

    private var _drugRecode = MutableLiveData<List<Drug>?>()
    val drugRecord: MutableLiveData<List<Drug>?> = _drugRecode

    private val _recyclerItems = MutableLiveData<List<RecyclerItem>>()
    val recyclerItems: LiveData<List<RecyclerItem>> = _recyclerItems

    // Real-world apps should use SingleLiveData instead. RxJava / Coroutines could also work
    // better for one-time event streams.
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    init {
        firebaseServise()
        val list : ArrayList<Drug> = ArrayList()
        list.add(Drug("","new drug","","","","","","",""))
        _drugRecode.value = list
        _recyclerItems.value = drugRecord.value
            ?.map {
                createUserItemViewModel(it)
            }
            ?.map {
                it.toRecyclerItem()
            }
    }

    private fun createUserItemViewModel(drug: Drug): DrugItemViewModel {
        return DrugItemViewModel(drug).apply {
            itemClickHandler = { user -> showClickMessage(drug) }
        }
    }

    private fun showClickMessage(drug: Drug) {
        _toastMessage.postValue(
            "${drug.name} ${drug.price} is clicked"
        )
    }
        fun DrugItemViewModel.toRecyclerItem() = RecyclerItem(
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

    fun firebaseServise(){
        var data = hashMapOf<String,Any>()
        data.put("name", "Drug")
        data.put("type", "Injection")
        data.put("price", "Rs. 500")

        var firbaseFirestores: FirebaseFirestore = FirebaseFirestore.getInstance()
        firbaseFirestores.collection("Medicines").document("med3")
            .set(data)
                .addOnCompleteListener{
                if(it.isSuccessful)
                {

                }else
                {


                }

            }
    }
}