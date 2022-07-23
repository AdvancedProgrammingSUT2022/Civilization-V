package Model.Enums;

public enum Menus {
    LOGIN_MENU("loginPage"),
    DIPLOMACY_PANEL("diplomacyPanel"),
    CHAT_MENU("ChatPage"),
    TRADE_PANEL("tradePanel"),
    MAIN_MENU("mainMenu"),
    PROFILE_MENU("profileMenu"),
    GAME_PAUSE_MENU("gamePauseMenu"),
    SCORE_BOARD("scoreBoard"),
    GAME_MENU("gameMenu"),
    CHANGE_USERNAME_PASSWORD_MENU("changeUserPassMenu"),
    PRE_GAME("preGame"),
    Tech_Tree("TechTree"),
    Trailer("trailer"),
    LoadMenu("LoadMenu");

    public final String value;


    Menus(String name) {
        this.value = name;
    }
}
