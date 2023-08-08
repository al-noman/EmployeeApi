# Employee API Service

This Employee API Service allows to manage employees. It exposes REST endpoints for creating, retrieving, updating, and deleting an employee record.

## How to run on localhost
### Prerequisite:
- Docker runtime available and Docker daemon is running in local machine.
- Following ports are free and available:
    - 8080, 5432, 22181, 29092

### Running steps:
- Open `Terminal`.
- Navigate to the root path of the project.
- Execute `docker-compose up` command from the root path.
- This will start the whole application in 4 different containers named: `app`, `kafka`, `zookeeper`, `postgres`.

### Documentations:
The available APIs, calling mechanism, and the expected responses are all documented in [here](https://documenter.getpostman.com/view/2367507/2s9Xy2NBky).

N.B: Due to lack of time, authentication feature is not implemented. 
Documenting the APIs with Swagger is also not done for same reason. Instead, the above postman documentation is provided for reference. 

