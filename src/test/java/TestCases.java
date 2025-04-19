import com.github.javafaker.Faker;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestCases {

    PayLoads payLoads;
    Faker faker;

    @BeforeClass
    public void setup(){

        faker = new Faker();
        payLoads = new PayLoads();

        payLoads.setEmployee_name(faker.name().fullName());
        payLoads.setEmployee_salary(faker.number().numberBetween(10000,999999));
        payLoads.setEmployee_age(faker.number().numberBetween(18,65));
        payLoads.setProfile_image(faker.avatar().image());

    }

    @Test(priority = 1)
    public void TC001_VerifyCreateUserWithValidData(ITestContext context){

        Response response = EndPoints.createEmployee(this.payLoads);
        response.then()
                .statusCode(200)
                .log().all();

        int id = response.jsonPath().get("data.id");
        context.getSuite().setAttribute("id",id);

    }

    @Test(priority = 2)
    public void TC002_ReadEmployeeDataWithValidParam(ITestContext context){

        int id = (int) context.getSuite().getAttribute("id");

        Response response = EndPoints.readEmployeeData(id);
        response.then()
                .statusCode(200)
                .log().all();

    }

    @Test(priority = 3)
    public void TC003_UpdateEmployeeWithValidData(ITestContext context){

        int id = (int) context.getSuite().getAttribute("id");

        Response response = EndPoints.updateEmployeeData(id,this.payLoads);
        response.then()
                .statusCode(200)
                .log().all();

    }

    @Test(priority = 4)
    public void TC004_DeleteEmployeeWithValidParam(ITestContext context){

        int id = (int) context.getSuite().getAttribute("id");

        Response response = EndPoints.deleteEmployee(id);

        response.then()
                .statusCode(200)
                .log().all();

    }

    @Test(priority = 5)
    public void TC005_SchemaValidationOfCreateResponse(){

        Response response = EndPoints.createEmployee(this.payLoads);

        response.then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("create_schema.json"));


    }

}
