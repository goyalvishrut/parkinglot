package repo

import models.Floor
import models.TicketDetails
import models.Vehicle

class ParkingRepoImpl : ParkingRepo {

    private var parkingLot: MutableMap<String, MutableMap<Int, Floor>>? = mutableMapOf()
    private var totalFloors: Int = 0
    private var totalSlots: Int = 0
    private var parkedVehicles: MutableMap<String, Vehicle>? = null

    override fun createParkingLot(parkingLotId: String, noOfFloor: Int, slotsPerFloor: Int): Boolean {
        val floors: MutableMap<Int, Floor> = mutableMapOf()
        for (floor in 1..noOfFloor) {
            floors[floor] = Floor(slots = MutableList(slotsPerFloor) { null })
        }
        parkingLot = mutableMapOf()
        parkingLot!![parkingLotId] = floors
        totalFloors = noOfFloor
        totalSlots = slotsPerFloor
        parkedVehicles = mutableMapOf()
        return true
    }

    override fun parkVehicle(vehicle: Vehicle, ticketDetails: TicketDetails): String {
        if (parkingLot == null) {
            return "Parking lot not initialized"
        }
        parkingLot!![ticketDetails.parkingLotId]!![ticketDetails.floor]?.slots?.set(ticketDetails.slot, vehicle)
        val ticketId = getTicketId(ticketDetails)
        parkedVehicles!![ticketId] = vehicle
        return ticketId
    }

    override fun unParkVehicle(ticketId: String): Vehicle? {
        return if (parkedVehicles?.contains(ticketId) == true) {
            val vehicle = parkedVehicles!!.remove(ticketId)
            val ticketDetails = getTicketDetails(ticketId)
            parkingLot!![ticketDetails.parkingLotId]!![ticketDetails.floor]?.slots?.set(ticketDetails.slot, null)
            vehicle
        } else {
            null
        }
    }

    override fun getParkingMap() = parkingLot

    private fun getTicketId(ticketDetails: TicketDetails): String {
        return "${ticketDetails.parkingLotId}_${ticketDetails.floor}_${ticketDetails.slot + 1}"
    }

    private fun getTicketDetails(ticketId: String): TicketDetails {
        val (id, floor, slot) = ticketId.split("_")
        return TicketDetails(
            parkingLotId = id,
            floor = floor.toInt(),
            slot = slot.toInt()
        )
    }
}
