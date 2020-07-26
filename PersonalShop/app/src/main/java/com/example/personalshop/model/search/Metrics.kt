package com.example.personalshop.model.search

import java.io.Serializable

class Metrics (

    val claims : Claims,
    val delayed_handling_time : Delayed_handling_time,
    val sales : Sales,
    val cancellations : Cancellations
): Serializable