package com.example.medicineschedule.viewModels

import com.example.medicineschedule.database.ReminderTracker

class ReminderItemViewModel(val reminderTracker: ReminderTracker) {
    lateinit var itemClickHandler: (reminderTracker: ReminderTracker) -> Unit
    lateinit var deleteBtnClickHandler: (reminderTracker: ReminderTracker) -> Unit
    lateinit var addDrugClickHandler: (reminderTracker: ReminderTracker) -> Unit

    fun onItemClick() {
        itemClickHandler(reminderTracker)
    }

    fun onDeleteButtonClick() {
        deleteBtnClickHandler(reminderTracker)
    }
    fun addButtonClickd() {
        addDrugClickHandler(reminderTracker)
    }
}