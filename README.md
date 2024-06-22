# Top 100 Albums

This project is an Android application that displays the top 100 albums. The app fetches data from an API and saves it in a local database using Realm, allowing for an offline-first experience. Users can retry fetching data if the initial network request fails.

## Demo
Please refer to the demo by clicking [Here](https://drive.google.com/file/d/1H1WJJZaB1alht9HeZR600vF8ABovFrOl/view?usp=sharing)

## Features

- Display a list of the top 100 albums.
- Fetch albums from a local Realm database first, then append with results from the API.
- Retry fetching data if the initial network request fails.
- Offline-first approach ensures that data is available even without an internet connection.

## Screens

1. **Albums Screen**: Displays a list of the top 100 albums.
   - Shows a loading indicator while fetching data.
   - Displays an error message with a retry button if the initial network request fails.
   - Fetches data from the local Realm database if available.

2. **Album Details Screen**: Displays detailed information about a selected album.
   - Shows the album's artwork, title, and other relevant information.
   - Provides a button to visit the album's URL.

## Dependencies

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Hilt for Dependency Injection](https://developer.android.com/training/dependency-injection/hilt-android)
- [Realm for Android](https://realm.io/products/realm-database/)
- [Retrofit for networking](https://square.github.io/retrofit/)

## Getting Started

### Prerequisites

- Android Studio Bumblebee or later
- Gradle 7.0 or later

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/top-100-albums.git
   cd top-100-albums
2. Open the project in Android Studio.

3. Sync the project with Gradle files.

4. Build and run the project on an emulator or a physical device.


### Project Structure
- data: Contains data models, repositories, and API definitions.
- ui: Contains Composables for the Albums and Album Details screens.
- viewmodel: Contains ViewModel classes to manage UI-related data.

#### API
The app fetches data from the following API:
https://rss.applemarketingtools.com/api/v2/us/music/most-played/100/albums.json

#### Local Database
The app uses Realm as the local database to store albums data for offline access.

#### Usage
- Launch the app to view the list of top 100 albums.
- Tap on an album to view its details.
- If the data fetch fails initially, tap the retry button in the app bar to try fetching the data again.

# License
This project is licensed under the GPL License - see the LICENSE file for details.

# Acknowledgements
Thanks to the creators of Jetpack Compose, Hilt, Retrofit, and Realm for their excellent libraries.
