/*
 * Copyright 2018 Google LLC
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

package com.google.samples.apps.sunflower

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Consumer
import androidx.databinding.DataBindingUtil.setContentView
import androidx.window.DeviceState
import androidx.window.WindowLayoutInfo
import androidx.window.WindowManager
import com.google.samples.apps.sunflower.databinding.ActivityGardenBinding
import java.util.concurrent.Executor

class GardenActivity : AppCompatActivity() {

    private lateinit var layoutChangeCallback: Consumer<WindowLayoutInfo>
    private val handler = Handler(Looper.getMainLooper())
    private val mainThreadExecutor = Executor { r: Runnable -> handler.post(r) }
    private lateinit var windowManager: WindowManager
    private lateinit var binding: ActivityGardenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGardenBinding.inflate(layoutInflater)
        setContentView<ActivityGardenBinding>(this, R.layout.activity_garden)

        windowManager = WindowManager(this, null)
        windowManager.registerDeviceStateChangeCallback(
                mainThreadExecutor,
                Consumer { newDeviceState ->
                    Toast.makeText(this, newDeviceState.posture.toString(), Toast.LENGTH_LONG).show()
                    when (newDeviceState.posture) {
                        DeviceState.POSTURE_HALF_OPENED -> toogleDesktopMode()
                        else -> toggleNormalMode()
                    }
                }
        )
    }

    private fun toggleNormalMode() {

    }

    private fun toogleDesktopMode() {
        //binding.splitLayout.updateWindowLayout(windowManager.windowLayoutInfo)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        windowManager.registerLayoutChangeCallback(
                mainThreadExecutor,
                Consumer<WindowLayoutInfo> { layoutInfo ->
                    Toast.makeText(this, layoutInfo.displayFeatures.toString(), Toast.LENGTH_LONG).show()
                }.also {
                    this.layoutChangeCallback = it
                }
        )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        windowManager.unregisterLayoutChangeCallback(layoutChangeCallback)
    }

}