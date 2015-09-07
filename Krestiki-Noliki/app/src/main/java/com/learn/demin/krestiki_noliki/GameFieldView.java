package com.learn.demin.krestiki_noliki;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

/**
 * Created by AleDemin on 16.08.2015.
 */
public class GameFieldView extends View {

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint = new Paint();
    private float mScaleFactor;

    private GestureDetector scrollGestureDetector;

    int margin = (int) (MainActivity.size.x * getFloatValueFromResourses(R.dimen.game_field_margin));
    float canvasSize = (MainActivity.size.x - 2 * margin);

    public GameFieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bitmap = Bitmap.createBitmap(MainActivity.size.x, MainActivity.size.x, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        mScaleFactor = 1f;
        paint.setColor(getResources().getColor(R.color.gamefield_color));
        paint.setStrokeWidth(MainActivity.size.y * getFloatValueFromResourses(R.dimen.game_field_line_width) / MainActivity.game.getFieldSize());

        canvas.drawRect(0, 0, MainActivity.size.x - 2 * margin, MainActivity.size.x - 2 * margin, paint);

        paint.setColor(getResources().getColor(R.color.gamefield_line_color));


        for (int i = 0; i < MainActivity.game.getFieldSize() + 1; i++) {
            canvas.drawLine(0, i * canvasSize / MainActivity.game.getFieldSize(), canvasSize, i * canvasSize / MainActivity.game.getFieldSize(), paint);
            canvas.drawLine(i * canvasSize / MainActivity.game.getFieldSize(), 0, i * canvasSize / MainActivity.game.getFieldSize(), canvasSize, paint);
        }


        scrollGestureDetector = new GestureDetector(context, new MyGestureListener());
    }

