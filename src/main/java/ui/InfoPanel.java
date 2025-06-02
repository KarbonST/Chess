package ui;

import model.Figures.Figure;
import model.Figures.FiguresTypes;
import ui.events.InfoPanelButtonUpgradeClickListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Кнопка Upgrade
     */
    private final JButton upgradeButton;

    /**
     * Список слушателей нажатия на кнопку Upgrade
     */
    private final List<InfoPanelButtonUpgradeClickListener> upgradeClickListeners = new ArrayList<>();

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

        // Нижняя часть для отображения жизней и кнопки
        JPanel bottomPanel = new JPanel(new BorderLayout(5,5));
        bottomPanel.setOpaque(false);

        // Жизни фигуры
        livesLabel = new JLabel("Жизней: —", SwingConstants.CENTER);
        livesLabel.setFont(livesLabel.getFont().deriveFont(14f));
        bottomPanel.add(livesLabel, BorderLayout.NORTH);

        // Кнопка Upgrade
        upgradeButton = new JButton("Upgrade");
        bottomPanel.add(upgradeButton, BorderLayout.SOUTH);

        upgradeButton.addActionListener(e -> {
            for (var l: upgradeClickListeners){
                l.buttonUpgradeClicked();
            }
        });

        add(bottomPanel, BorderLayout.SOUTH);

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

        // Активизировать кнопку Upgrade для пешки если ещё не нажимали кнопку
        if (figure.getFigureType() == FiguresTypes.PAWN && !figure.getTeam().isUpgraded()){
            upgradeButton.setEnabled(true);
        }
        else{
            upgradeButton.setEnabled(false);
        }
    }

    /**
     * Очистить все поля
     */
    public void clear(){
        nameLabel.setText("Фигура: -");
        iconLabel.setIcon(null);
        livesLabel.setText("Жизней: -");
        upgradeButton.setEnabled(false);
    }

    /**
     * Добавление слушателя клика по кнопке Upgrade
     */
    public void addUpgradeClickListener(InfoPanelButtonUpgradeClickListener l){
        upgradeClickListeners.add(l);
    }

    /**
     * Удаление слушателя клика по кнопке Upgrade
     */
    public void removeUpgradeClickListener(InfoPanelButtonUpgradeClickListener l){
        upgradeClickListeners.remove(l);
    }

}
