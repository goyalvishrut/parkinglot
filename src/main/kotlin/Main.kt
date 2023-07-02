import controller.ParkingController
import models.InputAction
import models.Vehicle
import repo.ParkingRepoImpl
import services.parking.ParkingServiceImpl
import services.print.PrintServiceImpl
import util.toInputAction
import util.toVehicleType
import kotlin.system.exitProcess

private val parkingRepo = ParkingRepoImpl()
private val parkingService = ParkingServiceImpl(parkingRepo)
private val printService = PrintServiceImpl()
private val parkingController = ParkingController(parkingService = parkingService, printService = printService)

val testInput = listOf(
    "create_parking_lot PR1234 2 6",
    "display free_count CAR ",
    "display free_count BIKE ",
    "display free_count TRUCK",
    "display free_slots CAR",
    "display free_slots BIKE",
    "display free_slots TRUCK",
    "display occupied_slots CAR",
    "display occupied_slots BIKE",
    "display occupied_slots TRUCK",
    "park_vehicle CAR KA-01-DB-1234 black",
    "park_vehicle CAR KA-02-CB-1334 red",
    "park_vehicle CAR KA-01-DB-1133 black",
    "park_vehicle CAR KA-05-HJ-8432 white",
    "park_vehicle CAR WB-45-HO-9032 white",
    "park_vehicle CAR KA-01-DF-8230 black",
    "park_vehicle CAR KA-21-HS-2347 red",
    "display free_count CAR",
    "display free_count BIKE",
    "display free_count TRUCK",
    "unpark_vehicle PR1234_2_5",
    "unpark_vehicle PR1234_2_5",
    "unpark_vehicle PR1234_2_7",
    "display free_count CAR",
    "display free_count BIKE",
    "display free_count TRUCK",
    "display free_slots CAR",
    "display free_slots BIKE",
    "display free_slots TRUCK",
    "display occupied_slots CAR",
    "display occupied_slots BIKE",
    "display occupied_slots TRUCK",
    "park_vehicle BIKE KA-01-DB-1541 black",
    "park_vehicle TRUCK KA-32-SJ-5389 orange",
    "park_vehicle TRUCK KL-54-DN-4582 green",
    "park_vehicle TRUCK KL-12-HF-4542 green",
    "display free_count CAR",
    "display free_count BIKE",
    "display free_count TRUCK",
    "display free_slots CAR",
    "display free_slots BIKE",
    "display free_slots TRUCK",
    "display occupied_slots CAR",
    "display occupied_slots BIKE",
    "display occupied_slots TRUCK",
    "exit"
)

fun main() {
//    testInput()
    while (true) {
        val input = readln()
        val list = input.split(" ")
        processInput(list)
    }
}

private fun testInput() {
    testInput.forEach {
        processInput(it.split(" "))
    }
}

fun processInput(list: List<String>) {
    val inputAction = list[0].toInputAction()
    val subList = list.subList(1, list.size)

    when (inputAction) {
        InputAction.CREATE_PARKING_LOT -> createParkingLot(subList)
        InputAction.PARK_VEHICLE -> parkVehicle(subList)
        InputAction.UNPARK_VEHICLE -> unparkVehicle(subList)
        InputAction.DISPLAY -> displayData(subList)
        InputAction.EXIT -> exitProcess(1)
    }
}

private fun displayData(subList: List<String>) {
    parkingController.displayData(
        displayType = subList[0],
        vehicleTypes = subList[1].toVehicleType()
    )
}

private fun unparkVehicle(subList: List<String>) {
    parkingController.unparkVehicle(
        ticketId = subList[0]
    )
}

private fun parkVehicle(subList: List<String>) {
    parkingController.parkVehicle(
        vehicle = Vehicle(
            vehicleType = subList[0].toVehicleType(),
            registrationNumber = subList[1],
            color = subList[2]
        )
    )
}

private fun createParkingLot(subList: List<String>) {
    parkingController.createParkingLot(
        parkingLotId = subList[0],
        noOfFloor = subList[1].toInt(),
        slotsPerFloor = subList[2].toInt()
    )
}
