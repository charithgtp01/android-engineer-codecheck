# Yumemi Co., Ltd. Android Engineer Code Check Assignment

## Summary

The app utilizes Kotlin, follows a single activity architecture with MVVM using data binding, and incorporates a Room database for handling favorites. It provides functionality to search GitHub repositories by name, view details, and manage favorites.

## Features
1) ### GitHub Repository Search:
- Search GitHub repositories by name using the free GitHub API.
- Display search results in a RecyclerView on the home page.
- Clicking on a repository opens a details page.

2) ### Favorites:

- Save repositories as favorites in the Room database.
- View a list of saved favorites in the Favorites fragment.
- Expand items to view details and delete them.

3) ### Settings
- Change the language from the Settings page.

4) ### UI Enhancements:

- Splash image, app launcher icon, and initial images for improved aesthetics.
- Custom action bar and bottom menu for better navigation.
5) ### Localization:

- App supports both japanese and english languages.

6) ### Code Quality:

- Follows MVVM architecture, data binding, and Room database best practices.
- Dependency injection using Dagger Hilt.

  ## Screenshots
<img src="https://yumimi-code-test.s3.amazonaws.com/Home.jpeg" alt="Image Alt Text" width="200">   <img src="https://yumimi-code-test.s3.amazonaws.com/progress.jpeg" alt="Image Alt Text" width="200">   <img src="https://yumimi-code-test.s3.amazonaws.com/git_repo_list.jpeg" alt="Image Alt Text" width="200"> <img src="https://yumimi-code-test.s3.amazonaws.com/details.jpeg" alt="Image Alt Text" width="200">

<img src="https://yumimi-code-test.s3.amazonaws.com/collapsed_fav.jpeg" alt="Image Alt Text" width="200">   <img src="https://yumimi-code-test.s3.amazonaws.com/expanded_fav.jpeg" alt="Image Alt Text" width="200">   <img src="https://yumimi-code-test.s3.amazonaws.com/settings.jpeg" alt="Image Alt Text" width="200">
## Installation
1. Clone the repository: `git clone https://github.com/charithgtp01/android-engineer-codecheck.git`
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.

## Usage
1. Open the app on your device.
2. Use the search functionality to find GitHub repositories.
3. Click on a repository to view its details.
4. Save repositories as favorites from the details page.
5. Manage favorites from the Favorites fragment.
6. Change app language from the Settings page.

## Main Libraries and Dependencies
- Room Database: Version 2.6.0
- Retrofit: Version 2.9.0
- Dagger Hilt: Version 2.48.1
- Coil: Version 2.5.0
- Kotlin Coroutines: Version 1.7.3
- Navigation Component: Version 2.7.5
- Espresso: Version 3.5.1

## Environment

- IDE：Android Studio Giraffe | 2022.3.1 Patch 2
- Kotlin：1.6.21
- Java：11
- Gradle：8.1.3
- minSdk：23
- targetSdk：34

