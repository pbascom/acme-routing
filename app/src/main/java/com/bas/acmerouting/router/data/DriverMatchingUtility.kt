package com.bas.acmerouting.router.data

import java.lang.Integer.min

/**
 * Adapts the json structure specified by the coding assignment into a Map of (Driver, Route) pairs.
 * See [ShipmentRepository] kdoc for an explanation of the motivation behind this class.
 */
class DriverMatchingUtility {
    fun adapt(response: ShipmentResponse) : Map<Driver, Route> {
        val shipments = hashMapOf<Driver, Route> ()
        // TODO: Implement business logic rules
        for(index in 0 until min(response.drivers.size, response.routes.size)) {
            shipments[Driver(response.drivers[index])] = Route(response.routes[index])
        }
        return shipments
    }
}