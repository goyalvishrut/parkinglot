package services.parking

import models.Floor
import models.TicketDetails
import models.Vehicle
import models.VehicleTypes
import repo.ParkingRepo
import java.util.HashSet

class ParkingServiceImpl(private val parkingRepo: ParkingRepo) : ParkingService {

    override fun createParkingLot(parkingLotId: String, noOfFloor: Int, slotsPerFloor: Int): Boolean {
        return parkingRepo.createParkingLot(parkingLotId, noOfFloor, slotsPerFloor)
    }

    override fun parkVehicle(vehicle: Vehicle): String? {
        val ticketDetails = getTicketDetails(vehicleTypes = vehicle.vehicleType)
        return if (ticketDetails != null) {
            parkingRepo.parkVehicle(vehicle, ticketDetails)
        } else {
            null
        }
    }

    override fun unparkVehicle(ticketId: String): Vehicle? {
        return parkingRepo.unParkVehicle(ticketId)
    }

    override fun getParkingLot(): MutableMap<String, MutableMap<Int, Floor>>? {
        return parkingRepo.getParkingMap()
    }

    override fun getFreeCount(vehicleTypes: VehicleTypes): MutableMap<Int, Int> {
        val freeCounts: MutableMap<Int, Int> = mutableMapOf()

        when (vehicleTypes) {
            VehicleTypes.TRUCK -> {
                parkingRepo.getParkingMap()?.forEach { (_, floors) ->
                    floors.forEach { (floorNo, floorDetails) ->
                        freeCounts[floorNo] = 0
                        if (floorDetails.slots[0] == null) {
                            freeCounts[floorNo] = (freeCounts[floorNo] ?: 0) + 1
                        }
                    }
                }
            }

            VehicleTypes.BIKE -> {
                parkingRepo.getParkingMap()?.forEach { (_, floors) ->
                    floors.forEach { (floorNo, floorDetails) ->
                        freeCounts[floorNo] = 0
                        if (floorDetails.slots[1] == null) {
                            freeCounts[floorNo] = freeCounts[floorNo]!! + 1
                        }
                        if (floorDetails.slots[1] == null) {
                            freeCounts[floorNo] = freeCounts[floorNo]!! + 1
                        }
                    }
                }
            }

            VehicleTypes.CAR -> {
                parkingRepo.getParkingMap()?.forEach { (_, floors) ->
                    floors.forEach { (floorNo, floorDetails) ->
                        freeCounts[floorNo] = 0
                        floorDetails.slots.forEachIndexed { index, vehicle ->
                            if (index > 2 && vehicle == null) {
                                freeCounts[floorNo] = freeCounts[floorNo]!! + 1
                            }
                        }
                    }
                }
            }
        }
        return freeCounts
    }

    override fun getFreeSlots(vehicleTypes: VehicleTypes): MutableMap<Int, HashSet<Int>> {
        val freeSlots: MutableMap<Int, HashSet<Int>> = mutableMapOf()

        when (vehicleTypes) {
            VehicleTypes.TRUCK -> {
                parkingRepo.getParkingMap()?.forEach { (_, floors) ->
                    floors.forEach { (floorNo, floorDetails) ->
                        freeSlots[floorNo] = hashSetOf()
                        if (floorDetails.slots[0] == null) {
                            freeSlots[floorNo]!!.add(1)
                        }
                    }
                }
            }

            VehicleTypes.BIKE -> {
                parkingRepo.getParkingMap()?.forEach { (_, floors) ->
                    floors.forEach { (floorNo, floorDetails) ->
                        freeSlots[floorNo] = hashSetOf()
                        if (floorDetails.slots[1] == null) {
                            freeSlots[floorNo]!!.add(2)
                        }
                        if (floorDetails.slots[1] == null) {
                            freeSlots[floorNo]!!.add(3)
                        }
                    }
                }
            }

            VehicleTypes.CAR -> {
                parkingRepo.getParkingMap()?.forEach { (_, floors) ->
                    floors.forEach { (floorNo, floorDetails) ->
                        freeSlots[floorNo] = hashSetOf()
                        floorDetails.slots.forEachIndexed { index, vehicle ->
                            if (index > 2 && vehicle == null) {
                                freeSlots[floorNo]!!.add(index + 1)
                            }
                        }
                    }
                }
            }
        }
        return freeSlots
    }

    override fun getOccupiedSlots(vehicleTypes: VehicleTypes): MutableMap<Int, HashSet<Int>> {
        val freeSlots: MutableMap<Int, HashSet<Int>> = mutableMapOf()

        when (vehicleTypes) {
            VehicleTypes.TRUCK -> {
                parkingRepo.getParkingMap()?.forEach { (_, floors) ->
                    floors.forEach { (floorNo, floorDetails) ->
                        freeSlots[floorNo] = hashSetOf()
                        if (floorDetails.slots[0] != null) {
                            freeSlots[floorNo]!!.add(1)
                        }
                    }
                }
            }

            VehicleTypes.BIKE -> {
                parkingRepo.getParkingMap()?.forEach { (_, floors) ->
                    floors.forEach { (floorNo, floorDetails) ->
                        freeSlots[floorNo] = hashSetOf()
                        if (floorDetails.slots[1] != null) {
                            freeSlots[floorNo]!!.add(2)
                        }
                        if (floorDetails.slots[1] != null) {
                            freeSlots[floorNo]!!.add(3)
                        }
                    }
                }
            }

            VehicleTypes.CAR -> {
                parkingRepo.getParkingMap()?.forEach { (_, floors) ->
                    floors.forEach { (floorNo, floorDetails) ->
                        freeSlots[floorNo] = hashSetOf()
                        floorDetails.slots.forEachIndexed { index, vehicle ->
                            if (index > 2 && vehicle != null) {
                                freeSlots[floorNo]!!.add(index + 1)
                            }
                        }
                    }
                }
            }
        }
        return freeSlots
    }

    private fun getTicketDetails(vehicleTypes: VehicleTypes): TicketDetails? {
        return when (vehicleTypes) {
            VehicleTypes.TRUCK -> isTruckParkingAvailable()
            VehicleTypes.BIKE -> isBikeParkingAvailable()
            VehicleTypes.CAR -> isCarParkingAvailable()
        }
    }

    private fun isTruckParkingAvailable(): TicketDetails? {
        parkingRepo.getParkingMap()?.forEach { (parkingLotId, floor) ->
            floor.forEach { (floorNumber, floorDetail) ->
                if (floorDetail.slots[0] == null) {
                    return TicketDetails(floor = floorNumber, slot = 0, parkingLotId = parkingLotId)
                }
            }
        }
        return null
    }

    private fun isBikeParkingAvailable(): TicketDetails? {
        parkingRepo.getParkingMap()?.forEach { (parkingLotId, floor) ->
            floor.forEach { (floorNumber, floorDetail) ->
                if (floorDetail.slots[1] == null) {
                    return TicketDetails(floor = floorNumber, slot = 1, parkingLotId = parkingLotId)
                }
                if (floorDetail.slots[2] == null) {
                    return TicketDetails(floor = floorNumber, slot = 2, parkingLotId = parkingLotId)
                }
            }
        }
        return null
    }

    private fun isCarParkingAvailable(): TicketDetails? {
        parkingRepo.getParkingMap()?.forEach { (parkingLotId, floor) ->
            floor.forEach { (floorNumber, floorDetail) ->
                floorDetail.slots.forEachIndexed { index, vehicle ->
                    if (index > 2 && vehicle == null) {
                        return TicketDetails(floor = floorNumber, slot = index, parkingLotId = parkingLotId)
                    }
                }
            }
        }
        return null
    }
}
