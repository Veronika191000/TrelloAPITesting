import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import utils.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static utils.Utils.*;

public class BaseTests {

    public String key;
    public String token;
    public RequestSpecification requestSpec;

    @BeforeClass(alwaysRun = true)
    public void setup() throws IOException {
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();

        key = getProperty(KEY);
        token = getProperty(TOKEN);
    }

    @Step("Request to Trello API to create Board with name: {boardName}")
    public Response requestToCreateBoard(String boardName) throws IOException {
        return given()
                .spec(requestSpec)
                .when()
                .body("")
                .post(Utils.POST_QUERY, key, token, boardName)
                .then()
                .body("name", equalTo(boardName))
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Step("Request to Trello API to remove Board with id: {boardId}")
    public void requestToRemoveBoard(String boardId) {
        given()
                .spec(requestSpec)
                .when()
                .delete(Utils.GET_PUT_DELETE_QUERY, boardId, key, token)
                .then()
                .log().all()
                .statusCode(200);
    }

    @Step("Request to Trello API to update Board with id: {boardId} with a new name: {boardName}")
    public void requestToUpdateBoard(String boardDetails, String boardId, String boardName) {
        given()
                .spec(requestSpec)
                .when()
                .contentType(Utils.CONTENT_TYPE)
                .body(boardDetails)
                .put(Utils.GET_PUT_DELETE_QUERY, boardId, key, token)
                .then()
                .body("name", equalTo(boardName))
                .log().all()
                .statusCode(200);
    }

    @Step("Request to Trello API to get Board with id: {boardId}")
    public void requestToGetBoard(String boardId, String boardName) {
        given()
                .spec(requestSpec)
                .when()
                .contentType(Utils.CONTENT_TYPE)
                .get(Utils.GET_PUT_DELETE_QUERY, boardId, key, token)
                .then()
                .body("id", equalTo(boardId))
                .body("name", equalTo(boardName))
                .log().all()
                .statusCode(200);
    }

    @Step("Request to Trello API to create Board with incorrect token: {incorrectToken}")
    public Response requestToCreateBoardWithIncorrectTokenValue(String incorrectToken, String boardName) {
        return given()
                .spec(requestSpec)
                .when()
                .body("")
                .post(Utils.POST_QUERY, key, incorrectToken, boardName)
                .then()
                .statusCode(401)
                .log().all()
                .extract().response();
    }

    @Step("Request to Trello API to create Board with incorrect key: {incorrectKey}")
    public Response requestToCreateBoardWithIncorrectKeyValue(String incorrectKey, String boardName) {
        return given()
                .spec(requestSpec)
                .when()
                .body("")
                .post(Utils.POST_QUERY, incorrectKey, token, boardName)
                .then()
                .statusCode(401)
                .log().all()
                .extract().response();
    }

    @Step("Request to Trello API to create Board with incorrect key: {incorrectKey} and incorrect token: {incorrectToken}")
    public Response requestToCreateBoardWithIncorrectKeyAndTokenValues(String incorrectKey, String incorrectToken, String boardName) {
        return given()
                .when()
                .body("")
                .post(Utils.POST_QUERY, incorrectKey, incorrectToken, boardName)
                .then()
                .statusCode(401)
                .extract().response();
    }

    @Step("Request to Trello API to remove Board with incorrect id: {boardId}")
    public Response requestToRemoveBoardWithIncorrectBoardId(String boardId) {
        return given()
                .spec(requestSpec)
                .when()
                .delete(Utils.GET_PUT_DELETE_QUERY, boardId, key, token)
                .then()
                .log().all()
                .statusCode(400)
                .extract().response();
    }

    @Step("Request to Trello API to get Board with incorrect id: {boardId}")
    public Response requestToGetBoardWithIncorrectBoardId(String boardId) {
        return given()
                .spec(requestSpec)
                .when()
                .get(Utils.GET_PUT_DELETE_QUERY, boardId, key, token)
                .then()
                .log().all()
                .statusCode(400)
                .extract().response();
    }

    @Step("Request to Trello API to update Board with incorrect id: {boardId}")
    public Response requestToUpdateBoardWithIncorrectBoardId(String boardDetails, String boardId) {
        return given()
                .spec(requestSpec)
                .when()
                .body(boardDetails)
                .put(Utils.GET_PUT_DELETE_QUERY, boardId, key, token)
                .then()
                .log().all()
                .statusCode(400)
                .extract().response();
    }

    @Step("Request to Trello API to delete non existent Board with id: {id}")
    public Response requestToDeleteNonExistentBoard(String id) {
        return given()
                .when()
                .spec(requestSpec)
                .delete(Utils.GET_PUT_DELETE_QUERY, id, key, token)
                .then()
                .log().all()
                .statusCode(404)
                .extract().response();
    }

    @Step("Request to Trello API to get Board with incorrect id: {id}")
    public Response requestToGetNonExistentBoard(String id) {
        return given()
                .when()
                .spec(requestSpec)
                .get(Utils.GET_PUT_DELETE_QUERY, id, key, token)
                .then()
                .log().all()
                .statusCode(404)
                .extract().response();
    }

    @Step("Request to Trello API to create Board with incorrect id: {boardId}")
    public Response requestToCreateBoardWithIncorrectBoardId(String boardId) {
        return given()
                .spec(requestSpec)
                .when()
                .body("")
                .put(Utils.GET_PUT_DELETE_QUERY, boardId, key, token)
                .then()
                .log().all()
                .statusCode(400)
                .extract().response();
    }
}
