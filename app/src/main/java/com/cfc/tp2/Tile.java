package com.cfc.tp2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Tile extends Fragment {

    public int x = 0, y = 0; //x is right and y down
    public int state = 0; //state 0 not a tile  1 empty tile  2 tile with pawn 3 tile hilighted
    public ImageView imageTile;
    private boolean tilesCreated = false;
    private int boardWidth = 0;
    private Tile tile = null;
    private Counter counter;
    private int marble, size;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            boardWidth = savedInstanceState.getInt("boardWidth");
        }
        boardWidth = getArguments().getInt("boardWidth");
        counter = Counter.getInstance();
        tile = this;
        View view =  inflater.inflate(R.layout.tile, container, false);
        imageTile = (ImageView) view.findViewById(R.id.imageTiles);
        size = imageTile.getLayoutParams().height;
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "x:" + x + "  y:" + y + "  state:" + state, Toast.LENGTH_LONG).show();
                //getContext().
                ((Game) getActivity()).onClickTile(tile);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        //Needed because i can not get the children of the associated view before the fragment is fully created
        super.onActivityCreated(savedInstanceState);
        imageTile = (ImageView)getView().findViewById(R.id.imageTiles);
        if(!tilesCreated){
            counter.increment();
            if(counter.getCount() == boardWidth*boardWidth){
                tilesCreated = true;
            }
        }
        if(tilesCreated) {
            ((Game) getActivity()).setTilesCreated();
        }
    }

    public void setTile(int x, int y, int state) {
        this.x = x;
        this.y = y;
        setRessource(state);
    }

    public void setMarble(int _marble){
        marble=_marble;
    }

    public int getMarble(){
        return marble;
    }

    public void setRessource(int state) {
        //set the ressource and the state
        this.state = state;
        if (state == 0) {//debugOnly
            imageTile.setImageResource(R.drawable.notatile);
        } else if (state == 1) {
            imageTile.setImageResource(R.drawable.empty_marble);
        } else if(state == 2){
            switch (marble){
                case 1:
                    imageTile.setImageResource(R.drawable.blue_marble);
                    break;
                case 2:
                    imageTile.setImageResource(R.drawable.green_marble);
                    break;
                case 3:
                    imageTile.setImageResource(R.drawable.yellow_marble);
                    break;
                case 4:
                    imageTile.setImageResource(R.drawable.turquoise_marble);
                    break;
                case 5:
                    imageTile.setImageResource(R.drawable.red_marble);
                    break;
                case 6:
                    imageTile.setImageResource(R.drawable.purple_marble);
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + marble);
            }
        }
        else{
            imageTile.setImageResource(R.drawable.yellow_empty);
        }
    }

    public void setLvl1Dim(){
        imageTile.getLayoutParams().height = size;
        imageTile.getLayoutParams().width = size;
        imageTile.requestLayout();
    }

    public void setLvl2Dim(){
        imageTile.getLayoutParams().height = (int)(size * 0.8);
        imageTile.getLayoutParams().width = (int)(size * 0.8);
        imageTile.requestLayout();
    }

    public void setLvl3Dim(){
        imageTile.getLayoutParams().height = (int)(size * 0.9);
        imageTile.getLayoutParams().width = (int)(size * 0.9);
        imageTile.requestLayout();
    }
}
