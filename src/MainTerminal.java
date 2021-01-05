/*import exceptions.*;
import model.game.Game;
import model.board.Board;
import observers.EndGameObserver;
import observers.PlayerRoundObserver;
import observers.PlayerScoreObserver;

import java.util.Scanner;

public class MainTerminal {
    public static void main(String[] args) {
        try{
            Game game = new Game();
            FirstPlayerObserver firstPlayerObserver = new FirstPlayerObserver(game);
            SecondPlayerObserver secondPlayerObserver = new SecondPlayerObserver(game);
            game.addFirstPlayerRoundObservers(firstPlayerObserver);
            game.addFirstPlayerScoreObservers(firstPlayerObserver);
            game.addEndGameObservers(firstPlayerObserver);
            game.addSecondPlayerRoundObservers(secondPlayerObserver);
            game.addSecondPlayerScoreObservers(secondPlayerObserver);
            game.addEndGameObservers(secondPlayerObserver);
            game.start();
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public static int[] readCoords(){
        Scanner scanner = new Scanner( System.in );
        System.out.print("\n0 pour passer\nX de départ:");
        int coordsDepX = scanner.nextInt();
        System.out.print("\n0 pour passer\nY de départ:");
        int coordsDepY = scanner.nextInt();
        System.out.print("\n0 pour passer\nX d'arriver:");
        int coordsArrX = scanner.nextInt();
        System.out.print("\n0 pour passer\nY d'arriver:");
        int coordsArrY = scanner.nextInt();
        return new int[]{coordsDepX, coordsDepY, coordsArrX, coordsArrY};
    }

    public static class FirstPlayerObserver implements PlayerRoundObserver, PlayerScoreObserver, EndGameObserver{
        private Game game;
        FirstPlayerObserver(Game game){
            this.game = game;
        }
        @Override
        public void nextRoundHandler(Board board) {
            boolean repeat = true;
            while (repeat){
                repeat = false;
                game.printBoard();
                int[] coords = readCoords();
                try{
                    if (coords[0] == 0 && coords[1] == 0 && coords[2] == 0 && coords[3] == 0){
                        game.firstPlayerMove(true,null, null);
                    }else{
                        System.out.println(board.toStringMovements(new int[]{coords[0], coords[1]}));
                        game.firstPlayerMove(false,new int[]{coords[0], coords[1]}, new int[]{coords[2], coords[3]});
                    }
                }catch (MoveEnemyPawnException | NoPawnException | WrongCoordsException | WrongMovementException | PawnAlreadyMovedInRoundException | OtherPawnAlreadyMovingException | PlayerNotPlayingException e){
                    repeat = true;
                    System.out.println(e.getMessage());
                } catch (WrongRoundException | GameOverException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        @Override
        public void newScoreHandler(int score) {
            System.out.println("First NOUVEAU SCORE: "+score);
        }


        @Override
        public void endGameHandler(int teamWinnerID) {
            System.out.println("FIN de partie");
            if (teamWinnerID == Board.SECOND_TEAM_ID){
                System.out.println("Premier joueur tu es PERDANT");
            }else if(teamWinnerID == Board.FIRST_TEAM_ID){
                System.out.println("Premier joueur tu es GAGNANT");
            }
        }
    }

    public static class SecondPlayerObserver implements PlayerRoundObserver, PlayerScoreObserver, EndGameObserver {
        private Game game;
        SecondPlayerObserver(Game game){
            this.game = game;
        }
        @Override
        public void nextRoundHandler(Board board) {
            boolean repeat = true;
            while (repeat){
                repeat = false;
                game.printBoard();
                int[] coords = readCoords();
                try{
                    if (coords[0] == 0 && coords[1] == 0 && coords[2] == 0 && coords[3] == 0){
                        game.secondPlayerMove(true,null, null);
                    }else{
                        System.out.println(board.toStringMovements(new int[]{coords[0], coords[1]}));
                        game.secondPlayerMove(false,new int[]{coords[0], coords[1]}, new int[]{coords[2], coords[3]});
                    }
                }catch (MoveEnemyPawnException | NoPawnException | WrongCoordsException | WrongMovementException | PawnAlreadyMovedInRoundException | OtherPawnAlreadyMovingException | PlayerNotPlayingException e){
                    repeat = true;
                    System.out.println(e.getMessage());
                } catch (WrongRoundException | GameOverException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        @Override
        public void newScoreHandler(int score) {
            System.out.println("Second NOUVEAU SCORE: "+score);
        }

        @Override
        public void endGameHandler(int teamWinnerID) {
            System.out.println("FIN de partie");
            if (teamWinnerID == Board.SECOND_TEAM_ID){
                System.out.println("Second joueur tu es GAGNANT");
            }else if(teamWinnerID == Board.FIRST_TEAM_ID){
                System.out.println("Second joueur tu es PERDANT");
            }
        }
    }
}
*/