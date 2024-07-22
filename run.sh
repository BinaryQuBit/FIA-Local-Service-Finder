#!/bin/bash

# Move to the backend directory and start the backend server
# cd /usr/src/app/backend
# java -jar backend-0.0.1-SNAPSHOT.jar

# Start the frontend server
cd /usr/src/app/frontend
npm start

# Wait for both processes to end
# wait $BACKEND_PID
# wait $FRONTEND_PID
