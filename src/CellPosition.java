public class CellPosition {

    // X и Y координаты ячейки
    private final int x;
    private final int y;

    // Конструкторы
    CellPosition(int currentX, int currentY){
        this.x = currentX;
        this.y = currentY;
    }

    // Геттеры
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
}
