# Basic gRPC Chat Application

Welcome to a basic Chat Application implemented using gRPC! This chat application allows users to chat in real-time, send messages,
and interact with others in a simple and efficient way.
The UI has been implemented using jetpack compose, dagger and the MVVM pattern for desktop.

## Getting Started

Follow these steps to set up and run the chat application locally:

- make sure java is installed on your system
- open up this project in android studio
- run this command in the root of this project folder `./gradlew generateProto` or
- from the gradle tasks window in android studio, select generate proto.
- run the `main` method in ChatServer.kt to start the server.
- run the `main` method in App.kt to start the gui