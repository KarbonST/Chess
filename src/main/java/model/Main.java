package model;

import model.events.*;
import ui.BoardPanel;
import ui.InfoPanel;
import ui.events.BoardClickListener;

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

            // Загрузка иконки игры
            try {
                Image icon = ImageIO.read(
                        Main.class.getResourceAsStream("/chess.png")
                );
                frame.setIconImage(icon);
            } catch (IOException | NullPointerException e) {
                // При неудачном прочтении
                e.printStackTrace();
            }

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Создание шахматной доски
            BoardPanel boardPanel = new BoardPanel(game.getBoard());

            // Создание информационной панели
            InfoPanel infoPanel = new InfoPanel();
            boardPanel.setInfoPanel(infoPanel);

            // Модель слушает GUI
            boardPanel.addBoardClickListener(new BoardClickListener() {
                @Override
                public void cellClicked(CellPosition pos) {
                    game.workWithFigure(pos);
                }
            });

            // GUI слушает модель
            game.addFigureActionListener(new FigureActionListener() {
                @Override
                public void figureActivated(FigureActivatedEvent e) {
                    boardPanel.highlightCellWithActiveFigure(e.getFigure().getCell().getPosition());
                    boardPanel.highlightCells(e.getFigure().getAllCellsPositionsFromTrajectories());
                }
                @Override
                public void figureDeactivated(FigureClearSelectedEvent e) {
                    boardPanel.clearHighlights();
                }
                @Override
                public void figureMoved(FigureMovedEvent e) {
                    boardPanel.moveFigure(
                            e.getFrom().getPosition(),
                            e.getTo().getPosition()
                    );
                }
                @Override
                public void figureDeadFromItself(FigureDeadFromItselfEvent e) {
                    boardPanel.deleteFigure(e.getFigurePosition());
                }

            });
            game.addGameFinishActionListener(new GameStatusActionListener() {
                @Override
                public void gameFinishInDraw(GameStatusDrawEvent e) {
                    JOptionPane.showMessageDialog(frame, "Ничья!");
                }
                @Override
                public void gameFinishWithWinner(GameStatusWinnerEvent e) {
                    JOptionPane.showMessageDialog(frame,
                            "Победила команда: " + e.getWinnerTeam().getColorAsString());
                }
                @Override
                public void teamHasCheck(GameStatusCheckEvent e) {
                    JOptionPane.showMessageDialog(frame,
                            "Шах команде: " + e.getCheckTeam().getColorAsString());
                }
            });


            frame.add(boardPanel, BorderLayout.CENTER);
            frame.add(infoPanel, BorderLayout.EAST);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            boardPanel.paintFiguresOnBoard();
        });
    }
}