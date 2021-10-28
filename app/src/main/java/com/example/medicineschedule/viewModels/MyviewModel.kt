package com.example.medicineschedule.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.medicineschedule.BR
import com.example.medicineschedule.R
import com.example.medicineschedule.models.Drug
import com.example.medicineschedule.models.DummyRecModel
import com.fraggjkee.recycleradapter.RecyclerItem
import java.util.*

open class MyviewModel:  ViewModel() {

    // public val recData: MutableLiveData<List<DummyRecModel>> = MutableLiveData()
    private val _recyclerItems = MutableLiveData<List<RecyclerItem>>()
    val recyclerItems: LiveData<List<RecyclerItem>> = _recyclerItems

    init {
        _recyclerItems.value = dataGenerter().map { it.toRecyclerItem() }
    }

    private fun DummyRecModel.toRecyclerItem() = RecyclerItem(
        data = this,
        layoutId = R.layout.detail_info_recyclerview_view,
        variableId = BR.model
    )
    fun dataGenerter():List<DummyRecModel>{
        val list: ArrayList<DummyRecModel> = ArrayList()
        list.add(DummyRecModel("General Instructions","Take AAFIA 250mg Tablet 20x10s as instructed by the doctor. Do not take in larger or smaller quantities than recommended. The dosage and the duration of use of this medicine should be kept as low as possible. This medicine should be taken with food. An adequate amount of water should be consumed while taking this medicine."))
        list.add(DummyRecModel("How it works", "This medicine works by stopping the production of certain chemicals in the body that causes swelling, pain, and fever."))
        list.add(DummyRecModel("Side effects","Diarrhea\n" +
                "Cosntipation\n" +
                "Bloating\n" +
                "Headache\n" +
                "Dizziness\n" +
                "Ringing or buzzing in the ears\n" +
                "Blurred vision severe\n" +
                "Weight gain\n" +
                "Shortness of breath severe\n" +
                "Swelling of face, lips, eyelids, tongue, hands and feet severe\n" +
                "Peeling and blistering of skin severe\n" +
                "Fast heartbeat severe\n" +
                "Unusual bleeding or bruising severe\n" +
                "Yellow colored eyes or skin severe\n" +
                "Difficult or painful urination severe\n" +
                "Bloody and Cloudy urine severe\n" +
                "Excessive tiredness"))
        list.add(DummyRecModel("What is it prescribed for?","Acute Pain\n" +
                "This medicine is used to relieve mild to moderate pain associated with sprains, gout, arthritis etc.\n" +
                "\n" +
                "Dysmenorrhea\n" +
                "This medicine is used to relieve pain and cramps associated with menstrual disorders."))
        list.add(DummyRecModel("Warnings","Pregnancy\n" +
                "This medicine is not recommended for use in pregnant women unless potential benefits outweigh the risks associated. Contact your doctor before using this medicine. It should not be used during the third trimester of pregnancy.\n" +
                "\n" +
                "Breast-feeding\n" +
                "Use of this medicine in breastfeeding women is not recommended as the amount passing through the milk and its effect on the infant is unknown. Your doctor may prescribe a safer alternative in such cases.Other medicines\n" +
                "Report the use of all other medicines including supplement and herbs to the doctor before receiving this medicine. Any possible interaction with these medicines should be ruled out before starting the treatment with AAFIA 250mg Tablet 20x10s\n" +
                "\n" +
                "Effect on heart\n" +
                "Use of this medicine for a long duration may cause fluid retention and heart failure. It should be administered with caution in patients suffering from a disease of the heart or blood vessels or patients exposed to the risk factors.\n" +
                "\n" +
                "Asthma\n" +
                "Use of this medicine can cause an acute attack of asthma to occur especially in patients with a history of aspirin sensitivity. It should be used with extreme caution in such cases.\n" +
                "\n" +
                "Peptic ulcer\n" +
                "This medicine should be used with caution in patients suffering from peptic ulcer or other diseases of the gastrointestinal tract.\n" +
                "\n" +
                "Allergic skin reaction\n" +
                "Use of this medicine can cause a series of allergic skin reaction (Stevens-Johnson syndrome or toxic epidermal necrolysis). It should be stopped immediately if signs of a skin allergy are observed."))
        list.add(DummyRecModel("Dosage","Missed Dose\n" +
                "Take the missed dose as soon as you remember. If it is almost time for the next scheduled dose then the missed dose can be skipped.\n" +
                "\n" +
                "Overdose\n" +
                "Contact your doctor immediately if an overdose with this medicine is suspected. Symptoms may include rashes on the skin, confusion, pain in the chest, blurred vision etc. Immediate medical attention is needed if an overdose is confirmed."))
       list.add(DummyRecModel("Interactions","Interaction with Medicines\n" +
               "Glimepiride moderate\n" +
               "Methotrexate severe\n" +
               "Propranolol moderate\n" +
               "Ramipril moderate\n" +
               "Tacrolimus severe\n" +
               "Warfarin severe\n" +
               "Furosemide moderate\n" +
               "Aspirin moderate\n" +
               "Ketorolac severe\n" +
               "Immune globulin (intravenous) severe\n" +
               "Spironolactone moderate\n" +
               "Disease interactions\n" +
               "severe\n" +
               "Use of this medicine should be avoided in patients suffering from aspirin-sensitive asthma. It should be used with caution in patients suffering from other forms of asthma or broncho-obstructive disorders.\n" +
               "\n" +
               "severe\n" +
               "This medicine should be used with caution in patients with pre-existing fluid retention disorder. Fluid retention can be attributed to other conditions like hypertension, heart failure etc. Therapy with this medicine should begin only after these risk factors are ruled out.\n" +
               "\n" +
               "severe\n" +
               "This medicine or other NSAIDs should be taken after consulting a doctor especially if the intended duration is longer than a month. Any symptom indicating ulceration and bleeding like chronic indigestion, appearance of coffee colored dry blood in stools or vomiting of blood should be reported immediately.\n" +
               "\n" +
               "severe\n" +
               "This medicine can cause these fatal skin allergies without any warnings. Signs and symptoms like rashes, hives, fever or other allergic symptoms should be reported without any delay. This condition requires immediate medical intervention.\n" +
               "\n" +
               "severe\n" +
               "This medicine should be taken after consulting a doctor if you are having a kidney disease. Suitable adjustment in dosage and monitoring of kidney functions is required in such situations. It should not be used if the impairment is severe.\n" +
               "\n" +
               "moderate\n" +
               "This medicine should be used with caution in patients having an impairment of liver function. Suitable dose adjustments and monitoring of liver function are recommended in such cases."))

        return list
    }
}