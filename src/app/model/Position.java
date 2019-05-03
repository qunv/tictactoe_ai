package app.model;

import app.vendor.Constant;

import java.util.LinkedList;
import java.util.List;

public class Position {
    private char[] board;
    private char turn;
    private int dim = Constant.DIM;

    public char[] getBoard() {
        return board;
    }

    public void setBoard(char[] board) {
        this.board = board;
    }

    public char getTurn() {
        return turn;
    }

    public void setTurn(char turn) {
        this.turn = turn;
    }

    public Position() {
        this.board = Constant.BOARD;
        this.turn = Constant.X;
    }

    public Position(char[] board, char turn) {
        this.board = board;
        this.turn = turn;
    }

    public String toString() {
        return new String(board);
    }

    public Position move(int idx) {
        char[] newboard = board.clone();
        newboard[idx] = turn;
        return new Position(newboard, turn == Constant.X ? Constant.O : Constant.X);
    }

    public boolean duplicationTurn(int idx){
        return board[idx] == ' ' ? false : true;
    }

    public Position reset(){
        return new Position();
    }

    public Integer[] possibleMoves() {
        List<Integer> list = new LinkedList<Integer>();
        for(int i = 0; i < board.length; i++) {
            if(board[i] == ' ') {
                list.add(i);
            }
        }
        Integer[] array = new Integer[list.size()];
        list.toArray(array);
        return array;
    }

    private boolean possibleVictories(char turn, int start, int step) {
        for(int i = 0; i < 3; i++) {
            // int x = start + step*i;
            // char y = board[start + step*i];
            if(board[start + step*i] != turn) {
                return false;
            }
        }
        return true;
    }

    public boolean win(char turn) {
        //win_line(turn, i, dim) check win in row
        //win_line(turn, i*dim, 1) check win in column
        //win_line(turn, dim-1, dim-1) check win in the diagonal line start index 2
        //win_line(turn, 0, dim+1) check win in the diagonal line start index 0
        for(int i = 0; i < dim; i++) {
            if(possibleVictories(turn, i*dim, 1) || possibleVictories(turn, i, dim)) {
                return true;
            }
        }
        if(possibleVictories(turn, dim-1, dim-1) || possibleVictories(turn, 0, dim+1)) {
            return true;
        }
        return false;
    }

    public int minimax() {
        if(win(Constant.X)) {
            return 100;
        }
        if(win(Constant.O)) {
            return -100;
        }
        if(possibleMoves().length == 0) return 0;
        Integer mm = null;
        Integer[] possibleMoves = possibleMoves();
        for(Integer idx : possibleMoves) {
            Integer value = move(idx).minimax();
            //System.out.println(new String(board));
            if(mm == null || turn == Constant.X && mm < value || turn == Constant.O && value < mm) {
                mm = value;
            }
        }
        int mmUpdated;
        mmUpdated = mm + (turn == Constant.X ? -1 : 1);
        return mmUpdated;
    }

//    public int bestMove() {
//        Integer mm = null;
//        int best = -1;
//        Integer[] possibleMoves = possibleMoves();
//        for(Integer idx : possibleMoves) {
//            Integer value = move(idx).minimax();
//            if(mm == null || turn == Constant.X && mm < value || turn == Constant.O && value < mm) {
//                mm = value;
//                best = idx;
//            }
//            //System.out.println(value);
//        }
//        return best;
//    }

    public boolean gameEnd() {
        return win(Constant.X) || win(Constant.O) || possibleMoves().length == 0;
    }
}
