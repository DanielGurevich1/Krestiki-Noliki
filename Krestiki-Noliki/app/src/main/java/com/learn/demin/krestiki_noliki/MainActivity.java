package com.learn.demin.krestiki_noliki;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    SharedPreferences sPref;
    public static Game game=new Game();
    private Display display;
    public static Point size = new Point();

    Button player1Btn,player2Btn;

    View.OnClickListener chooseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.player1) {
                game.setPlayersCount(1);
                game.getPlayers()[1]=new Computer();
            }
            else {
                game.setPlayersCount(2);
                game.getPlayers()[1]=new Human();
            }
            game.getPlayers()[1].setIsX(!game.getPlayers()[0].isX);
            Intent intent = new Intent(MainActivity.this, GameField.class);
            startActivity(intent);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mainLayoutBuild();

        player1Btn = (Button) findViewById(R.id.player1);
        player2Btn = (Button) findViewById(R.id.players2);
        player1Btn.setOnClickListener(chooseListener);
        player2Btn.setOnClickListener(chooseListener);

        sPref=getSharedPreferences("mySettings.xml",MODE_PRIVATE);

        game.getPlayers()[0].setIsX(sPref.getBoolean(Settings.SAVED_SYMBOL_X, true));
        game.setWinSize(sPref.getInt(Settings.SAVED_WIN_SIZE, 5));
        game.setFieldSize(Integer.parseInt(sPref.getString(Settings.SAVED_FIELD_SIZE, "10")));
        game.create();
    }



    public void mainLayoutBuild() {

        display = getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int buttonHeight, buttonWidth, button1Margin, button2Margin;

        Button button1 = (Button) findViewById(R.id.player1);
        Button button2 = (Button) findViewById(R.id.players2);

        RelativeLayout.LayoutParams btn1Params = (RelativeLayout.LayoutParams) button1.getLayoutParams();
        RelativeLayout.LayoutParams btn2Params = (RelativeLayout.LayoutParams) button2.getLayoutParams();

        buttonHeight =(int) (size.y * getFloatValueFromResourses(R.dimen.player_button_height));
        buttonWidth = (int) (size.x * getFloatValueFromResourses(R.dimen.player_button_width));
        button1Margin = (int) (size.y * getFloatValueFromResourses(R.dimen.player1_margin));
        button2Margin = (int) (size.y * getFloatValueFromResourses(R.dimen.players2_margin));

        button1.setHeight(buttonHeight);
        button2.setHeight(buttonHeight);
        button1.setWidth(buttonWidth);
        button2.setWidth(buttonWidth);

        btn1Params.setMargins(0,button1Margin, 0, 0);
        btn2Params.setMargins(0,button2Margin, 0, 0);

        button1.setLayoutParams(btn1Params);
        button2.setLayoutParams(btn2Params);

    }

    public float getFloatValueFromResourses(int res) {
        float value;
        TypedValue outValue = new TypedValue();
        getResources().getValue(res, outValue, true);
        return value = outValue.getFloat();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent=new Intent(this,Settings.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }
}
