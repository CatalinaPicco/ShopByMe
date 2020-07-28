package com.example.personalshop.model.description

class DescriptionResponse (

    val text : String,
    val plain_text: String,
    val last_updated : String,
    val date_created : String,
    val snapshot : Snapshot
)

class Snapshot (

    val url : String,
    val width : Double,
    val height : Double,
    val status : String
)