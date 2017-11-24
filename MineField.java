package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MineField {
    private static int DEFAULT_ROW = 16;
    private static int DEFAULT_COL = 30;
    private static int DEFAULT_MINES = 99;
    private static int SAFEZONE = 1;

    private int _row;
    private int _col;
    private int _mineNum;

    private int[][] _map;
    private boolean[][] _clearArea;

    /**
     * Generate a mine field using default data
     */
    public MineField(){
        _row = DEFAULT_ROW;
        _col = DEFAULT_COL;
        _mineNum = DEFAULT_MINES;
        generateField();
    }

    /**
     * Allow custom mine fields
     */
    public MineField(int row, int col, int mineNum)throws MineNumExceedException{
        _row = row;
        _col = col;
        _mineNum = mineNum;
        if(mineNum > _row *_col){
            throw new MineNumExceedException();
        }else {
            generateField();
        }
    }

    private void generateField(){
        int realRow = _row + 2 * SAFEZONE;
        int realCol = _col + 2 * SAFEZONE;
        _map = new int[realRow][realCol];
        _clearArea = new boolean[realRow][realCol];
        for (int[] row : _map) {
            Arrays.fill(row, 0);
        }
        for (boolean[] row : _clearArea) {
           Arrays.fill(row, false);
        }
        plantMines();

        /*
        //Print out the field
        for (int i = 0; i < _map.length; i++) {
            for (int col = 0; col < _map[0].length; col++) {
                if (_map[i][col] == -1){
                    System.out.print("*" + "\t");
                }else{
                    System.out.print(_map[i][col] + "\t");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
        */
    }

    /**
     * Randomly set mines in the map
     */
    private void plantMines(){
        ArrayList<Integer> field = new ArrayList<>();
        for (int i = 0; i < _row * _col; i++){
            field.add(i);
        }
        Collections.shuffle(field);
        List<Integer> mineList = field.subList(0, _mineNum);
        for (int m : mineList){
            int row = m / _col + SAFEZONE;
            int col = m % _col  + SAFEZONE;
            _map[row][col] = -1;
            calcMap(row, col);
        }
    }

    /**
     * Generate the number on every grid
     */
    private void calcMap(int row, int col){
        if (checkout(row-1, col-1)){
            _map[row-1][col-1] ++;
        }
        if (checkout(row-1, col)){
            _map[row-1][col] ++;
        }
        if (checkout(row-1, col+1)){
            _map[row-1][col+1] ++;
        }
        if (checkout(row, col-1)){
            _map[row][col-1] ++;
        }
        if (checkout(row, col+1)){
            _map[row][col+1] ++;
        }
        if (checkout(row+1, col-1)){
            _map[row+1][col-1] ++;
        }
        if (checkout(row+1, col)){
            _map[row+1][col] ++;
        }
        if (checkout(row+1, col+1)){
            _map[row+1][col+1] ++;
        }
    }

    /**
     * Returns true when the input grid is within the field && is not a mine. False otherwise
     */
    private boolean checkout(int row, int col){
        if ((row > 0) && (row < _map.length - 1) && (col > 0) && (col < _map[0].length - 1)){
            return _map[row][col] != -1;
        }
        return false;
    }




    public ArrayList<int[]> ripple(int row, int col){
        if(_map[row][col]==-1){
            return null;
        }else{
            ArrayList<int[]> rippleList = new ArrayList<>();
            if (!_clearArea[row][col] || _map[row][col] == 0){
                _clearArea[row][col] = true;
                int[] source = {row, col};
                rippleList.add(source);
                ArrayList<int[]> topr = ripple(row-1, col-1);
                ArrayList<int[]> top = ripple(row-1, col);
                ArrayList<int[]> topl = ripple(row-1, col+1);
                ArrayList<int[]> l = ripple(row, col-1);
                ArrayList<int[]> r = ripple(row, col+1);
                ArrayList<int[]> botl = ripple(row+1, col-1);
                ArrayList<int[]> bot = ripple(row+1, col);
                ArrayList<int[]> botr = ripple(row+1, col+1);
                rippleList.addAll(topr);
                rippleList.addAll(top);
                rippleList.addAll(topl);
                rippleList.addAll(l);
                rippleList.addAll(r);
                rippleList.addAll(botl);
                rippleList.addAll(bot);
                rippleList.addAll(botr);
            }
            return rippleList;
        }
    }









    /*
        Getters
     */
    public int getNum(int row, int col){
        return _map[row][col];
    }

    public int getRow(){
        return _row;
    }

    public int getCol(){
        return _col;
    }

    public int getMineNum(){
        return _mineNum;
    }

}
