package com.bas.acmerouting.router.data

/**
 * An implementation of [ShipmentRepository] which draws data from a local assets file, and adapts
 * it with a DriverMatchingUtility.
 */
class LocalShipmentRepository: ShipmentRepository {
    val adapter = DriverMatchingUtility()

    override suspend fun getShipments(): Map<Driver, Route> {
        val response = ShipmentResponse(listOf(), listOf()) // TODO: Read from disk
        return adapter.adapt(response)
    }
}