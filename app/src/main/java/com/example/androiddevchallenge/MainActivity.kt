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

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlin.math.max

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp(TimerViewModel())
            }
        }
    }
}

@Composable
fun MyApp(timerViewModel: TimerViewModel) {
    val timeLeft = timerViewModel.timeLeftMillis
    val isRunning = timerViewModel.isRunning
    val timeSet = timerViewModel.timeSet

    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Row() {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    IconButton(
                        onClick = {
                            if (!isRunning) {
                                timerViewModel.incrMinute()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowUp,
                            "Increment minutes",
                            tint = MaterialTheme.colors.secondary
                        )
                    }

                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp,
                        modifier = Modifier.height(138.dp),
                        backgroundColor = MaterialTheme.colors.primaryVariant
                    ) {
                        Text(
                            text = formatMinutes(timeLeft),
                            style = MaterialTheme.typography.h1,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(8.dp),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.primary
                        )
                    }

                    IconButton(
                        onClick = {
                            if (!isRunning) {
                                timerViewModel.decrMinute()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            "Decrement minutes",
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        onClick = {
                            if (!isRunning) {
                                timerViewModel.incrSecond()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowUp,
                            "Increment seconds",
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp,
                        modifier = Modifier.height(138.dp),
                        backgroundColor = MaterialTheme.colors.primaryVariant
                    ) {
                        Text(
                            text = formatSeconds(timeLeft),
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Light,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.primary
                        )
                    }
                    IconButton(
                        onClick = {
                            if (!isRunning) {
                                timerViewModel.decrSecond()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            "Decrement seconds",
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                }
            }

            Box(
                modifier = Modifier.aspectRatio(1f),
                contentAlignment = Alignment.Center,
            ) {
                val targetValue = when {
                    !isRunning -> 1f
                    else -> max(timeLeft / 1000.toFloat() - 1, 0f) / (timeSet / 1000)
                }

                val progress by animateFloatAsState(
                    targetValue = targetValue,
                    animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
                )

                CircularProgressIndicator(
                    color = MaterialTheme.colors.secondary,
                    progress = progress,
                    strokeWidth = 4.dp,
                    modifier = Modifier
                        .size(80.dp, 80.dp)
                        .padding(8.dp)
                )

                IconButton(
                    onClick = {
                        if (isRunning) {
                            timerViewModel.resetTimer()
                        } else {
                            timerViewModel.startTimer()
                        }
                    }
                ) {
                    if (isRunning) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            "Reset timer",
                            tint = MaterialTheme.colors.secondary
                        )
                    } else
                        Icon(
                            imageVector = Icons.Rounded.PlayArrow,
                            "Start timer",
                            tint = MaterialTheme.colors.secondary
                        )
                }
            }
        }
    }
}

fun formatMinutes(timeMillis: Long): String {
    val seconds = timeMillis / 1000
    return (seconds / 60).toString().padStart(2, '0')
}

fun formatSeconds(timeMillis: Long): String {
    val seconds = timeMillis / 1000
    return (seconds % 60).toString().padStart(2, '0')
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp(TimerViewModel())
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp(TimerViewModel())
    }
}
