import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.BoardDetails;
import org.testng.annotations.Test;
import utils.Utils;

import java.io.IOException;

public class PositiveApiTests extends BaseTests {
    String boardName;
    BoardDetails boardDetails = new BoardDetails();

    @Test(groups = {"positive", "regression"})
    @Description("Verify board can be successfully created")
    public void verifyBoardCreation() throws IOException {
        boardName = Utils.createBoardName();
        requestToCreateBoard(boardName);
    }


    @Test(groups = {"positive", "regression"})
    @Description("Verify board can be successfully received")
    public void verifyGettingBoardRequest() throws IOException {

        //create board
        boardName = Utils.createBoardName();
        Response response = requestToCreateBoard(boardName);
        boardDetails = Utils.convertStringToObject(response.asString());

        //get board
        requestToGetBoard(boardDetails.getId(), boardDetails.getName());
    }

    @Test(groups = {"positive", "regression"})
    @Description("Verify board can be successfully updated")
    public void verifyUpdatingBoard() throws IOException {
        //create board
        boardName = Utils.createBoardName();
        Response response = requestToCreateBoard(boardName);
        boardDetails = Utils.convertStringToObject(response.asString());

        //update board
        boardName = Utils.createBoardName();
        boardDetails.setName(boardName);
        requestToUpdateBoard(Utils.convertObjectToJsonString(boardDetails), boardDetails.getId(), boardName);
    }

    @Test(groups = {"positive", "regression"})
    @Description("Verify board can be successfully removed")
    public void verifyRemovingBoard() throws IOException {
        //create board
        boardName = Utils.createBoardName();
        boardDetails = Utils.convertStringToObject(requestToCreateBoard(boardName).asString());
        //delete board
        requestToRemoveBoard(boardDetails.getId());
    }

}
