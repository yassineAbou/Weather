<div align="center">
  <img src="https://drive.google.com/uc?export=view&id=1aph4gBtdXBnbbnsqTRx4zQENM3kgndc4" alt="Weather" style="width: 100px; height: 100px; object-fit: contain; margin-right: 10px;">
  <h1 style="display: inline-block; margin: 0; vertical-align: middle;">Weather</h1>
</div>

<p align="center">
  <a href="https://kotlinlang.org/"><img src="https://img.shields.io/badge/Kotlin-v1.8.0-blue.svg" alt="Kotlin"></a>
<a href="#"><img src="https://img.shields.io/badge/Min%20Version-Android%208-green" alt="Min Version: Android 8"></a>
 <a href="https://gradle.org/releases/">
  <img src="https://img.shields.io/badge/Gradle-8-red.svg"
       alt="Gradle Version 8"
       style="border-radius: 3px; padding: 2px 6px; background-color: #FF0000; color: #fff;">
</a>
</p>

Users can view and add weather information for multiple cities of their choice with this app.
It provides a 48-hour forecast, an 8-day forecast, and current weather conditions.
The program can also detect the user’s location to provide accurate local weather information.

<p align="center">
  <a href="https://github.com/yassineAbou"><img alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" height="80px"/></a>
  <a href="https://github.com/yassineAbou"><img alt="Get it on F-Droid" src="https://f-droid.org/badge/get-it-on.png" height="80px"/></a>
</p>

## Screenshots

<div style="display:flex; flex-wrap:wrap;">
  <img src="https://drive.google.com/uc?export=view&id=1WxGvaILZxyc81yZCcz9g6nRvPIhxTxW5" style="flex:1; margin:5px;" height="450">
  <img src="https://drive.google.com/uc?export=view&id=1jlPvT3d-A-hztQelKdOhV0blilVkMeRv" style="flex:1; margin:5px;" height="450">
  <img src="https://drive.google.com/uc?export=view&id=1FyPKaNQYeY3jDh8cw8jOQj3f78xQD4sG" style="flex:1; margin:5px;" height="450">
  <img src="https://drive.google.com/uc?export=view&id=18SXB6Jf9cKacNPTMRhz_D9cW3gjw0Mdr" style="flex:1; margin:5px;" height="450">

</div>


## API Key :key:
To request data from [OpenWeatherMap](https://openweathermap.org), you will need to obtain an API key. If you don't already have an account, you will need to create one in order to request an API Key.

Once you have obtained an API key, you will need to add it to your project's local.properties file. The local.properties file is located in the root directory of your project. If the file does not exist, you will need to create it.

To add your API key to the local.properties file, open the file and add the following line:
```
WEATHER_API_KEY = put your api key here
```
Once you have added your API key to the local.properties file, you can start requesting data from [OpenWeatherMap](https://openweathermap.org)


## Architecture
The architecture of this application relies and complies with the following points below:
* A single-activity architecture, using the [ViewPager2](https://developer.android.com/develop/ui/views/animations/screen-slide-2) to display a collection of fragments in a scrollable, swipeable interface.
* Pattern [Model-View-ViewModel](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)(MVVM) which facilitates a separation of development of the graphical user interface.
* [Android architecture components](https://developer.android.com/topic/libraries/architecture/) which help to keep the application robust, testable, and maintainable.

<p align="center"><a><img src="https://raw.githubusercontent.com/mayokunthefirst/Instant-Weather/master/media/final-architecture.png" width="700"></a></p>

## Package Structure
```
com.example.weather        # Root package
├── data                   # For data modeling layer
│   ├── local              # Local persistence database
|   ├── model              # Model classes
│   ├── remote             # Remote data source
│   └── repository         # Repositories for single source of data
|
├── di                     # Dependency injection modules
│
├── ui                     # Activity / Fragment / View layer
│   ├── addLocation        # Add location dialog fragment
│   ├── currentWeather     # Current weather fragment and viewModel
|   ├── dailyForecast      # Daily forecast fragment, adapter and viewModel
|   ├── hourlyForecast     # Hourly forecast fragment, adapter and viewModel
|   ├── listLocations      # List locations fragment and adapter
|   ├── PagerAdapter       # Adapter for viewPager2
|   ├── MainActivity       # Single activity
│   └── MainViewModel      # Shared viewModel
|
├── utils                  # Utility classes / Kotlin extensions
└── WeatherApplication     # Application

```


## Built With 🧰

- [ViewPager2](https://developer.android.com/develop/ui/views/animations/screen-slide-2) (Swipeable pager)
-  [Dots Indicator](https://github.com/tommybuonomo/dotsindicator) (Material view pager dots indicator)
- [Data Binding](https://developer.android.com/topic/libraries/data-binding) (Bind views)
-  [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) (State observable)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) (Store and manage UI-related data)
- [Kotlin Coroutine](https://developer.android.com/kotlin/coroutines) (Light-weight threads)
- [Google Play services](https://developers.google.com/android/guides/setup) (Google service integration)
- [Moshi](https://github.com/square/moshi) (JSON serialization)
- [Hilt](https://dagger.dev/hilt/) (Dependency injection for android)
-  [Retrofit2](https://github.com/square/retrofit) (HTTP client for APIs)
- [Room](https://developer.android.com/topic/libraries/architecture/room) (Abstraction layer over SQLite)
-  [Coil](https://github.com/coil-kt/coil) (Image loading)
- [Secrets Gradle](https://developers.google.com/maps/documentation/android-sdk/secrets-gradle-plugin) (Secure secret storage)
- [Sdp](https://github.com/intuit/sdp) (Scalable size unit)
-  [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) (Key-value storage)
- [Ssp](https://github.com/intuit/ssp) (Scalable size unit for texts)
-  [Kpermissions](https://github.com/fondesa/kpermissions) (Coroutines based runtime permissions)
- [NetworkX](https://github.com/rommansabbir/NetworkX) (Internet connection status)


## Contribution
We welcome contributions to our project! Please follow these guidelines when submitting changes:

-   Report bugs and feature requests by creating an issue on our GitHub repository.
-   Contribute code changes by forking the repository and creating a new branch.
-   Ensure your code follows our coding conventions.
-   Improve our documentation by submitting changes as a pull request.

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