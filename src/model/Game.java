package model;

import exceptions.*;
import model.board.Board;
import model.board.Square;
import model.pawns.Dragon;
import model.pawns.IPawn;
import model.player.Player;
import movements.Movements;
import movements.MovementsInformation;
import observers.PlayerScoreObserver;
import subjects.EndGameSubject;
import subjects.PlayerRoundSubject;

public class Game implements EndGameSubject, PlayerRoundSubject {
    private int round = 0;
    private Player firstPlayer;
    private Player secondPlayer;
    private Board board;

    public Game(){
        this.firstPlayer = new Player(Board.FIRST_TEAM_ID);
        this.secondPlayer = new Player(Board.SECOND_TEAM_ID);
        this.board = new Board(new Player[]{firstPlayer, secondPlayer});
    }

    public void start(){
        this.reset();
        this.notifyPlayerForNewRound();
    }

    public void reset(){
        firstPlayer.resetPoint();
        secondPlayer.resetPoint();
        this.board.reset();
        this.round = 0;
    }

    /**
     * Déplacement du premier joueur
     * @param skip Booléen pour passer son tour
     * @param fromSquareCoords index 0 = x, index 1 = y
     * @param toSquareCoords index 0 = x, index 1 = y
     * @throws NoPawnException
     * @throws WrongCoordsException
     * @throws WrongMovementException
     * @throws MoveEnemyPawnException
     * @throws WrongRoundException
     * @throws OtherPawnAlreadyMovingException
     * @throws PawnAlreadyMovedInRoundException
     * @throws PlayerNotPlayingException
     */
    public void firstPlayerMove(boolean skip, int[] fromSquareCoords, int[] toSquareCoords) throws NoPawnException, WrongCoordsException, WrongMovementException, MoveEnemyPawnException, WrongRoundException, OtherPawnAlreadyMovingException, PawnAlreadyMovedInRoundException, PlayerNotPlayingException {
        this.playerMove(this.firstPlayer, skip, fromSquareCoords, toSquareCoords);
    }

    /**
     * Déplacement du second joueur
     * @param skip Booléen pour passer son tour
     * @param fromSquareCoords index 0 = x, index 1 = y
     * @param toSquareCoords index 0 = x, index 1 = y
     * @throws NoPawnException
     * @throws WrongCoordsException
     * @throws WrongMovementException
     * @throws MoveEnemyPawnException
     * @throws WrongRoundException
     * @throws OtherPawnAlreadyMovingException
     * @throws PawnAlreadyMovedInRoundException
     * @throws PlayerNotPlayingException
     */
    public void secondPlayerMove(boolean skip, int[] fromSquareCoords, int[] toSquareCoords) throws NoPawnException, WrongCoordsException, WrongMovementException, MoveEnemyPawnException, WrongRoundException, PlayerNotPlayingException, OtherPawnAlreadyMovingException, PawnAlreadyMovedInRoundException {
        playerMove(this.secondPlayer, skip, fromSquareCoords, toSquareCoords);
    }

    /**
     * Déplacement d'un joueur
     * @param player joueur à déplacer
     * @param skip
     * @param fromSquareCoords
     * @param toSquareCoords
     * @throws NoPawnException
     * @throws WrongCoordsException
     * @throws WrongMovementException
     * @throws MoveEnemyPawnException
     * @throws WrongRoundException
     * @throws PlayerNotPlayingException
     * @throws OtherPawnAlreadyMovingException
     * @throws PawnAlreadyMovedInRoundException
     */
    private void playerMove(Player player, boolean skip, int[] fromSquareCoords, int[] toSquareCoords) throws NoPawnException, WrongCoordsException, WrongMovementException, MoveEnemyPawnException, WrongRoundException, PlayerNotPlayingException, OtherPawnAlreadyMovingException, PawnAlreadyMovedInRoundException {
        if (player.getTeamId() == Board.FIRST_TEAM_ID && round % 2 != 0){
            throw new WrongRoundException("Ce n'est pas au premier joueur de jouer");
        }else if(player.getTeamId() == Board.SECOND_TEAM_ID && round % 2 == 0){
            throw new WrongRoundException("Ce n'est pas au second joueur de jouer");
        }

        boolean newRound = false;
        if (!skip){
            MovementsInformation movementsInformation = this.board.movePawn(player, fromSquareCoords, toSquareCoords);
            Square capturedPawnSquare = movementsInformation.getCapturedPawnSquare();
            if (movementsInformation.getMovements() == Movements.WALK){
                newRound = true;
            }else if (capturedPawnSquare != null){
                   capturePawn(capturedPawnSquare);
                   this.board.finishMovePawn();
                   checkForWinner();
            }
        }else {
            newRound = true;
        }

        if (newRound){
            this.board.finishMovePawn();
            this.board.resetMovedPawnStatus();
            this.round++;
        }
        this.notifyPlayerForNewRound();
    }

    private void checkForWinner(){
        int winnerID;
        if (this.firstPlayer.getScore() <= 0){
            winnerID = Board.SECOND_TEAM_ID;
        }else if (this.secondPlayer.getScore() <= 0) {
            winnerID = Board.FIRST_TEAM_ID;
        }else{
            winnerID = this.board.winner();
        }

        if (winnerID != Board.NO_TEAM_ID){
            notifyEndGameObservers(winnerID);
        }
    }

    private void notifyPlayerForNewRound(){
        try{
            if (this.round % 2 == 0){
                this.notifyFirstPlayerRoundObservers((Board) this.board.clone());
            }else{
                this.notifySecondPlayerRoundObservers((Board) this.board.clone());
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private void capturePawn(Square square){
        IPawn pawn = square.getPawn();
        if (pawn instanceof Dragon){
            if (pawn.getTeamId() == this.firstPlayer.getTeamId()){
                this.firstPlayer.loseAPoint();
            }else{
                this.secondPlayer.loseAPoint();
            }
        }
        this.board.capturePawn(square);
    }

    public void addFirstPlayerScoreObservers(PlayerScoreObserver observer){
        firstPlayer.addPlayerScoreObservers(observer);
    }

    public void removeFirstPlayerScoreObservers(PlayerScoreObserver observer){
        firstPlayer.removePlayerScoreObservers(observer);
    }

    public void addSecondPlayerScoreObservers(PlayerScoreObserver observer){
        secondPlayer.addPlayerScoreObservers(observer);
    }

    public void removeSecondPlayerScoreObservers(PlayerScoreObserver observer){
        secondPlayer.removePlayerScoreObservers(observer);
    }

    public void printBoard(){
        System.out.println(this.board.toString());
    }

    public void printAvailableMovements(int[] fromSquare) throws WrongCoordsException {
        System.out.println(this.board.toStringMovements(fromSquare));
    }
}
