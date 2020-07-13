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

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.databinding.DataBindingUtil.setContentView
import androidx.window.WindowManager
import com.google.samples.apps.sunflower.databinding.ActivityGardenPortraitBinding
import com.google.samples.apps.sunflower.views.SplitLayout

class GardenPortraitActivity : AppCompatActivity() {

    private lateinit var windowManager: WindowManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityGardenPortraitBinding>(this, R.layout.activity_garden_portrait)

        windowManager = WindowManager(this, null)
    }

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        parent?.doOnLayout {
            findViewById<SplitLayout>(R.id.split_layout).updateWindowLayout(windowManager.windowLayoutInfo)
        }

        return super.onCreateView(parent, name, context, attrs)
    }
}