package com.example.gameoflifev3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public Button next_button;
    public Button reset_button;
    public GridView gridView;
    private ArrayList<Boolean> gridValue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridView);
        if(savedInstanceState == null) {
            gridValue = new ArrayList<Boolean>();
            for (int i=0; i < Integer.parseInt(getResources().getString(R.string.grid_size)); i++) {
                gridValue.add(true);
            }
        } else {
            gridValue = (ArrayList<Boolean>) savedInstanceState.getSerializable("gridState");
        }
        gridView.setAdapter(new GridAdapter(this, gridValue));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View imgView, int gridPosition, long id) {

                ImageView imageView = (ImageView) imgView;
                GridAdapter.GridHolder holder = (GridAdapter.GridHolder) imageView.getTag();

                if(holder.isAlive) {
                    imageView.setImageResource(R.drawable.ic_crop_square_orange_24dp);
                } else {
                    imageView.setImageResource(R.drawable.ic_crop_square_black_24dp);
                }
                holder.isAlive = !holder.isAlive;
                gridValue.remove(gridPosition);
                gridValue.add(gridPosition, holder.isAlive);
            }
        });

        next_button = (Button) findViewById(R.id.next);
        next_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                next();
            }
        });

        reset_button = (Button) findViewById(R.id.reset);

        reset_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Confirm");
                alertDialog.setMessage("Reset the grid?");
                alertDialog.setIcon(R.drawable.ic_warning_black_24dp);
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reset();
                    }
                });
                alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.create().show();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("gridState", gridValue);
        super.onSaveInstanceState(outState);
    }

    public int countAliveCells () {
        int countOfAliveCells = 0;

        for (int i = 0; i < gridView.getChildCount(); i++) {
            ImageView child = (ImageView) gridView.getChildAt(i);
            GridAdapter.GridHolder holder = (GridAdapter.GridHolder) child.getTag();

            if(!holder.isAlive) {
                countOfAliveCells ++;
            }
        }

        return countOfAliveCells;
    }

    public void drawGrid() {
        for (int i = 0; i < gridView.getChildCount(); i++) {
            ImageView child = (ImageView) gridView.getChildAt(i);
            GridAdapter.GridHolder holder = (GridAdapter.GridHolder) child.getTag();

            if(holder.isAlive) {
                child.setImageDrawable(getResources().getDrawable(R.drawable.ic_crop_square_black_24dp));
            } else {
                child.setImageDrawable(getResources().getDrawable(R.drawable.ic_crop_square_orange_24dp));
            }
        }
    }

    public void prepareGrid (int aliveCellCounter[]) {

        for (int j = 0; j < gridView.getChildCount(); j++) {

            ImageView child = (ImageView) gridView.getChildAt(j);
            GridAdapter.GridHolder holder = (GridAdapter.GridHolder) child.getTag();

            if(!holder.isAlive) {
                if((aliveCellCounter[j] < 2 ) || (aliveCellCounter[j] > 3)) {
                    holder.isAlive = !holder.isAlive;
                }
            } else {
                if(aliveCellCounter[j] == 3) {
                    holder.isAlive = !holder.isAlive;
                }
            }
        }
    }

    public int Ncount (int gridPosition) {

        ImageView neighbourChild;
        GridAdapter.GridHolder neighbourHolder;
        int aliveCellCounter = 0;

        if((gridPosition - 13) > 0) {
            neighbourChild = (ImageView) gridView.getChildAt(gridPosition - 13);
            neighbourHolder = (GridAdapter.GridHolder) neighbourChild.getTag();
            if(neighbourChild != null && !neighbourHolder.isAlive) {
                aliveCellCounter ++;
            }
        }

        if ((gridPosition -12 >= 0)) {
            neighbourChild = (ImageView) gridView.getChildAt(gridPosition - 12);
            neighbourHolder = (GridAdapter.GridHolder) neighbourChild.getTag();
            if(neighbourChild != null && !neighbourHolder.isAlive) {
                aliveCellCounter ++;
            }
        }

        if ((gridPosition - 11) >= 0) {
            neighbourChild = (ImageView) gridView.getChildAt(gridPosition - 11);
            neighbourHolder = (GridAdapter.GridHolder) neighbourChild.getTag();
            if(neighbourChild != null && !neighbourHolder.isAlive) {
                aliveCellCounter ++;
            }
        }

        if((gridPosition - 1) >= 0) {
            neighbourChild = (ImageView) gridView.getChildAt(gridPosition - 1);
            neighbourHolder = (GridAdapter.GridHolder) neighbourChild.getTag();
            if(neighbourChild != null && !neighbourHolder.isAlive) {
                aliveCellCounter ++;
            }
        }

        if((gridPosition + 1) <= 179) {
            neighbourChild = (ImageView) gridView.getChildAt(gridPosition + 1);
            neighbourHolder = (GridAdapter.GridHolder) neighbourChild.getTag();
            if(neighbourChild != null && !neighbourHolder.isAlive) {
                aliveCellCounter ++;
            }
        }

        if((gridPosition + 11) <= 179) {
            neighbourChild = (ImageView) gridView.getChildAt(gridPosition + 11);
            neighbourHolder = (GridAdapter.GridHolder) neighbourChild.getTag();
            if(neighbourChild != null && !neighbourHolder.isAlive) {
                aliveCellCounter ++;
            }
        }

        if((gridPosition + 12) <= 179) {
            neighbourChild = (ImageView) gridView.getChildAt(gridPosition + 12);
            neighbourHolder = (GridAdapter.GridHolder) neighbourChild.getTag();
            if(neighbourChild != null && !neighbourHolder.isAlive) {
                aliveCellCounter ++;
            }
        }

        if((gridPosition + 13) <= 179) {
            neighbourChild = (ImageView) gridView.getChildAt(gridPosition + 13);
            neighbourHolder = (GridAdapter.GridHolder) neighbourChild.getTag();
            if(neighbourChild != null && !neighbourHolder.isAlive) {
                aliveCellCounter ++;
            }
        }

        return aliveCellCounter ++;
    }

    public void next() {
        int aliveCellCounter[] = new int[180];
        for (int i= 0; i < gridView.getChildCount(); i++) {
            aliveCellCounter[i] = Ncount(i);
        }
        prepareGrid(aliveCellCounter);
        drawGrid();
    }

    public void reset() {
        for (int i = 0; i < gridView.getChildCount(); i++) {
            ImageView child = (ImageView) gridView.getChildAt(i);
            GridAdapter.GridHolder holder = (GridAdapter.GridHolder) child.getTag();

            child.setImageDrawable(getResources().getDrawable(R.drawable.ic_crop_square_black_24dp));
            holder.isAlive = true;
        }
    }
}
