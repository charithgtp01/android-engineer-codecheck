# Yumemi Co., Ltd. Android Engineer Code Check Assignment

## Summary

The app utilizes Kotlin, follows a single activity architecture with MVVM using data binding, and incorporates a Room database for handling favorites. It provides functionality to search GitHub repositories by name, view details, and manage favorites. User can change app language to english or japanese

<img src="https://yumimi-code-test.s3.amazonaws.com/full_demo.gif" width="320">

## Features
1) ### GitHub Repository Search:
- Search GitHub repositories by name using the free GitHub API.
- Display search results in a RecyclerView on the home page.
- Clicking on a repository opens a details page.
- See more Git hub profile in a web view

2) ### Web Profile View:
- Explore additional GitHub profile details using the web link

3) ### Favorites:

- Save repositories as favorites in the Room database.
- View a list of saved favorites in the Favorites fragment.
- Expand items to view details and delete them.

4) ### Settings:
- Change the language from the Settings page.

5) ### UI Enhancements:

- Splash image, app launcher icon, and initial images for improved aesthetics.
- Custom action bar and bottom menu for better navigation.
6) ### Localization:

- App supports both japanese and english languages.

7) ### Code Quality:

- Follows MVVM architecture, data binding, and Room database best practices.
- Dependency injection using Dagger Hilt.

## Screenshots
<img src="https://yumimi-code-test.s3.amazonaws.com/Home.jpeg" alt="Image Alt Text" width="200">  <img src="https://yumimi-code-test.s3.amazonaws.com/settings.jpeg" alt="Image Alt Text" width="200">  <img src="https://yumimi-code-test.s3.amazonaws.com/empty_favourites.jpeg" alt="Image Alt Text" width="200">  <img src="https://yumimi-code-test.s3.amazonaws.com/error_alert_dialog.jpeg" alt="Image Alt Text" width="200">  <img src="https://yumimi-code-test.s3.amazonaws.com/github_repo_list.jpeg" alt="Image Alt Text" width="200">  <img src="https://yumimi-code-test.s3.amazonaws.com/repo_details_fragment.jpeg" alt="Image Alt Text" width="200">  <img src="https://yumimi-code-test.s3.amazonaws.com/confirm_alert.jpeg" alt="Image Alt Text" width="200">  <img src="https://yumimi-code-test.s3.amazonaws.com/success_alert.jpeg" alt="Image Alt Text" width="200">  <img src="https://yumimi-code-test.s3.amazonaws.com/profile_web_view_fragment.jpeg" alt="Image Alt Text" width="200">  <img src="https://yumimi-code-test.s3.amazonaws.com/favourite_collapsed_view.jpeg" alt="Image Alt Text" width="200">  <img src="https://yumimi-code-test.s3.amazonaws.com/favourite_expanded_view.jpeg" alt="Image Alt Text" width="200"> 

## Installation
1. Clone the repository: `git clone https://github.com/charithgtp01/android-engineer-codecheck.git`
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.

## Usage
1. Open the app on your device.
2. Use the search functionality to find GitHub repositories.
3. Click on a repository to view its details.
4. Navigate to repo details page and click on the "See More" button.
5. Save repositories as favorites from the details page.
6. Manage favorites from the Favorites fragment.
7. Change app language from the Settings page.

## Project Structure

The project is organized into the following packages:

### [jp.co.yumemi.android.code_check.apiservices](https://github.com/charithgtp01/android-engineer-codecheck/tree/main/app/src/main/kotlin/jp/co/yumemi/android/code_check/apiservices)
- Contains classes responsible for handling API services and network requests.

### [jp.co.yumemi.android.code_check.constants](https://github.com/charithgtp01/android-engineer-codecheck/tree/main/app/src/main/kotlin/jp/co/yumemi/android/code_check/constants)
- Defines constant values used throughout the project.

### [jp.co.yumemi.android.code_check.db](https://github.com/charithgtp01/android-engineer-codecheck/tree/main/app/src/main/kotlin/jp/co/yumemi/android/code_check/db)
- Manages the database-related components, including Room database setup and entities.

### [jp.co.yumemi.android.code_check.di](https://github.com/charithgtp01/android-engineer-codecheck/tree/main/app/src/main/kotlin/jp/co/yumemi/android/code_check/di)
- Contains classes related to dependency injection using Dagger or any other dependency injection framework.

### [jp.co.yumemi.android.code_check.interfaces](https://github.com/charithgtp01/android-engineer-codecheck/tree/main/app/src/main/kotlin/jp/co/yumemi/android/code_check/interfaces)
- Defines interfaces used in the project.

### [jp.co.yumemi.android.code_check.models](https://github.com/charithgtp01/android-engineer-codecheck/tree/main/app/src/main/kotlin/jp/co/yumemi/android/code_check/models)
- Contains data models classes used to represent entities in the application.

### [jp.co.yumemi.android.code_check.repository](https://github.com/charithgtp01/android-engineer-codecheck/tree/main/app/src/main/kotlin/jp/co/yumemi/android/code_check/repository)
- Manages the data repository, handling the flow of data between the database, network, and UI.

### [jp.co.yumemi.android.code_check.ui.activities](https://github.com/charithgtp01/android-engineer-codecheck/tree/main/app/src/main/kotlin/jp/co/yumemi/android/code_check/ui/activities)
- Contains activity classes.

### [jp.co.yumemi.android.code_check.ui.bindadapters](https://github.com/charithgtp01/android-engineer-codecheck/tree/main/app/src/main/kotlin/jp/co/yumemi/android/code_check/ui/bindadapters)
- Includes custom data binding adapters.

### [jp.co.yumemi.android.code_check.ui.dialogs](https://github.com/charithgtp01/android-engineer-codecheck/tree/main/app/src/main/kotlin/jp/co/yumemi/android/code_check/ui/dialogs)
- Contains dialog-related classes.

### [jp.co.yumemi.android.code_check.ui.fragments](https://github.com/charithgtp01/android-engineer-codecheck/tree/main/app/src/main/kotlin/jp/co/yumemi/android/code_check/ui/fragments)
- Includes fragment classes.

### [jp.co.yumemi.android.code_check.utils](https://github.com/charithgtp01/android-engineer-codecheck/tree/main/app/src/main/kotlin/jp/co/yumemi/android/code_check/utils)
- Includes utility classes and helper functions used across the application.


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
- Java：17
- Gradle：8.1.3
- minSdk：23
- targetSdk：34

