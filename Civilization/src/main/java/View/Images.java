package View;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public enum Images {
    profilePics(new ArrayList<>(){{
        add(new Image("/images/profiles/1.png"));
        add(new Image("/images/profiles/2.png"));
        add(new Image("/images/profiles/3.png"));
        add(new Image("/images/profiles/4.png"));
    }});

    public final ArrayList<Image> pics;


    Images(ArrayList<Image> pics) {
        this.pics = pics;
    }
}
