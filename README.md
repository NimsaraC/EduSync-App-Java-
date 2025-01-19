# EduSync App - README

Welcome to the **EduSync** project repository! This mobile application is designed to help university students streamline their academic life by managing timetables, assignments, notes, and events in one centralized platform.

---

## Features

- **Timetable Management**: View daily or weekly class schedules, set reminders, and customize your timetable.
- **Assignment Tracker**: Add assignments, track deadlines, and monitor progress with a completion tracker.
- **Personal Notes**: Create, edit, and organize personal notes categorized by subjects. Backup and export notes.
- **Study Materials Repository**: Upload, download, and manage course materials with filtering and sorting options.
- **Event Calendar**: Stay updated on university events, exams, and deadlines with a detailed event calendar.
- **User Profile Management**: View and edit personal details, update preferences, and manage app settings.

---

## Installation

### Prerequisites

- [Java JDK](https://www.oracle.com/java/technologies/javase-jdk-downloads.html)
- [Android Studio](https://developer.android.com/studio) (for emulator setup and project building)
- [Git](https://git-scm.com/)
- [Firebase Account](https://firebase.google.com/) (for backend integration)

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/NimsaraC/EduSync-App-Java-.git
   ```

2. Open the project in Android Studio:
   - Launch Android Studio and select **Open an Existing Project**.
   - Navigate to the cloned repository and open it.

3. Set up Firebase:
   - Go to the [Firebase Console](https://console.firebase.google.com/).
   - Create a new project or use an existing one.
   - Add an Android app to your Firebase project by providing the package name of your app.
   - Download the `google-services.json` file and place it in the `app/` directory of your project.
   - Enable necessary Firebase services such as Authentication, Firestore, and Storage.

4. Build the project:
   - Let Android Studio download the necessary dependencies.
   - Sync the Gradle files if prompted.

5. Run the application:
   - Connect an Android device or start an emulator.
   - Click on the **Run** button or press `Shift + F10` to build and launch the app.

---

## Usage

1. **Setup**:
   - Log in or sign up with your university credentials.
   - Set up your profile and timetable.
2. **Daily Tasks**:
   - Use the timetable to track your daily schedule.
   - Add and track assignments in the Assignment Tracker.
   - Create notes and categorize them for easy retrieval.
3. **Stay Updated**:
   - Check the Event Calendar for upcoming events and deadlines.
   - Access study materials shared by peers and lecturers.
4. **Profile Management**:
   - Edit your profile, preferences, and app settings as needed.

---

## Project Structure

```
EduSync-App-Java-/
├── app/               # Main Android application module
├── res/               # Resources like layouts, drawables, and values
├── src/               # Java source code for app logic
│   ├── main/          # Main source set
│   │   ├── java/      # Java files for activities, adapters, etc.
│   │   └── res/       # Layouts, strings, and other resources
├── build.gradle       # Gradle build script
└── README.md          # Project documentation
```

---

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a feature branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add feature description"
   ```
4. Push to the branch:
   ```bash
   git push origin feature-name
   ```
5. Open a pull request.

---

## Contact

For questions or support, please contact:

- **Author**: [NimsaraC](https://github.com/NimsaraC)
- **Email**: [c.t.nimsara@gmail.com](mailto:c.t.nimsara@gmail.com)

---

## License

EduSync is licensed under the MIT License. See the LICENSE file for details.

---

## Acknowledgments

We extend our gratitude to the university community for their valuable feedback during the development of EduSync. Special thanks to beta testers for their insights and support.

