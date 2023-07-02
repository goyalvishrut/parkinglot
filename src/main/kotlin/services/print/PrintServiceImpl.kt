package services.print

import models.Vehicle
import models.VehicleTypes

class PrintServiceImpl : PrintService {

    override fun createParkingLot(parkingLotId: String, noOfFloor: Int, slotsPerFloor: Int) {
        println("Created parking lot with $noOfFloor floors and $slotsPerFloor slots per floor")
    }

    override fun vehicleParked(ticketId: String?) {
        val details = ticketId ?: "No Space available for parking"
        println(details)
    }

    override fun unparkVehicle(vehicle: Vehicle?) {
        val details =
            vehicle?.let { "Unparked vehicle with Registration Number: ${it.registrationNumber} and Color: ${it.color}" }
                ?: "Invalid Ticket"
        println(details)
    }

    override fun displayFreeCount(vehicleTypes: VehicleTypes, floorData: MutableMap<Int, Int>) {
        floorData.forEach { (floorNumber, freeCount) ->
            println("No. of free slots for ${vehicleTypes.name} on Floor $floorNumber: $freeCount")
        }
    }

    override fun displayFreeSlotCount(vehicleTypes: VehicleTypes, slotsData: MutableMap<Int, HashSet<Int>>) {
        slotsData.forEach { (floorNumber, freeSlots) ->
            println("Free slots for $vehicleTypes on Floor $floorNumber: ${freeSlots.joinToString(",")}")
        }
    }

    override fun displayOccupiedCount(vehicleTypes: VehicleTypes, occupiedSlots: MutableMap<Int, HashSet<Int>>) {
        occupiedSlots.forEach { (floorNumber, freeSlots) ->
            println("Occupied slots for $vehicleTypes on Floor $floorNumber: ${freeSlots.joinToString(",")}")
        }
    }

    override fun invalidInput(reason: String?) {
        val message = reason ?: "Invalid Input"
        println(message)
    }
}
