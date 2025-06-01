package ui;

import model.Figures.Figure;
import model.Figures.FiguresTypes;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    /**
     * Поле с именем фигуры
     */
    private final JLabel nameLabel;

    /**
     * Поле с иконкой фигуры
     */
    private final JLabel iconLabel;

    /**
     * Поле с количеством жизней фигуры
     */
    private final JLabel livesLabel;

    public InfoPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(0xF0F0F0));

        // Имя фигуры
        nameLabel = new JLabel("Фигура: —", SwingConstants.CENTER);
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 16f));
        add(nameLabel, BorderLayout.NORTH);

        // Иконка фигуры
        iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(iconLabel, BorderLayout.CENTER);

        // Жизни фигуры
        livesLabel = new JLabel("Жизней: —", SwingConstants.CENTER);
        livesLabel.setFont(livesLabel.getFont().deriveFont(14f));
        add(livesLabel, BorderLayout.SOUTH);

        // Изначально ничего не выбрано
        clear();
    }

    /**
     * Показать информацию о фигуре
     * @param figure фигура
     * @param figureIcon иконка фигуры
     */
    public void showFigureInfo(Figure figure, ImageIcon figureIcon){
        if (figure == null){
            clear();
            return;
        }

        // Задать тип фигуры
        nameLabel.setText("Фигура: " + figure.getFigureType().toString());

        // Задать иконку фигуры
        iconLabel.setIcon(figureIcon);

        // Задать количество жизней
        if (figure.getFigureType() == FiguresTypes.KING){
            livesLabel.setText("Жизней: бесконечно");
        }
        else{
            livesLabel.setText("Жизней: " + figure.getLives());
        }
    }


    /**
     * Очистить все поля
     */
    public void clear(){
        nameLabel.setText("Фигура: -");
        iconLabel.setIcon(null);
        livesLabel.setText("Жизней: -");
    }
}
