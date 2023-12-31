package api;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ReqresTest {
    private final static String URL = "https://reqres.in/";

    @Test
    public void checkAvatarAndIdTest() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.requestSpecificationOK200());
        List<UserData> users = given()
                .when()
//                .contentType(ContentType.JSON)
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);

        users.stream().forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));

        Assert.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));


        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
        List<String> ids = users.stream().map(x -> x.getId().toString()).collect(Collectors.toList());

        for (int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }

    @Test
    public void successRegTest() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.requestSpecificationOK200());

        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Register register = new Register("eve.holt@reqres.in", "pistol");
        SuccessReg successReg = given()
                .body(register)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(SuccessReg.class);

        Assert.assertNotNull(successReg.getId());
        Assert.assertNotNull(successReg.getToken());

        Assert.assertEquals(id, successReg.getId());
        Assert.assertEquals(token, successReg.getToken());
    }

    @Test
    public void unSuccessRegTest() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.requestSpecificationError400());

        Register register = new Register("sydney@fife", "");

        UnSuccessReg unSuccessReg = given()
                .body(register)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(UnSuccessReg.class);

        Assert.assertEquals("Missing password", unSuccessReg.error());
    }

    @Test
    public void sortedYearsTest() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.requestSpecificationOK200());

        List<ColorsData> colorsData = given()
                .when()
                .get("api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ColorsData.class);

        List<Integer> years = colorsData.stream().map(ColorsData::getYear).collect(Collectors.toList());
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());

        Assert.assertEquals(sortedYears, years);
    }

    @Test
    public void deleteUserTest(){
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.requestSpecificationUnique(204));

        given()
                .when()
                .delete("api/users/2")
                .then().log().all();

    }

}
