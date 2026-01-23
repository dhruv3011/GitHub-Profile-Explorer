# GitHub Profile Explorer
> A Kotlin-based application that lets you explore GitHub users and their profiles.

## Project Overview
> The GitHub Profile Explorer is developed using Kotlin within Android Studio. The project follows the Model-View-ViewModel (MVVM) architectural pattern to ensure a clean separation of concerns (SoC).

## App Demo

<img src="demo/app_demo.gif" alt="Android Demo" width="300"/>

## What This Project Shows

This repo demonstrates:

✔️ Search GitHub user by username  
✔️ Display profile details (avatar, name)  
✔️ View the repository list  
✔️ View Detail view of Repository (Repo name, star-count, fork-count, language, description)

## Tech Stack

- **UI Framework** – Jetpack Compose (Material 3)
- **Architecture** – MVVM + Clean Architecture principles
- **Networking** – Retrofit & OkHttp
- **Dependency Injection** – Hilt
- **State Management** – StateFlow & collectAsStateWithLifecycle

## Application Features & UI

- **Dynamic User Search**: Real-time search functionality allowing users to look up any GitHub profile by username.
- **Comprehensive Profile Overview**: Displays key user information, including profile imagery and name.
- **Repository Insights**: Lists public repositories with essential metrics such as Star counts, Fork counts, and project descriptions.
- **Interactive Repository Details**: Each repository item is interactive; upon selection, the user is routed to a detailed view of repo details

## Advanced Logic & State Handling

- **High-Impact "Gold Star" Logic**: To highlight popular projects, the detail view includes conditional rendering logic; if a repository's fork count exceeds 5,000, a Gold Star badge is automatically displayed.
- **Robust State Management**: The UI utilizes a sealed UiState to handle Loading, Success, Idle, and Error scenarios, preventing UI flickers and providing clear user feedback.

## Prerequisites

- Android Studio
- JDK 17+
