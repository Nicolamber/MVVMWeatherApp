 # MVVM -> Model View ViewModel
 MVVM is one of the architectural patterns which enhances separation of concerns, it allows separating the user interface logic from the business (or the back-end) logic. Its target (with other MVC patterns goal) is to achieve the following principle “Keeping UI code simple and free of app logic in order to make it easier to manage”.
 
 1. Model: represents the data and business logic of the app. One of the recommended implementation strategies of this layer, is to expose its data through observables to be decoupled completely from ViewModel or any other observer/consumer
 2. ViewModel: interacts with model and also prepares observable(s) that can be observed by a View. ViewModel can optionally provide hooks for the view to pass events to the model.
 3. View: the view role in this pattern is to observe (or subscribe to) a ViewModel observable to get data in order to update UI elements accordingly.
 
![alt text](https://i.stack.imgur.com/CnBPm.png)


# LiveData
is an observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services. This awareness ensures LiveData only updates app component observers that are in an active lifecycle state

Links: 
 1. https://proandroiddev.com/mvvm-architecture-viewmodel-and-livedata-part-1-604f50cda1
 2. https://medium.com/@elye.project/understanding-live-data-made-simple-a820fcd7b4d0
 3. https://developer.android.com/topic/libraries/architecture/viewmodel
 4. https://developer.android.com/topic/libraries/architecture/livedata

# MVVMWeatherApp

This application was made following this course: 
 - https://www.youtube.com/watch?v=yDaaM3u389I&list=PLB6lc7nQ1n4jTLDyU2muTBo8xk0dg0D_w

Course repository: https://github.com/ResoCoder/forecast-mvvm-android-kotlin
 
Course agenda: 
  - Kotlin
  - Material Design
  - MVVM Pattern
  - ViewModel
  - LiveData
  - Repository pattern
  - Dao Pattern
  - Room databases
  - Shared preferences
  - Dependency injector with Kodein

Screenshots:
 
 ![alt text](https://github.com/Nicolamber/MVVMWeatherApp/blob/master/Screenshots/coldSS.png) 
 ![alt text](https://github.com/Nicolamber/MVVMWeatherApp/blob/master/Screenshots/niceSS.png)
 ![alt text](https://github.com/Nicolamber/MVVMWeatherApp/blob/master/Screenshots/hotSS.png)
 ![alt text](https://github.com/Nicolamber/MVVMWeatherApp/blob/master/Screenshots/farenheitSS.png)
 ![alt text](https://github.com/Nicolamber/MVVMWeatherApp/blob/master/Screenshots/settingsSS.png)
 ![alt text](https://github.com/Nicolamber/MVVMWeatherApp/blob/master/Screenshots/placeSS.png)
 ![alt text](https://github.com/Nicolamber/MVVMWeatherApp/blob/master/Screenshots/unitSystemSS.png)
 
