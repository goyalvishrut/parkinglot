package models

enum class InputAction(val value: String) {
    CREATE_PARKING_LOT("create_parking_lot"),
    PARK_VEHICLE("park_vehicle"),
    UNPARK_VEHICLE("unpark_vehicle"),
    DISPLAY("display"),
    EXIT("exit")
}
