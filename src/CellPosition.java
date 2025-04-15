/**
 * Позиция ячейки.
 */
public class CellPosition {

    // X и Y координаты ячейки
    private final int x;
    private final int y;

    // Конструкторы
    CellPosition(int X, int Y){
        this.x = X;
        this.y = Y;
    }

    // Геттеры
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
}
