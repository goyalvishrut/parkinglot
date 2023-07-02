package util

import models.InputAction
import models.VehicleTypes

fun String.toInputAction(): InputAction =
    InputAction.values().firstOrNull { it.value.equals(this, ignoreCase = true) } ?: InputAction.EXIT

fun String.toVehicleType(): VehicleTypes = VehicleTypes.values().first { it.name.equals(this, ignoreCase = true) }
