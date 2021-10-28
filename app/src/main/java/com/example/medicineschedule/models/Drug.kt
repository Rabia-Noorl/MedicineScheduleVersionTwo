package com.example.medicineschedule.models

data class Drug(
    val name: String,
    val dose: String,
    val status: String,
    val type: String
)

val Drug_List: List<Drug> =
    listOf(
        Drug(
            "Pills", "Once a Day", "notTaken", "pills"
        ),
        Drug(
            "Injection", "one at 8:pm", "notTaken", "syringe"
        ),
        Drug(
            "Caps", "Two at 8:pm", "notTaken", "capsole"
        ),
        Drug(
            "Caps", "Two at 8:pm", "notTaken", "capsole"
        ),
        Drug(
            "Pills", "Once a Day", "notTaken", "pills"
        ),
        Drug(
            "Pills", "Once a Day", "notTaken", "pills"
        ),
        Drug(
            "Injection", "one at 8:pm", "notTaken", "syringe"
        ),
        Drug(
            "Caps", "Two at 8:pm", "notTaken", "capsole"
        ),
        Drug(
            "Caps", "Two at 8:pm", "notTaken", "capsole"
        ),
        Drug(
            "Pills", "Once a Day", "notTaken", "pills"
        )

    )

