Welcome to SpaceX.

If you are reading this file on github use the code format for readability.

To use the application clone the repositories for frontend and backend.  Start up the backend in the Spring Boot root directory
with the command 'docker-compose up'.  Start the frontend implementation in the angular root dir and use the command 'ng serve'.
Open a fresh tab and access http://localhost:4200/register to begin.

You first need to register a user and then login as that user.  The application is pretty straight forward you can add rockets and launches
associated to a rocket using the 'Add Rocket' form and 'Add Launch' form.  You can view the rockets and their associated launches by clicking
the 'Launches' link.

All tables can be sorted by any column including the sub tables of associated launches.

When finished click Logout to end the session.

The API endpoints:
    baseUrl: http://localhost:8080
    POST /api/auth/register -- create new user
        params: {"username":<string>, "password": <string>}
    POST /api/auth/login -- authenticate with application
        params {"username":<string>, "password":<string>}
    GET /api/auth/logged
        params
            This endpoint requires no params and will determine if the user is logged in using Spring Security context.
    POST /api/rockets -- add a new rocket
        params: {"name": <string>, "weightInPounds": number}
    GET /api/rockets/{id} -- retrieve rocket by primary key
        params: number as a path variable in /api/rockets/{number}
    GET /api/rockets/name/{name} -- retrieve rocket by name
        params: rocket name string in path variable /api/rockets/name/{rocketName}
    GET /api/rockets -- retrieve all rockets in List format
        params: none
    PUT /api/rockets/{id} -- update rocket
        params: {"name": <string>, "weightInPounds": number} and the primary key id in the path variable /api/rockets/{id}
    DELETE /api/rockets/{id} -- delete rocket by id
        params: id number in path variable for /api/rockets/{id}
    POST /api/launches/rocketname/{rocketname} -- add a new Launch
        params: {"date": <"YYYY-MM-DD">, "success" : <boolean>} and the rocket name in the path variable for /api/launches/rocketname/{rocketname}
    GET /api/launches/{id} -- fetch launch by primary key id
        params: id number in the path variable for /api/launches/{id} (replace {id})
    GET /api/launches -- fetch all launches grouped by their associated rocket in List format
        params: none
    PUT /api/launches/{id} -- update launch
        params: {"date": <"YYYY-MM-DD">, "success": <boolean>} and the id as a path variable for /api/launches/{id} (replace {id})
    DELETE /api/launches/{id} delete launch
        params: id as path variable for /api/launches/{id} (replace {id})
