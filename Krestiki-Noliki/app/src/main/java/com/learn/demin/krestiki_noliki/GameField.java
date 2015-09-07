package com.learn.demin.krestiki_noliki;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;


public class GameField extends ActionBarActivity {

    Button cancel, newGame, plus, minus;
    GameFieldView gameField;
    int margin;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.gamefield_new_game) {
                Intent intent = new Intent(GameField.this, MainActivity.class);
                startActivity(intent);
            } else {
                if (MainActivity.game.getMoveCount() > 0 && !MainActivity.game.isOver()) {
                    gameField.cancelDraw();
                    MainActivity.game.cancel();
                }
            }
        }
    };
    View.OnClickListener zoomListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.plus) {
                gameField.zoomPlus();
            } else {
                gameField.zoomMinus();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_field);
        cancel = (Button) findViewById(R.id.gamefield_move_cancel);
        newGame = (Button) findViewById(R.id.gamefield_new_game);
        plus = (Button) findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);
        gameField = (GameFieldView) findViewById(R.id.view);
        bulidGameField();
        newGame.setOnClickListener(listener);
        cancel.setOnClickListener(listener);
        plus.setOnClickListener(zoomListener);
        minus.setOnClickListener(zoomListener);
        if (!MainActivity.game.getPlayers()[0].isX() && MainActivity.game.getPlayers()[1] instanceof Computer) {
            int cellX = MainActivity.game.getPlayers()[1].strategy().getX();
            int cellY = MainActivity.game.getPlayers()[1].strategy().getY();
            gameField.drawSymbol(MainActivity.game.getTurn(), cellX, cellY);
            MainActivity.game.getPlayers()[1].doMove(cellX, cellY);
        }
    }

    public void bulidGameField() {
        TableRow.LayoutParams params = (TableRow.LayoutParams) gameField.getLayoutParams();
        margin = (int) (MainActivity.size.x * getFloatValueFromResourses(R.dimen.game_field_margin));
        params.setMargins(margin, margin, margin, 0);
        params.height=MainActivity.size.x - 2*margin;
        params.width=MainActivity.size.x - 2*margin;
        gameField.setLayoutParams(params);
    }

    public float getFloatValueFromResourses(int res) {
        TypedValue outValue = new TypedValue();
        getResources().getValue(res, outValue, true);
        return outValue.getFloat();
    }
}
