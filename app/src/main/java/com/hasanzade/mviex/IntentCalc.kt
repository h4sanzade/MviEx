package com.hasanzade.mviex

sealed class IntentCalc {
    data class Num1Val(val value : String ) : IntentCalc()
    data class Num2Val(val value : String) : IntentCalc()
    object CalculateClicked : IntentCalc()
}