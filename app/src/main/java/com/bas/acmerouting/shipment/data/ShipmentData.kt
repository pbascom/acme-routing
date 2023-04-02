package com.bas.acmerouting.shipment.data

import com.google.gson.annotations.SerializedName

/**
 * I chose to go with "destination" for the object representing the destination address, and
 * "shipment" for the Driver / Destination pair. This was pretty much just a matter of taste; I
 * liked the alliteration. In practice, the naming convention for a data model would be worth a
 * brief conversation with the development team, to make sure we're all on board with the decision.
 * I'd also try to keep it in line with the service team's naming convention, but if our team chose
 * to differ, this is where we would maintain the boundary.
 *
 * Complex data objects would get their own file, but there's no reason to give each of these tiny
 * and tightly coupled classes a separate file.
 */
data class ShipmentResponse(
    @SerializedName("shipments")
    val destinations: List<String>,
    val drivers: List<String>
)

data class Driver(
    val name: String
)

data class Destination(
    val address: String
)
