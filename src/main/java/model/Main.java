package model;

import ui.BoardPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chess");

            // Загрузка иконки из ресурсов
            try {
                Image icon = ImageIO.read(
                        Main.class.getResourceAsStream("/chess.png")
                );
                frame.setIconImage(icon);
            } catch (IOException | NullPointerException e) {
                // Если не удалось загрузить — можно просто проигнорировать или залогировать
                e.printStackTrace();
            }

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Создаём нашу панель с клетками
            BoardPanel boardPanel = new BoardPanel(game.getBoard());
            frame.getContentPane().add(boardPanel);

            frame.pack();               // подгоняем размер под preferredSize клеток
            frame.setLocationRelativeTo(null); // центрируем окно на экране
            frame.setVisible(true);
            boardPanel.paintFiguresOnBoard();
        });
    }
}