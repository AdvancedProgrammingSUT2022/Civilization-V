package Model.Enums;

public enum Menus {
    LOGIN_MENU("loginPage"),
    MAIN_MENU("mainMenu"),
    PROFILE_MENU("profileMenu"),
    GAME_PAUSE_MENU("gamePauseMenu"),
    SCORE_BOARD("scoreBoard"),
    GAME_MENU("gameMenu"),
    CHANGE_USERNAME_PASSWORD_MENU("changeUserPassMenu"),
    PRE_GAME("preGame");

    public final String value;


    Menus(String name) {
        this.value = name;
    }
}
