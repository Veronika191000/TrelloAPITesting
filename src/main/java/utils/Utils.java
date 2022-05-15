package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import models.BoardDetails;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {

    public static final String POST_QUERY = "https://api.trello.com/1/boards/?key={KEY}&token={TOKEN}&name={boardName}";
    public static final String GET_PUT_DELETE_QUERY = "https://api.trello.com/1/boards/{boardId}?key={KEY}&token={TOKEN}";
    public static final String KEY = "key";
    public static final String TOKEN = "token";
    public static final String INCORRECT_KEY_VALUE = "e6d76b07153a275edf7831fc3f4ee209e3heg63g6d36";
    public static final String INCORRECT_TOKEN_VALUE = "db7cefbca238d580649b279deb0ce5405a819938aaa8ce9810d729f5356d3311yeb463hdy6d";
    public static final String INCORRECT_BOARD_ID = "cjuiwh74yr34h3724ry237g6t3e362ge";
    public static final String CONTENT_TYPE = "application/json";


    public static String createBoardName() {
        return "My_board_" + Math.random() * 10000;
    }

    public static String convertObjectToJsonString(BoardDetails boardDetails) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(boardDetails);
    }

    public static BoardDetails convertStringToObject(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, BoardDetails.class);
    }

    public static String getProperty(String value) throws IOException {
        InputStream input = Utils.class.getClassLoader().getResourceAsStream("credential.properties");
        Properties properties = new Properties();
        if (input == null) {
            System.out.println("Sorry, unable to find " + "credential.properties");
        }
        properties.load(input);
        return properties.getProperty(value);
    }
}
