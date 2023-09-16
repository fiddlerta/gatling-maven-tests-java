package apiHealthChecks;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.Simulation;

public class ApiHealthChecksSimulation extends Simulation {

    // HTTP configurations
    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:4000/api-docs")
            .acceptHeader("application/json");

    // Scenario definition

    private static ChainBuilder getAllTodos =
            exec(http("Get all todos")
                    .get("/todos"));

    private static ChainBuilder getTodoById =
            exec(http("Get todo by id")
                    .get("/todos/0"));

    // TODO: Add requests for POST, PUT, DELETE

    private ScenarioBuilder scn = scenario("Retrieve all todos and one particular by id")
            .exec(getAllTodos)
            .pause(2)
            .exec(getTodoById);

    // Load scenario

    {
        setUp(
                scn.injectOpen(
                        nothingFor(5),
                        rampUsers(5).during(10)
                ).protocols(httpProtocol)
        ).maxDuration(20);
    }

}
