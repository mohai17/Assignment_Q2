import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class EndPoints {

    public static Response createEmployee(PayLoads payLoads){

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payLoads)
                .log().all()
                .when()
                .post(Routes.create_url);

        return response;

    }

    public static Response readEmployeeData(int id){

        Response response = given()
                .contentType(ContentType.JSON)
                .pathParams("id",id)
                .log().all()
                .when()
                .get(Routes.read_url);

        return  response;

    }

    public static Response readAllEmployeesData(){

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(Routes.readAll_url);

        return  response;

    }

    public static Response updateEmployeeData(int id, PayLoads payLoads){

        Response response = given()
                .contentType(ContentType.JSON)
                .pathParams("id",id)
                .body(payLoads)
                .log().all()
                .when()
                .put(Routes.update_url);

        return response;

    }

    public static Response deleteEmployee(int id){

        Response response = given()
                .contentType(ContentType.JSON)
                .pathParams("id",id)
                .log().all()
                .when()
                .delete(Routes.delete_url);

        return response;

    }

}
