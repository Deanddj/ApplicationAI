# ChatAI - JavaFX Application

A JavaFX-based desktop application developed as a school project, featuring a modern chat interface with user authentication and AI integration capabilities.

## 🚀 Features

- **User Authentication System** - Secure login with email and password validation
- **Modern UI** - Custom window decorations with minimize and close buttons
- **Transparent Window Design** - Frameless window with rounded corners
- **Chat Interface** - Interactive chat tabs for user communication
- **Settings Management** - Configurable application settings
- **Multi-language Support** - Language management system with observer pattern
- **Resource Selector** - Dynamic resource selection functionality
- **Elasticsearch Integration** - Search and data indexing capabilities

## 🛠️ Technologies Used

- **Java 17** - Programming language
- **JavaFX 17.0.6** - GUI framework
- **Maven** - Build and dependency management
- **JSON** - Data storage and configuration
- **JUnit 5** - Testing framework

## 📁 Project Structure

```
ApplicationAI/
├── src/
│   ├── main/
│   │   ├── java/org/app/applicationai/
│   │   │   ├── HelloApplication.java      # Main application entry point
│   │   │   ├── loginController.java        # Login screen controller
│   │   │   ├── ChatTabController.java      # Chat interface controller
│   │   │   ├── SettingsController.java     # Settings management
│   │   │   ├── SceneManager.java           # Scene navigation manager
│   │   │   ├── UserManager.java            # User data management
│   │   │   ├── API.java                    # API integration
│   │   │   ├── Elasticsearch.java          # Search functionality
│   │   │   └── ResourceSelector.java       # Resource selection logic
│   │   └── resources/
│   │       ├── css/
│   │       │   └── stylesheet.css          # Application styling
│   │       ├── images/                     # Image assets
│   │       └── org/app/applicationai/
│   │           ├── login-screen.fxml       # Login UI
│   │           ├── hello-view.fxml         # Main application UI
│   │           ├── chat-tab.fxml           # Chat interface UI
│   │           ├── Settings.fxml           # Settings UI
│   │           └── changeuser.json         # User credentials
│   └── test/
│       └── java/                           # Unit tests
├── pom.xml                                 # Maven configuration
└── README.md                               # This file
```

## 🎨 Design Patterns Used

- **Observer Pattern** - For language change notifications
- **Singleton Pattern** - Scene and user management
- **MVC Pattern** - Separation of UI and business logic

## 📝 Notes

- Java 17 or higher is required to run the project.
- The application uses a custom window design without default OS decorations
- User credentials are stored in `changeuser.json` (for demonstration purposes only)

## 📄 License

This is a school project for educational purposes.
