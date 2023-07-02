package repo

import models.Floor
import models.TicketDetails
import models.Vehicle

interface ParkingRepo {

    fun createParkingLot(parkingLotId: String, noOfFloor: Int, slotsPerFloor: Int): Boolean

    fun parkVehicle(vehicle: Vehicle, ticketDetails: TicketDetails): String

    fun unParkVehicle(ticketId: String): Vehicle?

    fun getParkingMap(): MutableMap<String, MutableMap<Int, Floor>>?
}
