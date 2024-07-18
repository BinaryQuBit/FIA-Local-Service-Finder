# Define variables for directories
BACKEND_DIR = backend
FRONTEND_DIR = frontend

# Default target to run both backend and frontend
all: backend frontend

# Rule to run the backend
backend:
	cd $(BACKEND_DIR) && ./mvnw clean && ./mvnw install -DskipTests && ./mvnw spring-boot:run & echo $$! > backend_pid

# Rule to run the frontend
frontend:
	cd $(FRONTEND_DIR) && npm install && npm start

# Rule to wait for the backend process to complete
wait:
	@pid=$$(cat backend_pid); if [ -n "$$pid" ]; then wait $$pid; fi

# Clean rule to remove generated files
clean:
	@pid=$$(cat backend_pid); if [ -n "$$pid" ]; then kill $$pid; fi
	rm -f backend_pid

.PHONY: all backend frontend wait clean
