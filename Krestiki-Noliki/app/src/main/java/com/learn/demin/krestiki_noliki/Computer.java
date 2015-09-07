package com.learn.demin.krestiki_noliki;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AleDemin on 16.08.2015.
 */
public class Computer extends Player {

    Game game = MainActivity.game;

    @Override
    public Square strategy() {
        ArrayList<Square> danger = new ArrayList<Square>();
        ArrayList<Square> perspective = new ArrayList<Square>();
        Square square = new Square(-1, -1);
        square = defense(danger);
        if (square.getX() > 0) return square;
        square = attack(perspective);
        if (square.getX() > 0) return square;

        perspective.addAll(danger);
        if (perspective.isEmpty()) {
            for (int i = 0; i < game.getFieldSize(); i++) {
                for (int j = 0; j < game.getFieldSize(); j++) {
                    if (game.getField()[i][j].getFilled() == 0)
                        perspective.add(game.getField()[i][j]);
                }
            }
        }
        return chooseFromList(perspective);
    }

    public Square defense(ArrayList<Square> danger) {
        Square square;
        for (int i = 0; i < game.getFieldSize(); i++) {
            for (int j = 0; j < game.getFieldSize(); j++) {
                square = vertical(i, j, danger, false);
                if (square.getX() > 0) return square;
                square = horizontal(i, j, danger, false);
                if (square.getX() > 0) return square;
                square = diagRight(i, j, danger, false);
                if (square.getX() > 0) return square;
                square = diagLeft(i, j, danger, false);
                if (square.getX() > 0) return square;
            }
        }
        return new Square(-1, -1);
    }

    public Square attack(ArrayList<Square> perspective) {
        if (game.getMoveCount() == 0)
            return game.getField()[game.getFieldSize() / 2][game.getFieldSize() / 2];
        Square square;
        for (int i = 0; i < game.getFieldSize(); i++) {
            for (int j = 0; j < game.getFieldSize(); j++) {
                square = vertical(i, j, perspective, true);
                if (square.getX() > 0) return square;
                square = horizontal(i, j, perspective, true);
                if (square.getX() > 0) return square;
                square = diagRight(i, j, perspective, true);
                if (square.getX() > 0) return square;
                square = diagLeft(i, j, perspective, true);
                if (square.getX() > 0) return square;
            }
        }
        return new Square(-1, -1);
    }


    public Square vertical(int x, int y, ArrayList<Square> list, boolean isAttack) {
        int count = 0;
        for (int i = 0; i < game.getWinSize() && y + i < game.getFieldSize(); i++) {
            if (game.getField()[x][y + i].getFilled() == (isAttack ? 2 : 1)) count++;
            if (game.getField()[x][y + i].getFilled() == (isAttack ? 1 : 2)) {
                count = 0;
                break;
            }
        }
        if (count == game.getWinSize() - 1) {
            for (int i = 0; i < game.getWinSize() && y + i < game.getFieldSize(); i++) {
                if (game.getField()[x][y + i].getFilled() == 0) return game.getField()[x][y + i];
            }
        }
        if (count == game.getWinSize() - 2) {
            for (int i = 0; i < game.getWinSize() && y + i < game.getFieldSize(); i++) {
                if (game.getField()[x][y + i].getFilled() == 0) list.add(game.getField()[x][y + i]);
            }
        }
        return new Square(-1, -1);
    }

    public Square horizontal(int x, int y, ArrayList<Square> list, boolean isAttack) {
        int count = 0;
        for (int i = 0; i < game.getWinSize() && x + i < game.getFieldSize(); i++) {
            if (game.getField()[x + i][y].getFilled() == (isAttack ? 2 : 1)) count++;
            if (game.getField()[x + i][y].getFilled() == (isAttack ? 1 : 2)) {
                count = 0;
                break;
            }
        }
        if (count == game.getWinSize() - 1) {
            for (int i = 0; i < game.getWinSize() && x + i < game.getFieldSize(); i++) {
                if (game.getField()[x + i][y].getFilled() == 0) return game.getField()[x + i][y];
            }
        }
        if (count == game.getWinSize() - 2) {
            for (int i = 0; i < game.getWinSize() && x + i < game.getFieldSize(); i++) {
                if (game.getField()[x + i][y].getFilled() == 0) list.add(game.getField()[x + i][y]);
            }
        }
        return new Square(-1, -1);
    }

    public Square diagRight(int x, int y, ArrayList<Square> list, boolean isAttack) {
        int count = 0;
        for (int i = 0; i < game.getWinSize() && x + i < game.getFieldSize() && y + i < game.getFieldSize(); i++) {
            if (game.getField()[x + i][y + i].getFilled() == (isAttack ? 2 : 1)) count++;
            if (game.getField()[x + i][y + i].getFilled() == (isAttack ? 1 : 2)) {
                count = 0;
                break;
            }
        }
        if (count == game.getWinSize() - 1) {
            for (int i = 0; i < game.getWinSize() && x + i < game.getFieldSize() && y + i < game.getFieldSize(); i++) {
                if (game.getField()[x + i][y + i].getFilled() == 0)
                    return game.getField()[x + i][y + i];
            }
        }
        if (count == game.getWinSize() - 2) {
            for (int i = 0; i < game.getWinSize() && x + i < game.getFieldSize() && y + i < game.getFieldSize(); i++) {
                if (game.getField()[x + i][y + i].getFilled() == 0)
                    list.add(game.getField()[x + i][y + i]);
            }
        }
        return new Square(-1, -1);
    }

    public Square diagLeft(int x, int y, ArrayList<Square> list, boolean isAttack) {
        int count = 0;
        for (int i = 0; i < game.getWinSize() && x + i < game.getFieldSize() && y - i >= 0; i++) {
            if (game.getField()[x + i][y - i].getFilled() == (isAttack ? 2 : 1)) count++;
            if (game.getField()[x + i][y - i].getFilled() == (isAttack ? 1 : 2)) {
                count = 0;
                break;
            }
        }
        if (count == game.getWinSize() - 1) {
            for (int i = 0; i < game.getWinSize() && x + i < game.getFieldSize() && y - i >= 0; i++) {
                if (game.getField()[x + i][y - i].getFilled() == 0)
                    return game.getField()[x + i][y - i];
            }
        }
        if (count == game.getWinSize() - 2) {
            for (int i = 0; i < game.getWinSize() && x + i < game.getFieldSize() && y - i >= 0; i++) {
                if (game.getField()[x + i][y - i].getFilled() == 0)
                    list.add(game.getField()[x + i][y - i]);
            }
        }
        return new Square(-1, -1);
    }

    public Square chooseFromList(ArrayList<Square> list) {
        HashMap<Square, Integer> squareCount = new HashMap<Square, Integer>();
        ArrayList<Square> maxCountList = new ArrayList<Square>();
        int maxCount = 1;
        for (Square square : list) {
            if (squareCount.containsKey(square)) {
                squareCount.put(square, squareCount.get(square) + 1);
                if (squareCount.get(square) > maxCount) maxCount = squareCount.get(square);
            } else squareCount.put(square, 1);
        }
        for (Map.Entry<Square, Integer> entry : squareCount.entrySet()) {
            if (entry.getValue() == maxCount) maxCountList.add(entry.getKey());
        }
        Collections.sort(maxCountList);
        return maxCountList.get(maxCountList.size() - 1);
    }

}
