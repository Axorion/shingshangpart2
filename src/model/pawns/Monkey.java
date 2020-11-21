package model.pawns;

import model.board.Board;

public class Monkey extends Pawn {
    private static final int MAX_MOVE_RANGE = 2;
    private static final int height = 1;

    public Monkey(int teamId)throws IllegalArgumentException {
        super(teamId, Monkey.height, Monkey.MAX_MOVE_RANGE);
    }

    @Override
    public String toString() {
        String color;
        if (teamId == Board.FIRST_TEAM_ID) {
            color = "\u001B[37m";
        } else {
            color = "\u001B[30m";
        }
        return "\u001B[45m" + color + "| 1 |\u001B[0m";
    }
}
