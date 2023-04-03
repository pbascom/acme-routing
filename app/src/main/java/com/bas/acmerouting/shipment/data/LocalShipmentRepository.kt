package com.bas.acmerouting.shipment.data

import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * An implementation of [ShipmentRepository] which draws data from a local assets file, and adapts
 * it with a DriverMatchingUtility.
 *
 * Because the LocalShipmentRepository needs to access the Assets folder, we're providing it with
 * a Context object at creation. Later on we'll supply this dependency through Dagger.
 */
class LocalShipmentRepository(
    private val context: Context, //TODO: Inject all of these with Dagger
    private val adapter: DriverMatchingUtility = DriverMatchingUtility(),
    private val gson: Gson = Gson()
): ShipmentRepository {

    override suspend fun getShipments(): Map<Driver, Destination> {
        // There's no error handling right now
        var response: ShipmentResponse
        withContext(Dispatchers.IO) {
            val responseString = context.assets.open(SHIPMENTS_FILE_LOCATION).use{ stream ->
                stream.bufferedReader().use { reader ->
                    reader.readText()
                }
            }
            response = gson.fromJson(responseString, ShipmentResponse::class.java)
        }
        return adapter.adapt(response)
    }

    companion object {
        private const val SHIPMENTS_FILE_LOCATION = "shipments.json"
    }
}