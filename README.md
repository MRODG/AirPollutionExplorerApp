# AirPollutionExplorerApp
App that allows you to explore the the air pollution conditions in different cities my exploring a Map activity.
Selecting a region on the map will open a page with details of the city, weather and its air pollution. Used AirVisual Api as data source.

- Supported Android API versions: 23 and above
- Programming language: Kotlin
- Supported device types: Phone only
- Supported orientation: Portrait only

## Architecture
MVVM pattern (Model-View-View-Model).
For asynchronous service calls I used kotlin coroutines and flow to be able to easily manage and update UI with the progress of the service calls.
Used LiveData for observing the ViewModels on the view side.

## Dependency Injection
We use dependency injection to reduce coupling and simplify testing.
For dependency injection used Dagger-Hilt.

## Project Structure
I structure code with separation of concerns in mind to make code more modular, maintainable and scalable




