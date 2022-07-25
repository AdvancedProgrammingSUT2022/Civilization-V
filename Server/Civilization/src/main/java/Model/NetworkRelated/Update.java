package Model.NetworkRelated;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Update {
    private UpdateType updateType;
    private ArrayList<String> params = new ArrayList<>();

    public Update() {}

    public Update(UpdateType updateTypeType, ArrayList<String> params) {
        this.updateType = updateTypeType;
        this.params = params;
    }

    public ArrayList<String> getParams() {
        return params;
    }

    public void setParams(ArrayList<String> params) {
        this.params = params;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public void addData(String value){
        params.add(value);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Update fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Update.class);
    }
}
