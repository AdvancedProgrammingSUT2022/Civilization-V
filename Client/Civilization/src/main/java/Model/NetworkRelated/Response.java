package Model.NetworkRelated;

import com.google.gson.Gson;

public class Response {
    private ResponseType responseType;
    private String message;


    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Response fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Response.class);
    }
}
