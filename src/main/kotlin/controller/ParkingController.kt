package controller

import models.Vehicle
import models.VehicleTypes
import services.parking.ParkingService
import services.print.PrintService

class ParkingController(
    private val parkingService: ParkingService,
    private val printService: PrintService
) {

    fun createParkingLot(parkingLotId: String, noOfFloor: Int, slotsPerFloor: Int) {
        parkingService.createParkingLot(
            parkingLotId = parkingLotId,
            noOfFloor = noOfFloor,
            slotsPerFloor = slotsPerFloor
        )
        printService.createParkingLot(parkingLotId, noOfFloor, slotsPerFloor)
    }

    fun parkVehicle(vehicle: Vehicle) {
        printService.vehicleParked(parkingService.parkVehicle(vehicle))
    }

    fun unparkVehicle(ticketId: String) {
        printService.unparkVehicle(parkingService.unparkVehicle(ticketId = ticketId))
    }

    fun displayData(displayType: String, vehicleTypes: VehicleTypes) {
        when (displayType) {
            "free_count" -> printService.displayFreeCount(
                vehicleTypes = vehicleTypes,
                floorData = parkingService.getFreeCount(vehicleTypes)
            )

            "free_slots" -> printService.displayFreeSlotCount(
                vehicleTypes = vehicleTypes,
                slotsData = parkingService.getFreeSlots(vehicleTypes)
            )

            "occupied_slots" -> printService.displayOccupiedCount(
                vehicleTypes = vehicleTypes,
                occupiedSlots = parkingService.getOccupiedSlots(vehicleTypes)
            )

            else -> printService.invalidInput(null)
        }
    }
}
