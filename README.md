<div align="center">  
  <img src="https://i.imgur.com/RSSAkFv.png" alt="Weather" style="width: 100px; height: 100px; object-fit: contain; margin-right: 10px;">  
 <h1 style="display: inline-block; margin: 0; vertical-align: middle;">Weather</h1>  
</div>  

<p align="center">
  <a href="https://kotlinlang.org/">
    <img src="https://img.shields.io/badge/Kotlin-1.8.21-blue?logo=kotlin" alt="Kotlin version 1.8.21">
  </a>
  <a href="https://gradle.org/releases/">
    <img src="https://img.shields.io/badge/Gradle-8.0.2-white?logo=gradle" alt="Gradle version 8.0.2">
  </a>
  <a href="https://developer.android.com/studio">
    <img src="https://img.shields.io/badge/Android%20Studio-Flamingo-orange?logo=android-studio" alt="Android Studio Flamingo">
  </a>
  <a href="https://developer.android.com/studio/releases/platforms#7.0">
    <img src="https://img.shields.io/badge/Android%20min%20version-7-brightgreen?logo=android" alt="Android min version 7">
</a>
</p>

Users can view and add weather information for multiple cities of their choice with this app.  
It provides a 48-hour forecast, an 8-day forecast, and current weather conditions.  
The program can also detect the user’s location to provide accurate local weather information.

## <a href="https://imgur.com/jDOxJdo"><img src="https://i.imgur.com/jDOxJdo.png" title="source: imgur.com" /></a>Play Store

I am trying to add a weather app to the Google Play Store. I have been rejected several times because my app requires background location permissions. I have been trying to fix this for a week, but I am not sure how. If you are able to help, please email me at  Abouyassine056@gmail.com

<!-- Image -->
<div align="center">
  <a href="https://imgur.com/jeI1uPy">
    <img src="https://i.imgur.com/jeI1uPy.jpg" alt="Weather App Screenshot">
  </a>
</div>

## Screenshots

<div style="display:flex; flex-wrap:wrap;">  
  <img src="https://i.imgur.com/NTDBQRX.png" style="flex:1; margin:5px;" height="450">  
 <img src="https://i.imgur.com/JWAsVQ6.png" style="flex:1; margin:5px;" height="450">  
 <img src="https://i.imgur.com/Gw0vnrb.png" style="flex:1; margin:5px;" height="450">  
 <img src="https://i.imgur.com/Odc7Gic.png" style="flex:1; margin:5px;" height="450">  

</div>

## Features
<p>To use the app's auto location feature, permission is needed. Here's how based on Android versions:</p>

<ul>
  <li>Android 7-10: The app asks for location directly.</li>
  <li>Android 11+: A dialog appears. Click "Ok" to go to settings and grant location access.</li>
</ul>


## API Key :key:
The project has an API key included. To use a different API key, follow these steps:

1. Visit the [OpenWeatherMap website](https://openweathermap.org/api/one-call-api).
2. If you don't already have an account, register for one.
3. **Make sure you use One Call API 2.5. It is free to use for up to 1,000 API calls per day.**
4. Once you have an account, request an API key.
5. Open `WeatherApiService.kt`.
6. In the `WEATHER_API_KEY` property, paste your API key.



## Architecture
The architecture of this application relies and complies with the following points below:
* A single-activity architecture, using the [ViewPager2](https://developer.android.com/develop/ui/views/animations/screen-slide-2) to display a collection of fragments in a scrollable, swipeable interface.
* Pattern [Model-View-ViewModel](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)(MVVM) which facilitates a separation of development of the graphical user interface.
* [Android architecture components](https://developer.android.com/topic/libraries/architecture/) which help to keep the application robust, testable, and maintainable.

<p align="center"><a><img src="https://raw.githubusercontent.com/mayokunthefirst/Instant-Weather/master/media/final-architecture.png" width="700"></a></p>  

## Package Structure
```  
com.yassineabou.weather        # Root package  
├── data                   # For data modeling layer  
│   ├── local              # Local persistence database  
|   ├── model              # Model classes  
│   ├── remote             # Remote data source  
│   └── repository         # Repositories for single source of data  
|  
├── di                     # Dependency injection modules  
│  
├── ui                     # Activity / Fragment / ViewModel  
│   ├── addLocation        # Allows user to add new locations
│   ├── currentWeather     # Presents current weather details
|   ├── dailyForecast      # Showcases daily weather forecast
|   ├── hourlyForecast     # Illustrates hourly weather forecast  
|   ├── listLocations      # Lists saved locations
|   ├── PagerAdapter       # Adapter for viewPager2  
|   ├── MainActivity       # Single activity  
│   └── MainViewModel      # Shared viewModel  
|  
├── utils                  # Utility classes / Kotlin extensions  
└── WeatherApplication     # Application  
  
```  


## Built With 🧰

- [Baseline Profiles](https://developer.android.com/topic/performance/baselineprofiles/overview) (Performance optimization tool)
- [ViewPager2](https://developer.android.com/develop/ui/views/animations/screen-slide-2) (Swipeable pager)
- [Dots Indicator](https://github.com/tommybuonomo/dotsindicator) (Material view pager dots indicator)
- [Data Binding](https://developer.android.com/topic/libraries/data-binding) (Bind views)
- [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) (State observable)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) (Store and manage UI-related data)
- [Kotlin Coroutine](https://developer.android.com/kotlin/coroutines) (Light-weight threads)
- [Google Play services](https://developers.google.com/android/guides/setup) (Google service integration)
- [Moshi](https://github.com/square/moshi) (JSON serialization)
- [Hilt](https://dagger.dev/hilt/) (Dependency injection for android)
- [Retrofit2](https://github.com/square/retrofit) (HTTP client for APIs)
- [Room](https://developer.android.com/topic/libraries/architecture/room) (Abstraction layer over SQLite)
- [Sdp](https://github.com/intuit/sdp) (Scalable size unit)
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) (Key-value storage)
- [Ssp](https://github.com/intuit/ssp) (Scalable size unit for texts)
- [Kpermissions](https://github.com/fondesa/kpermissions) (Coroutines based runtime permissions)
- [NetworkX](https://github.com/rommansabbir/NetworkX) (Internet connection status)


## Contribution
We welcome contributions to our project! Please follow these guidelines when submitting changes:

- Report bugs and feature requests by creating an issue on our GitHub repository.
- Contribute code changes by forking the repository and creating a new branch.
- Ensure your code follows our coding conventions.
- Improve our documentation by submitting changes as a pull request.

Thank you for your interest in contributing to our project!

## License
```
Copyright 2023 Yassine Abou 
  
Licensed under the Apache License, Version 2.0 (the "License");  
you may not use this file except in compliance with the License.  
You may obtain a copy of the License at  
  
    http://www.apache.org/licenses/LICENSE-2.0  
  
Unless required by applicable law or agreed to in writing, software  
distributed under the License is distributed on an "AS IS" BASIS,  
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  
See the License for the specific language governing permissions and  
limitations under the License.

