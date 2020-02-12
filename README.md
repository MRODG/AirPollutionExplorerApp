# AirPollutionExplorerApp
App that allows you to explore the the air pollution conditions in different cities my exploring a Map activity. Selcting a region on the map will open a page with details of the city and its air pollution. Used AirVisual Api as data source.

- Supported Android API versions: 21 and above
- Programming language: Kotlin
- Supported device types: Phone only
- Supported orientation: Portrait only

## Architecture
MVVM pattern, as it is recommended by Google and has direct support in the platform.
For asynchronous service calls I developed my own simple callback interface to make api calls on the model side.
Used LiveData for observing the viewmodels on the view side.

## Dependency Injection
We use dependency injection to reduce coupling and simplify testing.
For dependency injection used Koin. A service locator framwerork that is much simpler and does not require code generation like dagger.

## Project Structure
I structure code by feature. This method makes the codebase modular.
I created a placeholder feature package to understand what I mean.

## Testing
Wrote a couple of tests Unit and Ui



