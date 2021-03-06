/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {

    var timeLeftMillis by mutableStateOf(0L)
    var timeSet by mutableStateOf(0L)

    var isRunning by mutableStateOf(false)

    lateinit var timer: CountDownTimer

    fun startTimer() {

        if (timeLeftMillis > 1000) {
            timeSet = timeLeftMillis
            timer = object : CountDownTimer(timeLeftMillis, 1000) {

                override fun onTick(remainingTime: Long) {
                    timeLeftMillis = remainingTime
                }

                override fun onFinish() {
                    timeLeftMillis = 0
                    isRunning = false
                }
            }
                .start()
            isRunning = true
        }
    }

    fun resetTimer() {
        timer.cancel()
        timeLeftMillis = 0
        isRunning = false
    }

    fun incrMinute() {
        timeLeftMillis += 60000
    }

    fun decrMinute() {
        if (timeLeftMillis > 59000) {
            timeLeftMillis -= 60000
        }
    }

    fun incrSecond() {
        timeLeftMillis += 1000
    }

    fun decrSecond() {
        if (timeLeftMillis > 999) {
            timeLeftMillis -= 1000
        }
    }
}
