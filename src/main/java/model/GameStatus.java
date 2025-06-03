package model;

public enum GameStatus {
    /**
     * Игра идёт
     */
    GAME_IS_ON,

    /**
     * Поставлен шах
     */
    CHECK,

    /**
     * Объявлен мат
     */
    CHECKMATE,

    /**
     * Объявлен пат
     */
    STALEMATE;
}