    public void onDraw(Canvas canvas) {
        canvas.scale(mScaleFactor, mScaleFactor);//зумируем канвас
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    public float getFloatValueFromResourses(int res) {
        TypedValue outValue = new TypedValue();
        getResources().getValue(res, outValue, true);
        return outValue.getFloat();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scrollGestureDetector.onTouchEvent(event);
        return true;
    }


    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        public boolean onSingleTapConfirmed(MotionEvent event) {
            //получаем координаты €чейки, по которой тапнули
            int cellX = (int) ((event.getX() + getScrollX()) / mScaleFactor / (canvasSize / MainActivity.game.getFieldSize()));
            int cellY = (int) ((event.getY() + getScrollY()) / mScaleFactor / (canvasSize / MainActivity.game.getFieldSize()));
            if (!MainActivity.game.isOver() && MainActivity.game.getField()[cellX][cellY].getFilled() == 0) {
                drawSymbol(MainActivity.game.getTurn(), cellX, cellY);
                MainActivity.game.getTurn().doMove(cellX, cellY);
                if (MainActivity.game.win()) {
                    drawWin();
                }
                invalidate();
                if (MainActivity.game.getPlayers()[1] instanceof Computer && !MainActivity.game.win()) {
                    cellX = MainActivity.game.getPlayers()[1].strategy().getX();
                    cellY = MainActivity.game.getPlayers()[1].strategy().getY();
                    drawSymbol(MainActivity.game.getTurn(), cellX, cellY);
                    MainActivity.game.getTurn().doMove(cellX, cellY);
                    if (MainActivity.game.win()) {
                        drawWin();
                    }
                    invalidate();
                }
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //не даем канвасу показать кра€ по горизонтали
            if (getScrollX() + distanceX <= canvasSize*(mScaleFactor-1) && getScrollX() + distanceX >= 0) {
                scrollBy((int) distanceX, 0);
            }
            //не даем канвасу показать кра€ по вертикали
            if (getScrollY() + distanceY <= canvasSize*(mScaleFactor-1) && getScrollY() + distanceY >= 0) {
                scrollBy(0, (int) distanceY);
            }

            return true;
        }
    }

    public void drawSymbol(Player player, int x, int y) {
        if (player.isX()) drawX(x, y);
        else drawO(x, y);
    }

    public void drawX(int x, int y) {
        Paint p = new Paint();
        p.setColor(getResources().getColor(R.color.x_color));
        float cellSize = (canvasSize / MainActivity.game.getFieldSize());
        p.setStrokeWidth(cellSize * getFloatValueFromResourses(R.dimen.inside_cell_symbol_width));
        float cell_margin = cellSize * getFloatValueFromResourses(R.dimen.inside_cell_symbol_margin);
        canvas.drawLine(cellSize * x + cell_margin, cellSize * y + cell_margin, cellSize * (x + 1) - cell_margin, cellSize * (y + 1) - cell_margin, p);
        canvas.drawLine(cellSize * (x + 1) - cell_margin, cellSize * y + cell_margin, cellSize * x + cell_margin, cellSize * (y + 1) - cell_margin, p);
    }

    public void drawO(int x, int y) {
        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(getResources().getColor(R.color.o_color));
        float cellSize = (canvasSize / MainActivity.game.getFieldSize());
        p.setStrokeWidth(cellSize * getFloatValueFromResourses(R.dimen.inside_cell_symbol_width));
        float cell_margin = cellSize * getFloatValueFromResourses(R.dimen.inside_cell_symbol_margin) + cellSize * getFloatValueFromResourses(R.dimen.inside_cell_symbol_width);
        canvas.drawCircle(cellSize * x + cellSize / 2, cellSize * y + cellSize / 2, (cellSize - cell_margin) / 2, p);
    }

    public void cancelDraw() {
        float cellSize = (canvasSize / MainActivity.game.getFieldSize());
        float width=cellSize * getFloatValueFromResourses(R.dimen.inside_cell_symbol_width);
        int x = MainActivity.game.findLastSquare().getX();
        int y = MainActivity.game.findLastSquare().getY();
        Paint p = new Paint();
        p.setColor(getResources().getColor(R.color.gamefield_color));
        canvas.drawRect(cellSize * x + width / 2, cellSize * y + width / 2, cellSize * (x + 1) - width / 2, cellSize * (y + 1) - width / 2, p);
        invalidate();
    }

    public void drawWin() {
        Paint p = new Paint();

        if (MainActivity.game.getTurn().isX())
            p.setColor(getResources().getColor(R.color.win_color));
        else p.setColor(getResources().getColor(R.color.win_color));
        float cellSize = (canvasSize / MainActivity.game.getFieldSize());
        p.setStrokeWidth(cellSize * getFloatValueFromResourses(R.dimen.win_line_width));

        for (int i = 0; i < MainActivity.game.getFieldSize(); i++) {
            for (int j = 0; j < MainActivity.game.getFieldSize(); j++) {
                switch (MainActivity.game.getField()[i][j].getWin()) {
                    case 1:
                        canvas.drawLine(cellSize * (i + 0.5f), cellSize * j, cellSize * (i + 0.5f), cellSize * (j + 1), p);
                        break;
                    case 2:
                        canvas.drawLine(cellSize * i, cellSize * (j + 0.5f), cellSize * (i + 1), cellSize * (j + 0.5f), p);
                        break;
                    case 3:
                        canvas.drawLine(cellSize * i, cellSize * j, cellSize * (i + 1), cellSize * (j + 1), p);
                        break;
                    case 4:
                        canvas.drawLine(cellSize * (i + 1), cellSize * j, cellSize * i, cellSize * (j + 1), p);

                }

            }
        }
    }

    public void zoomPlus() {
        float zoomScale = getFloatValueFromResourses(R.dimen.zoom_scale);
        if (mScaleFactor * zoomScale < MainActivity.game.getFieldSize() / getFloatValueFromResourses(R.dimen.zoom_max_scale)) {
            mScaleFactor *= zoomScale;
            scrollTo((int) (canvasSize * (mScaleFactor - 1) / 2), (int) (canvasSize * (mScaleFactor - 1) / 2));
            invalidate();
        }
    }

    public void zoomMinus() {
        float zoomScale = getFloatValueFromResourses(R.dimen.zoom_scale);
        if (mScaleFactor / zoomScale >= 1) {
            mScaleFactor /= zoomScale;
            scrollTo((int) (canvasSize * (mScaleFactor - 1) / 2), (int) (canvasSize * (mScaleFactor - 1) / 2));
            invalidate();
        }
    }
}
