package com.learn.demin.krestiki_noliki;

/**
 * Created by AleDemin on 16.08.2015.
 */
public class Player {
    protected boolean isX;

    public void setIsX(boolean isX) {
        this.isX = isX;
    }

    public boolean isX() {

        return isX;
    }

    public void doMove(int x, int y) {
        MainActivity.game.getField()[x][y].setFilled(MainActivity.game.getTurn().equals(MainActivity.game.getPlayers()[0]) ? 1 : 2);//устанавливаем как помечен квадрат
        MainActivity.game.setMoveCount(MainActivity.game.getMoveCount() + 1);//увеличиваем число ходов
        MainActivity.game.getField()[x][y].setFilledNumber(MainActivity.game.getMoveCount());
    }

    public Square strategy(){
        return null;
    }
}

