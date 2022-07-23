package Model.NetworkRelated;

import java.util.ArrayList;

public class RequestCreator {
    private static RequestCreator requestCreator;
    public static RequestCreator getInstance(){
        if(requestCreator == null)
            requestCreator = new RequestCreator();
        return  requestCreator;
    }
    private RequestCreator(){}

}
