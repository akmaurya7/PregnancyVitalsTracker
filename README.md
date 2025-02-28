# Pregnancy Vitals Tracker

This Android application helps users track and log their pregnancy vitals such as blood pressure, weight, baby kicks, and heart rate. It uses **Jetpack Compose** for the UI, **Room** for local data storage, **Hilt** for dependency injection, **WorkManager** to schedule notifications, and **MVVM** architecture for clear separation of concerns.

## Features
- **Log Vitals**: Users can add blood pressure (Sys/Dia), weight, baby kicks, and heart rate.
- **Display Vitals**: All logged vitals are displayed in a list.
- **Date & Time**: Each log entry captures the current timestamp.
- **Clean UI**: Built entirely with Jetpack Compose, ensuring modern UI design principles.
- **Local Persistence**: Uses Room database to store and retrieve logs.
- **Dependency Injection**: Leverages Hilt for cleaner, testable code.
- **Scheduled Reminders**: Uses WorkManager to schedule periodic notifications, reminding users to log their vitals.

## Getting Started

1. **Clone or Download** this repository.  
2. **Open** the project in **Android Studio** (preferably the latest version).  
3. **Sync** the project to download dependencies (`Tools > Sync Project with Gradle Files`).  
4. **Run** the project on an emulator or physical device.

---

## Usage Flow

1. **Home Screen**  
   - Displays a list of all previously logged vitals.  
   - Has a floating action button (FAB) to add new vitals.

2. **Add Vitals Dialog**  
   - When the user taps on the FAB, a dialog pops up.  
   - The user enters Sys BP, Dia BP, Weight, and Baby Kicks.  
   - On clicking **Submit**, the data is stored in the Room database and the dialog closes.

3. **Displaying Logged Vitals**  
   - Each log entry is displayed as a **VitalCard** in a `LazyColumn`.  
   - The **VitalCard** shows blood pressure, weight, baby kicks, and the recorded timestamp.

4. **Notification Reminders**  
   - The app uses WorkManager to schedule periodic notifications (e.g., every hour) to remind users to log their vitals.
   - When notifications are enabled, the app icon reflects this status, and users receive timely reminders.

---

## Screenshots

| Home Screen | Add Vital Dialog |
|-------------|------------------|
| <img src="https://github.com/user-attachments/assets/5f5fbac8-d1ef-487d-9f5e-ce8263b21477" width="250"/> | <img src="https://github.com/user-attachments/assets/8558500a-3caa-49a7-999c-013086f225d8" width="250"/> |

