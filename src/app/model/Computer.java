package app.model;

import app.vendor.Constant;

public class Computer {

    private int bestTurn;

    public Computer(){
        this.bestTurn = -1;
    }

    public int getBestTurn() {
        return bestTurn;
    }

    public void setBestTurn(int bestTurn) {
        this.bestTurn = bestTurn;
    }

    public void bestMove(Position position) {
        Integer mm = null;
        Integer[] possibleMoves = position.possibleMoves();
        for(Integer idx : possibleMoves) {
            Integer value = position.move(idx).minimax();
            if(mm == null || position.getTurn() == Constant.X && mm < value || position.getTurn() == Constant.O && value < mm) {
                mm = value;
                bestTurn = idx;
            }
            //System.out.println(value);
        }
    }
}
