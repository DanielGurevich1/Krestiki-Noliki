package com.learn.demin.krestiki_noliki;

/**
 * Created by AleDemin on 16.08.2015.
 */
public class Square implements Comparable<Square>{
    private int x,y;
    private int filled;//0-не заполнена, 1-€чейка заполнена первым игроком, 2-компьютером или вторым игроком
    private int win;//€вл€етс€ ли €чейка частью выигрышной комбинации 0-нет, 1-вертикаль, 2-горизонталь, 3-правый нижний, 4-правый верхний
    private int filledNumber;//номер хода на котором был помечен квадрат

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setFilled(int filled) {this.filled = filled;}

    public int getFilled() {
        return filled;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getWin() {

        return win;
    }

    public int getFilledNumber() {
        return filledNumber;
    }

    public void setFilledNumber(int filledNumber) {
        this.filledNumber = filledNumber;
    }

    public Square(int x,int y){
        this.x=x;
        this.y=y;
        filled=0;
        win=0;
        filledNumber=0;
    }
    
    public int price(){
        int price=0;
        for (int i = 0; i < MainActivity.game.getFieldSize(); i++) {
            for (int j = 0; j < MainActivity.game.getFieldSize(); j++) {
                price+=Math.abs(i-x)<MainActivity.game.getWinSize()?(MainActivity.game.getWinSize()-Math.abs(i - x))*MainActivity.game.getField()[i][j].filled:0;
                price+=Math.abs(j-y)<MainActivity.game.getWinSize()?(MainActivity.game.getWinSize()-Math.abs(j - y))*MainActivity.game.getField()[i][j].filled:0;
            }
        }
        return price;
    }


    @Override
    public int compareTo(Square another) {
        return this.price()-another.price();
    }
}
