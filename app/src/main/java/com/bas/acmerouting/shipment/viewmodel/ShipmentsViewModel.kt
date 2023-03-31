package com.bas.acmerouting.shipment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bas.acmerouting.shipment.data.Driver
import com.bas.acmerouting.shipment.data.LocalShipmentRepository
import com.bas.acmerouting.shipment.data.Route
import com.bas.acmerouting.shipment.data.ShipmentRepository
import kotlinx.coroutines.launch

class ShipmentsViewModel(app: Application): AndroidViewModel(app) {

    //TODO: Inject
    val shipmentRepository: ShipmentRepository = LocalShipmentRepository(app.applicationContext)

    //TODO: Refactor to use Flows
    private val _shipmentsData = MutableLiveData<Map<Driver, Route>>(hashMapOf())
    val shipmentsData: LiveData<Map<Driver, Route>> = _shipmentsData

    fun refreshShipments() {
        viewModelScope.launch {
            _shipmentsData.postValue(shipmentRepository.getShipments())
        }
    }
}