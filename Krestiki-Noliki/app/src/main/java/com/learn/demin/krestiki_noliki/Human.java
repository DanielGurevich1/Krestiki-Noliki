package com.learn.demin.krestiki_noliki;

/**
 * Created by AleDemin on 16.08.2015.
 */
public class Human extends Player {

    @Override
    public void doMove(int x, int y) {
        MainActivity.game.getField()[x][y].setFilled(MainActivity.game.getTurn().equals(MainActivity.game.getPlayers()[0])?1:2);//устанавливаем как помечен квадрат
        MainActivity.game.setMoveCount(MainActivity.game.getMoveCount() + 1);//увеличиваем число ходов
        MainActivity.game.getField()[x][y].setFilledNumber(MainActivity.game.getMoveCount());
    }
}



