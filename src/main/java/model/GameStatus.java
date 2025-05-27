package model;

public enum GameStatus {
    /**
     * Имеется победитель
     */
    WINNER,

    /**
     * Игра идёт
     */
    GAME_IS_ON,

    /**
     * Игра завершилась досрочно
     */
    GAME_ENDED_AHEAD,

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
