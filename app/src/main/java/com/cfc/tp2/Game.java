package com.cfc.tp2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Vibrator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends AppCompatActivity {

    private GridLayout board = null;
    private Button buttonBack = null;
    private TextView countDownText; // timer display
    private TextView textBille=null;
    private List<Tile> tiles;//Tiles of the game
    private Tile firstTileSeleted = null; //represents the first Tile that the user click on
    private ImageView imageFondChrono=null;
    private GlobalData data;
    private int nbPawns=0, totalPawns = 0;//number of pawns left
    private int levelNumber = 0;//6 levels
    private int boardWidth = 0;//width of the board
    private long timeLeft = 60000;
    private MediaPlayer mediaPlayer, soundPlayer ; //Music player
    private Vibrator v;
    private Integer[] states = null;//states of the tile 0(not a tile) 1(empty) 2(pawn) 3(hilighted). Is one of the following level
    private Integer[] level1 = {0, 0, 2, 2, 2, 0, 0, 0, 0,
            0, 2, 2, 2, 2, 2, 0, 0, 0,
            2, 2, 2, 2, 2, 2, 2, 0, 0,
            2, 2, 2, 1, 2, 2, 2, 0, 0,
            2, 2, 2, 2, 2, 2, 2, 0, 0,
            0, 2, 2, 2, 2, 2, 0, 0, 0,
            0, 0, 2, 2, 2, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0
    };
    private Integer[] level2 = {0, 0, 0, 2, 2, 2, 0, 0, 0,
            0, 0, 0, 2, 2, 2, 0, 0, 0,
            0, 0, 0, 2, 2, 2, 0, 0, 0,
            2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 1, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2,
            0, 0, 0, 2, 2, 2, 0, 0, 0,
            0, 0, 0, 2, 2, 2, 0, 0, 0,
            0, 0, 0, 2, 2, 2, 0, 0, 0,
    };
    private Integer[] level3 = {0, 0, 2, 2, 2, 0, 0, 0, 0,
            0, 0, 2, 2, 2, 0, 0, 0, 0,
            0, 2, 2, 2, 2, 2, 0, 0, 0,
            2, 2, 2, 2, 2, 2, 2, 2, 0,
            2, 2, 2, 1, 2, 2, 2, 2, 0,
            2, 2, 2, 2, 2, 2, 2, 0, 0,
            0, 2, 2, 2, 2, 2, 0, 0, 0,
            0, 0, 2, 2, 2, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0
    };
    private Integer[] level4 = {0, 0, 2, 2, 2, 0, 0, 0, 0,
            0, 0, 2, 2, 2, 0, 0, 0, 0,
            2, 2, 2, 2, 2, 2, 2, 0, 0,
            2, 2, 2, 1, 2, 2, 2, 0, 0,
            2, 2, 2, 2, 2, 2, 2, 0, 0,
            0, 0, 2, 2, 2, 0, 0, 0, 0,
            0, 0, 2, 2, 2, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0
    };
    private Integer[] level5 = {0, 0, 0, 0, 2, 0, 0, 0, 0,
            0, 0, 0, 2, 2, 2, 0, 0, 0,
            0, 0, 2, 2, 2, 2, 2, 0, 0,
            0, 2, 2, 2, 2, 2, 2, 2, 0,
            2, 2, 2, 2, 1, 2, 2, 2, 2,
            0, 2, 2, 2, 2, 2, 2, 2, 0,
            0, 0, 2, 2, 2, 2, 2, 0, 0,
            0, 0, 0, 2, 2, 2, 0, 0, 0,
            0, 0, 0, 0, 2, 0, 0, 0, 0,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        //creation fragments list
        tiles = new ArrayList<>();
        // global data recovery
        data = GlobalData.getInstance();
        Intent intent = getIntent();
        states = null;
        if (intent != null) {
            levelNumber = intent.getIntExtra("levelNumber", 4);
        }
        switch (levelNumber) {
            case 1:
                totalPawns = nbPawns = 36;
                states = level1;
                break;
            case 2:
                totalPawns = nbPawns = 44;
                states = level2;
                break;
            case 3:
                totalPawns = nbPawns = 38;
                states = level3;
                break;
            case 4:
                totalPawns = nbPawns = 32;
                states = level4;
                break;
            case 5:
                totalPawns = nbPawns = 40;
                states = level5;
                break;
        }
        boardWidth = (int) Math.sqrt(states.length);
        board = (GridLayout) findViewById(R.id.board);
        board.setColumnCount(boardWidth);
        board.setRowCount(boardWidth);
        board = (GridLayout) findViewById(R.id.board);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        countDownText = (TextView) findViewById(R.id.countDownText);
        textBille = (TextView) findViewById((R.id.textViewBilles));
        imageFondChrono = (ImageView) findViewById((R.id.imageFondChrono));

       if(data.getMusique()=="adventures")
          mediaPlayer = MediaPlayer.create(this, R.raw.adventures);

       if(data.getMusique()=="wearehere")
            mediaPlayer = MediaPlayer.create(this, R.raw.wearehere);

       if(data.getMusique()=="timetwister")
               mediaPlayer = MediaPlayer.create(this, R.raw.timetwister);

       if(data.getMusique()=="game_music2")
            mediaPlayer = MediaPlayer.create(this, R.raw.game_music2);



       // mediaPlayer = MediaPlayer.create(this, R.raw.game_music);
        soundPlayer = MediaPlayer.create(this, R.raw.marble_on_wood);
        // Get instance of Vibrator from current Context
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        if(data.getModeGame()==1){
            //get time for the timer
            timeLeft = data.getTimeValue()*60000;
            //display the timer area
            countDownText.setVisibility(View.VISIBLE);
            imageFondChrono.setVisibility(View.VISIBLE);
            // Start service for the countdown
            startService(new Intent(this, BroadcastService.class));
        }else{
            //delete the timer area
            countDownText.setVisibility(View.GONE);
            imageFondChrono.setVisibility(View.GONE);
        }
        buildBoard();

    }

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent);
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        // set state of the tiles
        setTilesCreated();

    }

    @Override
    protected void onResume(){
        super.onResume();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Game.this, Levels.class);
                Game.this.startActivity(intent);
                finish();
            }
        });

        registerReceiver(br, new IntentFilter(BroadcastService.COUNTDOWN_BR));

        setDimMarble(); // Adapt marble size

        // Switch on the music
        if(data.getPlayMusic()){
            mediaPlayer.start(); //start music
            mediaPlayer.setLooping(true); //loop music
        }

        if(data.getModeGame()==1) {
            checkFinTimer();
        }

    }

    protected void onPause() {
        super.onPause();

        // Stop receiver
        unregisterReceiver(br);

        // Saving tiles state
        for (int i = 0; i < states.length; i++) {
            states[i] = tiles.get(i).state;
        }
        // Music on pause
        mediaPlayer.pause();
    }

    protected void onStop(){
        try {
            unregisterReceiver(br);
        } catch (Exception e) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop();
        mediaPlayer.stop();

    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, BroadcastService.class));

        super.onDestroy();
    }

    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            //get timer value
            timeLeft = intent.getLongExtra("countdown", 0);
            int minutes = (int)timeLeft / 60000;
            int secondes = (int)timeLeft%60000 /1000;

            // Display timer value
            String timeLeftText;
            timeLeftText = ""+minutes;
            timeLeftText += ":";
            if(secondes<10){
                timeLeftText+="0";
            }
            timeLeftText+=secondes;
            countDownText.setText(timeLeftText);

            // If the timer is finished it's the end of the game
            if(data.getModeGame()==1){
                checkFinTimer();
            }

        }
    }

    private void buildBoard() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Random random = new Random();
        boardWidth = (int) Math.sqrt(states.length);
        for (int i = 0; i < states.length; i++) {
            int x = i % boardWidth;
            int y = (int) Math.floor(i / boardWidth);
            board.setColumnCount(x + 1);
            board.setRowCount(y + 1);
            Tile newTile = new Tile();
            // Choose image of a marble
            newTile.setMarble(random.nextInt(6)+1);
            Bundle args = new Bundle();
            args.putInt("boardWidth", boardWidth);
            newTile.setArguments(args);
            tiles.add(newTile);
            ft.add(R.id.board, newTile);
        }
        ft.commit();

        textBille.setText(getString(R.string.Billes_enlevées)+ (totalPawns - nbPawns) + "\n" + getString(R.string.Billes_restantes) + nbPawns);
    }

    public void setTilesCreated() {
        for (int i = 0; i < states.length; i++) {
            int x = i % boardWidth;
            int y = (int) Math.floor(i / boardWidth);
            tiles.get(i).setTile(x, y, states[i]);
        }
    }

    public void onClickTile(Tile lastTileSelected) {
        //manages the tile selection actions.
        if (firstTileSeleted == null) {
            firstTileSeleted = lastTileSelected;
            highlightPossibilities();
        } else {
            removeHighLight();
            Tile tileJumped = isJumpable(firstTileSeleted, lastTileSelected);
            if (tileJumped != null) {
                if(data.getPlaySound()){
                    soundPlayer.start();
                }
                // Vibrate for 400 milliseconds
                if(data.getVibration()){
                    v.vibrate(60);
                }
                jumps(firstTileSeleted, tileJumped, lastTileSelected);
                nbPawns--;
                textBille.setText(getString(R.string.Billes_enlevées)+ (totalPawns - nbPawns) + "\n" + getString(R.string.Billes_restantes) + nbPawns);
                if (nbPawns == 0) {
                    Toast.makeText(this, "WIN", Toast.LENGTH_SHORT).show();
                }
                // If there is no more move possible
                if(checkFin())
                {
                    //if(data.getModeGame()==1){ startStopTimer();}  // If there is a timer, stop it
                    Intent intent2 = new Intent(Game.this, Score.class);
                    intent2.putExtra("pawns", nbPawns);
                    intent2.putExtra("levelNumber", levelNumber);
                    if(data.getModeGame()==1){
                        intent2.putExtra("tempsRestant", timeLeft);
                    }
                    Game.this.startActivity(intent2);
                    finish();
                }
            }
            removeHighLight();

            firstTileSeleted = null;
        }
    }

    private Tile getTileByXY(int x, int y) {
        //return the corresponding tile from the tile position
        int index = y * boardWidth + x;
        return tiles.get(index);
    }


    private Tile isJumpable(Tile tile1, Tile tile2) {
        //tile1 jumps to tile2
        // return the jumped tile, null if error
        if (tile1.state == 2 && tile2.state == 1) {
            if (tile2.x - tile1.x == 2 && tile2.y - tile1.y == 0 && getTileByXY(tile1.x + 1, tile2.y).state == 2) {//tile2 right from tile1
                return getTileByXY(tile1.x + 1, tile1.y);
            } else if (tile2.x - tile1.x == 0 && tile2.y - tile1.y == 2 && getTileByXY(tile1.x, tile1.y + 1).state == 2) {//tile2 below tile1
                return getTileByXY(tile1.x, tile1.y + 1);
            } else if (tile2.x - tile1.x == -2 && tile2.y - tile1.y == 0 && getTileByXY(tile1.x - 1, tile1.y).state == 2) {//tile2 left from tile1
                return getTileByXY(tile1.x - 1, tile1.y);
            } else if (tile2.x - tile1.x == 0 && tile2.y - tile1.y == -2 && getTileByXY(tile1.x, tile1.y - 1).state == 2) {//tile2 above of tile1
                return getTileByXY(tile1.x, tile1.y - 1);
            }
        }
        return null;
    }

    private void jumps(Tile tile1, Tile tile2, Tile tile3) {
        //change the tiles ressources, tile1 jumps above tile2 and lands on tile3
        tile1.state = 1;
        tile1.setRessource(tile1.state);
        tile2.state = 1;
        tile2.setRessource(tile2.state);
        tile3.state = 2;
        tile3.setMarble(tile1.getMarble());
        tile3.setRessource(tile3.state);
    }

    private void highlightPossibilities() {
        //change the ressources of the possibles tiles
        if (firstTileSeleted.x < boardWidth - 2 && firstTileSeleted.state == 2 && getTileByXY(firstTileSeleted.x + 1, firstTileSeleted.y).state == 2 && getTileByXY(firstTileSeleted.x + 2, firstTileSeleted.y).state == 1)
            getTileByXY(firstTileSeleted.x + 2, firstTileSeleted.y).setRessource(3);//right
        if (firstTileSeleted.y < boardWidth - 2 && firstTileSeleted.state == 2 && getTileByXY(firstTileSeleted.x, firstTileSeleted.y + 1).state == 2 && getTileByXY(firstTileSeleted.x, firstTileSeleted.y + 2).state == 1)
            getTileByXY(firstTileSeleted.x, firstTileSeleted.y + 2).setRessource(3);//below
        if (firstTileSeleted.x > 1 && firstTileSeleted.state == 2 && getTileByXY(firstTileSeleted.x - 1, firstTileSeleted.y).state == 2 && getTileByXY(firstTileSeleted.x - 2, firstTileSeleted.y).state == 1)
            getTileByXY(firstTileSeleted.x - 2, firstTileSeleted.y).setRessource(3);//left
        if (firstTileSeleted.y > 1 && firstTileSeleted.state == 2 && getTileByXY(firstTileSeleted.x, firstTileSeleted.y - 1).state == 2 && getTileByXY(firstTileSeleted.x, firstTileSeleted.y - 2).state == 1)
            getTileByXY(firstTileSeleted.x, firstTileSeleted.y - 2).setRessource(3);//above
    }

    private void removeHighLight() {
        for (int i = 0; i < tiles.size(); i++) {
            Tile curentTile = tiles.get(i);
            if (curentTile.state == 3) {
                tiles.get(i).setRessource(1);
            }
        }
    }

    private boolean checkFin() {
        boolean fin = true;
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            if (tile.x < boardWidth - 2 && tile.state == 2 && getTileByXY(tile.x + 1, tile.y).state == 2 && getTileByXY(tile.x + 2, tile.y).state == 1)
                fin = false;//right
            if (tile.y < boardWidth - 2 && tile.state == 2 && getTileByXY(tile.x, tile.y + 1).state == 2 && getTileByXY(tile.x, tile.y + 2).state == 1)
                fin = false;//below
            if (tile.x > 1 && tile.state == 2 && getTileByXY(tile.x - 1, tile.y).state == 2 && getTileByXY(tile.x - 2, tile.y).state == 1)
                fin = false;//left
            if (tile.y > 1 && tile.state == 2 && getTileByXY(tile.x, tile.y - 1).state == 2 && getTileByXY(tile.x, tile.y - 2).state == 1)
                fin = false;//above
        }

        return fin;
    }

    private void checkFinTimer()
    {
        // If the timer is finished it's the end of the game
        if(data.getTimerFinished() && data.getModeGame()==1){
            stopService(new Intent(this, BroadcastService.class));
            data.setTimerFinished(false);
            Intent intent2 = new Intent(Game.this, Score.class);
            intent2.putExtra("pawns", nbPawns);
            intent2.putExtra("levelNumber", levelNumber);
            Game.this.startActivity(intent2);

            finish();
        }
    }


    public void setDimMarble(){
        // Set marble dimension according to the number of marbles
        for(int i=0;i<tiles.size();i++)
        {
            switch (levelNumber) {
                case 1:
                    tiles.get(i).setLvl1Dim();
                    break;
                case 2:
                    tiles.get(i).setLvl2Dim();
                    break;
                case 3:
                    tiles.get(i).setLvl3Dim();
                    break;
                case 4:
                    tiles.get(i).setLvl1Dim();
                    break;
                case 5:
                    tiles.get(i).setLvl2Dim();
                    break;
            }
        }
    }

}
