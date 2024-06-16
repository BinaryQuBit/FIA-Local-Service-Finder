#!/bin/bash

# Navigate to backend, clean, install, and run the backend
cd backend
./mvnw clean
./mvnw install -DskipTests
./mvnw spring-boot:run &

# Save the PID of the backend process
BACKEND_PID=$!

# Navigate to frontend and start the frontend server
cd ../frontend
npm install
npm start

# Wait for the backend process to complete
wait $BACKEND_PID
