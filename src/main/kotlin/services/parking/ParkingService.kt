package services.parking

import models.Floor
import models.Vehicle
import models.VehicleTypes
import java.util.HashSet

interface ParkingService {

    fun createParkingLot(parkingLotId: String, noOfFloor: Int, slotsPerFloor: Int): Boolean

    fun parkVehicle(vehicle: Vehicle): String?

    fun unparkVehicle(ticketId: String): Vehicle?

    fun getParkingLot(): MutableMap<String, MutableMap<Int, Floor>>?

    fun getFreeCount(vehicleTypes: VehicleTypes): MutableMap<Int, Int>

    fun getFreeSlots(vehicleTypes: VehicleTypes): MutableMap<Int, HashSet<Int>>

    fun getOccupiedSlots(vehicleTypes: VehicleTypes): MutableMap<Int, HashSet<Int>>
}
