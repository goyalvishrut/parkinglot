package services.print

import models.Vehicle
import models.VehicleTypes

interface PrintService {

    fun createParkingLot(parkingLotId: String, noOfFloor: Int, slotsPerFloor: Int)

    fun vehicleParked(ticketId: String?)

    fun unparkVehicle(vehicle: Vehicle?)

    fun displayFreeCount(vehicleTypes: VehicleTypes, floorData: MutableMap<Int, Int>)

    fun displayFreeSlotCount(vehicleTypes: VehicleTypes, slotsData: MutableMap<Int, HashSet<Int>>)

    fun displayOccupiedCount(vehicleTypes: VehicleTypes, occupiedSlots: MutableMap<Int, HashSet<Int>>)

    fun invalidInput(reason: String?)
}
