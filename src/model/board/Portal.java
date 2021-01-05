package model.board;

public class Portal extends Square{
    private int teamId;
    Portal(Portal square){
        super(square);
        this.teamId = square.teamId;
    }

    Portal(int x, int y, int teamId){
        super(x,y);
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        String color;
        if (teamId == Board.FIRST_TEAM_ID){
            color = "\u001B[41m\u001B[30m";
        }else{
            color = "\u001B[44m\u001B[30m";
        }
        return hasPawn() ?
                color+"|"+getPawn().toString()+color+"|\u001B[0m"
                :
                color+"|   |\u001B[0m";
    }

	public int getTeamId() {
		return teamId;
	}
}
