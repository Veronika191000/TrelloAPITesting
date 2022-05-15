import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import models.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static utils.Utils.*;

public class NegativeApiTests extends BaseTests {

    // private Logger log = Logger.getLogger(NegativeApiTests.class.getName());
    private BoardLabelNames labelNames = new BoardLabelNames("", "", "", "", "",
            "", "", "", "", "");
    private BoardPrefs boardPrefs = new BoardPrefs("private", false, "disabled",
            "members", "members", true, true, false,
            "regular", false, "blue", null,
            null, false, "dark", "#0079BF",
            "#0079BF", "#0079BF", true, true,
            true, true, true);
    private List<Members> members = Arrays.asList(new Members("5fc61647d0228828630a7627", "5f3bf91f5f2e1f6d2769520b",
            "admin", false, false));
    private Organization organization = new Organization("5fc61647d0228828630a7626", "userworkspace5713",
            "Trello workspace 57", members);

    private BoardDetails boardDetails = new BoardDetails("5fc61647d0228828630a762a", "My board name 9178", "", null,
            false, "5fc61647d0228828630a7626", null, false, "https://trello.com/b/TfcIMylx/my-board-name-9178",
            "https://trello.com/b/TfcIMylx", boardPrefs, labelNames, organization, new Limits());
    String boardName;

    @Test(groups = {"negative;", "regression"})
    public void verifyResponseWithIncorrectKeyValue() throws IOException {
        boardName = Utils.createBoardName();
        Response response = requestToCreateBoardWithIncorrectKeyValue(INCORRECT_KEY_VALUE, boardName);
        Assert.assertEquals(response.asString(), "invalid key", "Invalid response.");
    }

    @Test(groups = {"negative", "regression"})
    public void verifyResponseWithIncorrectTokenValue() throws IOException {
        boardName = Utils.createBoardName();
        Response response = requestToCreateBoardWithIncorrectTokenValue(INCORRECT_TOKEN_VALUE, boardName);
        Assert.assertEquals(response.asString(), "invalid token", "Invalid response.");
    }

    @Test(groups = {"negative", "regression"})
    public void verifyResponseWithIncorrectTokenAndKeyValues() throws JsonProcessingException {
        boardName = Utils.createBoardName();
        Response response = requestToCreateBoardWithIncorrectKeyAndTokenValues(INCORRECT_KEY_VALUE, INCORRECT_TOKEN_VALUE, boardName);
        Assert.assertEquals(response.asString(), "invalid key", "Invalid key => Check key value");
    }

    @Test(groups = {"negative", "regression"})
    public void verifyDeleteBoardWithIncorrectBoardId() throws IOException {
        Response response = requestToRemoveBoardWithIncorrectBoardId(INCORRECT_BOARD_ID);
        Assert.assertEquals(response.asString(), "invalid id", "Invalid id => Check id value");
    }

    @Test(groups = {"negative", "regression"})
    public void verifyGetBoardWithIncorrectBoardId() throws IOException {
        Response response = requestToGetBoardWithIncorrectBoardId(INCORRECT_BOARD_ID);
        Assert.assertEquals(response.asString(), "invalid id", "Invalid id => Check id value");
    }

    @Test(groups = {"negative", "regression"})
    public void verifyDeleteNon_ExistentBoard() throws IOException {
        Response response = requestToDeleteNonExistentBoard(boardDetails.getId());
        Assert.assertEquals(response.asString(), "The requested resource was not found.", "Don't try to deleThe requested resource was not foundte non-existent board");
    }

    @Test(groups = {"negative", "regression"})
    public void verifyGetNon_ExistentBoard() throws IOException {
        Response response = requestToGetNonExistentBoard(boardDetails.getId());
        Assert.assertEquals(response.asString(), "The requested resource was not found.", "Don't try to get non-existent board");
    }

    @Test(groups = {"negative", "regression"})
    public void verifyUpdateBoardWithIncorrectId() throws IOException {
        boardName = Utils.createBoardName();
        boardDetails.setName(boardName);
        String json = Utils.convertObjectToJsonString(boardDetails);
        Response response = requestToUpdateBoardWithIncorrectBoardId(json, INCORRECT_BOARD_ID);
        Assert.assertEquals(response.asString(), "invalid id", "Invalid id => Check id value");
    }

    @Test(groups = {"negative", "regression"})
    public void verifyCreateBoardWithIncorrectId() throws IOException {
        boardName = Utils.createBoardName();
        boardDetails.setName(boardName);
        Response response = requestToCreateBoardWithIncorrectBoardId(INCORRECT_BOARD_ID);
        Assert.assertTrue(response.asString().contains("invalid id"), "Don't try to create board with incorrect id");
    }
}
