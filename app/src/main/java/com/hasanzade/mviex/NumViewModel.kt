package com.hasanzade.mviex

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class NumViewModel : ViewModel() {
    private  val _state = MutableStateFlow(NumState())
    val state : StateFlow<NumState> = _state

    fun onIntent(intent: IntentCalc){
        when(intent){
            is IntentCalc.Num1Val -> {
                _state.update { it.copy(num1 = intent.value) }
            }
            is IntentCalc.Num2Val -> {
                _state.update { it.copy(num2  = intent.value) }
            }

            IntentCalc.CalculateClicked -> {
                calculate()
            }
        }
    }

    private fun calculate() {
        val currentState = _state.value
        val v1 = currentState.num1.toIntOrNull()
        val v2 = currentState.num2.toIntOrNull()

        if(v1 == null || v2 == null){
            _state.update { it.copy(error = "Invalid") }
        }
        else{
            _state.update {
                it.copy(result = v1 + v2,
                    error = null)
            }
        }
    }

}