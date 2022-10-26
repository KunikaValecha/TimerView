<h1 align="center">TimerView</h1></br>
<p align="center">
:fire: A beautifully designed fully customisable Android view that allows developers to create the UI for countdown timers. <br>
</p>
<p align="start">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://jitpack.io/#KunikaValecha/CreditCardView"><img alt="API" src="https://jitpack.io/v/KunikaValecha/TimerView.svg?style=flat"/></a>
</p>

<p align="center">
<img src="https://github.com/KunikaValecha/TimerView/blob/master/assets/timer-view.jpg" width="273"/>
<img src="https://github.com/KunikaValecha/TimerView/blob/master/assets/timer-view.gif" width="280"/>
</p>

#### Index
+ [Getting started](#getting-started)
+ [Basic usage](#basic-usage)
+ [Customisation](#customisation)
+ [Functions available](#functions-available)
+ [License](#license)

## Getting started
In your project's build.gradle

```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```
</br>
In your modules's build.gradle
</br>
<br>
<a href="https://jitpack.io/#KunikaValecha/TimerView"><img alt="API" src="https://jitpack.io/v/KunikaValecha/TimerView.svg?style=flat"/></a>
</br>

```gradle
implementation "com.github.KunikaValecha:TimerView:$latest_version"
```

## Basic usage

```xml
<com.github.credit_card_view.CreditCardView
        android:id="@+id/timerView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="7dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:timer_backgroundColor="@color/green_33009F6B"
        app:timer_backgroundOutlineColor="@color/green_99009F6B"
        app:timer_backgroundOutlineRadius="10dp"
        app:timer_backgroundOutlineWidth="4dp"
        app:timer_cellHeight="60dp"
        app:timer_cellWidth="40dp"
        app:timer_shouldShowHeaders="true"
        app:timer_textViewColor="#354741"
        app:timer_textViewSize="20sp" />
```

## Customisation
| Attribute  | Type | Optional | Definition |
| ------------- | ------------- | ------------- | ------------- |
| timer_backgroundColor  | color hex  | No | color of each cell of timer view |
| timer_backgroundOutlineColor  | color hex  | No | color of outline stroke of each cell of timer view  |
| timer_backgroundOutlineRadius  | integer  | No  | radius of outline stroke of each cell of timer view |
| timer_backgroundOutlineWidth  | integer  | No  | width of outline stroke of each cell of timer view |
| timer_cellHeight  | integer  | No  | height of each cell of timer view |
| timer_cellWidth  | integer  | No  | width of each cell of timer view |
| timer_shouldShowHeaders  | boolean  | No  | whether to show headers viz. Hours, Minutes, Seconds below their respective countdown cells |
| timer_textViewColor  | color hex  | No  | color of text in each cell of timer view along with color of headers and seperators |
| timer_textViewSize  | integer  | No  | size of text in each cell of timer view along with size of headers and seperators |

## Functions available
```kotlin
fun startTimer(
        durationInMillis: Long,
        uiScope: CoroutineScope,
        onTimerFinished: () -> Unit,
        onTimerInterval: () -> Unit
    ) //Function to set countdown in milliseconds

fun pauseTimer(): String? //Function to pause timer

fun resumeTimer(): ExpiryDate? //Function to resume timer

fun updateTimerUIState(
        bgColor: Int,
        outlineColor: Int,
        strokeWidth: Int,
        cornerRadius: Int
    ) //Function to update states of timer
```

## Find this library useful? :heart:
Support it by joining __[stargazers](https://github.com/KunikaValecha/TimerView/stargazers)__ for this repository. :star:

# License
```xml
Copyright 2022 KunikeValecha

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
