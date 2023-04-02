package com.bas.acmerouting.shipment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bas.acmerouting.shipment.data.Driver
import com.bas.acmerouting.shipment.data.LocalShipmentRepository
import com.bas.acmerouting.shipment.data.Destination
import com.bas.acmerouting.shipment.data.ShipmentRepository
import kotlinx.coroutines.launch

class ShipmentsViewModel(app: Application): AndroidViewModel(app) {

    // TODO: Inject with Dagger
    val shipmentRepository: ShipmentRepository = LocalShipmentRepository(app.applicationContext)

    // TODO (Optional): Refactor to use Flows
    // Flows aren't strictly necessary here, since we perform a single load operation in onCreate
    // and never update the UI afterwards. If we had a more dynamic UI, we might want to consider
    // Flows because they play a little nicer with Compose via collectAsState. I don't have a strong
    // preference, personally.
    private val _shipmentsData = MutableLiveData<Map<Driver, Destination>>(hashMapOf())
    val shipmentsData: LiveData<Map<Driver, Destination>> = _shipmentsData

    fun refreshShipments() {
        viewModelScope.launch {
            _shipmentsData.postValue(shipmentRepository.getShipments())
        }
    }
}