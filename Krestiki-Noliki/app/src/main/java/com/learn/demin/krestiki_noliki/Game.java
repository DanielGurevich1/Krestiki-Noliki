package com.learn.demin.krestiki_noliki;


/**
 * Created by AleDemin on 16.08.2015.
 */
public class Game {

    private int playersCount;
    private int fieldSize = 10;
    private int winSize = 5;
    private int moveCount = 0;
    private Player[] players;
    private Square[][] field;
    private boolean isOver;//закончена ли игра

    public Game() {
        players = new Player[2];
        players[0] = new Human();
    }

    public void create() {
        field = new Square[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[i][j]=new Square(i,j);
            }
        }
        moveCount = 0;
        isOver=false;
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getWinSize() {
        return winSize;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public Square[][] getField() {
        return field;
    }

    public void setField(Square[][] field) {
        this.field = field;
    }

    public void setFieldSize(int fieldSize) {
        this.fieldSize = fieldSize;
    }

    public void setWinSize(int winSize) {
        this.winSize = winSize;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    public void setIsOver(boolean isOver) {
        this.isOver = isOver;
    }

    public boolean isOver() {

        return isOver;
    }

    public void resetField() {
        for (int i = 0; i < fieldSize; i++)
            for (int j = 0; j < fieldSize; j++) {
                field[i][j].setFilled(0);
                field[i][j].setWin(0);
            }
    }

    public Player getTurn() {
        return players[(moveCount + (players[0].isX() ? 0 : 1)) % 2];
    }

    public int diagRightWin(int x, int y) {
        int count = 0;
        for (int i = 0; i < winSize && x+i<fieldSize && y+i<fieldSize; i++) {
            if (field[x][y].getFilled() == 2 && getTurn().equals(players[0]) || field[x][y].getFilled() == 1 && getTurn().equals(players[1]))
            if (field[x + i][y + i].getFilled() == 2 && getTurn().equals(players[0]) || field[x + i][y + i].getFilled() == 1 && getTurn().equals(players[1]))
                count++;
        }
        return count;
    }

    public int diagLeftWin(int x, int y) {
        int count = 0;
        for (int i = 0; i < winSize && x+i<fieldSize && y-i>=0; i++) {
            if (field[x][y].getFilled() == 2 && getTurn().equals(players[0]) || field[x][y].getFilled() == 1 && getTurn().equals(players[1]))
            if (field[x + i][y - i].getFilled() == 2 && getTurn().equals(players[0]) || field[x + i][y - i].getFilled() == 1 && getTurn().equals(players[1]))
                count++;
        }
        return count;
    }

    public boolean win() {
        int count = 0;
        //вертикаль
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j].getFilled() == 2 && getTurn().equals(players[0]) || field[i][j].getFilled() == 1 && getTurn().equals(players[1]))
                    count++;
                else count = 0;
                if (count == winSize) {
                    for (int k = 0; k < winSize; k++) {
                        field[i][j - k].setWin(1);
                    }
                    isOver=true;
                    return true;
                }
            }
            count = 0;
        }
        //горизонталь
        count = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[j][i].getFilled() == 2 && getTurn().equals(players[0]) || field[j][i].getFilled() == 1 && getTurn().equals(players[1]))
                    count++;
                else count = 0;
                if (count == winSize) {
                    for (int k = 0; k < winSize; k++) {
                        field[j - k][i].setWin(2);
                    }
                    isOver=true;
                    return true;
                }
            }
            count = 0;
        }
        //диагональ
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (i <= field.length - winSize && j <= field[0].length - winSize)
                    if (diagRightWin(i, j) == winSize) {
                        for (int k = 0; k < winSize; k++) {
                            field[i + k][j + k].setWin(3);
                        }
                        isOver=true;
                        return true;
                    }
                if (i <= field.length - winSize && j >= winSize - 1)
                    if (diagLeftWin(i, j) == winSize) {
                        for (int k = 0; k < winSize; k++) {
                            field[i + k][j - k].setWin(4);
                        }
                        isOver=true;
                        return true;
                    }
            }
        }
        return false;
    }

    public Square findLastSquare(){//ищет последний помеченный квадрат,нужно для Cancel
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if(field[i][j].getFilledNumber()==moveCount) return field[i][j];
            }
        }
        return null;
    }

    public void cancel(){
        field[findLastSquare().getX()][findLastSquare().getY()].setFilled(0);
        field[findLastSquare().getX()][findLastSquare().getY()].setFilledNumber(0);
        moveCount--;
    }

}
