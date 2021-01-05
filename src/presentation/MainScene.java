package presentation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import exceptions.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.board.Board;
import model.board.Portal;
import model.board.Square;
import model.game.Game;
import model.pawns.Dragon;
import model.pawns.Lion;
import model.pawns.Monkey;
import model.pawns.Pawn;
import model.pawns.Wall;
import observers.EndGameObserver;
import observers.PlayerRoundObserver;
import observers.PlayerScoreObserver;

public class MainScene extends Scene {

    private final static int COLS = 10;
    private final static int ROWS = 10;
	static private Button[][] lesBoutons;

	public MainScene() throws Exception {
		super(new BorderPane());
        try{
            Game game = new Game();
    		BorderPane root = (BorderPane)(this.getRoot());
    		GridPane boardPane = new GridPane();
    		
    		lesBoutons = new Button[ROWS][COLS];

    		BorderPane scoresAndStartButton = new BorderPane();
    		scoresAndStartButton.setPadding(new Insets(10, 20, 20, 20));
    		
    		GridPane scores = new GridPane();
			Label titleScoreFirstPlayer = new Label("Nb dragons P1: ");
			titleScoreFirstPlayer.setFont(new Font(30));
			scores.add(titleScoreFirstPlayer, 0, 0);
			
			Label scoreFirstPlayer = new Label("2");
			scoreFirstPlayer.setFont(new Font(30));
			scores.add(scoreFirstPlayer, 1, 0);
			
			Label titleScoreSecondPlayer = new Label("Nb dragons P2: ");
			titleScoreSecondPlayer.setFont(new Font(30));
			scores.add(titleScoreSecondPlayer, 0, 1);
			
			Label scoreSecondPlayer = new Label("2");
			scoreSecondPlayer.setFont(new Font(30));
			scores.add(scoreSecondPlayer, 1, 1);
			scoresAndStartButton.setTop(scores);
			

    		BorderPane startButtonPane = new BorderPane();
			Button startButton = new Button("Start");
			startButton.setPrefSize(150,70);
			startButton.setFont(new Font(30));
			startButtonPane.setCenter(startButton);
			scoresAndStartButton.setBottom(startButtonPane);
			
            
			FirstPlayerObserver firstPlayerObserver = new FirstPlayerObserver(game, boardPane);
            SecondPlayerObserver secondPlayerObserver = new SecondPlayerObserver(game, boardPane);
            game.addFirstPlayerRoundObservers(firstPlayerObserver);
            game.addFirstPlayerScoreObservers(firstPlayerObserver);
            game.addEndGameObservers(firstPlayerObserver);
            game.addSecondPlayerRoundObservers(secondPlayerObserver);
            game.addSecondPlayerScoreObservers(secondPlayerObserver);
            game.addEndGameObservers(secondPlayerObserver);
            game.start(); // A transferer dans un bouton
            
            root.setLeft(boardPane);

            root.setCenter(scoresAndStartButton);
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
        private GridPane boardPane;
        FirstPlayerObserver(Game game, GridPane boardPane){
            this.game = game;
            this.boardPane = boardPane;
        }
        @Override
        public void nextRoundHandler(Board board) {
            boolean repeat = true;
            while (repeat){
                repeat = false;
                
                // Print board
                for (int i = 0; i < COLS; i++){
                    for (int j = 0; j < ROWS; j++){
                        Square square = null;
						try {
							square = board.getSquare(j,i);
						} catch (WrongCoordsException e) {
		                    System.out.println(e.getMessage());
						}
						Image imageSquare = null;
						try {
							imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"square_empty.png"), 70, 70, false, false);
						} catch (FileNotFoundException e) {
		                    System.out.println(e.getMessage());
						}
                        
                        if(square != null && square.hasPawn())
                        {
                        	Pawn pawnSquare = square.getPawn();
                        	if(pawnSquare.getTeamId() == Board.FIRST_TEAM_ID)
                        	{
                        		if(pawnSquare instanceof Wall)
                        		{
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"wall.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        		else if(pawnSquare instanceof Dragon)
                        		{
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"blue_dragon.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        		else if(pawnSquare instanceof Lion)
                        		{
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"blue_lion.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        		else if(pawnSquare instanceof Monkey)
                        		{
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"blue_monkey.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        		else {
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"square_empty.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        	}
                        	else
                        	{
                        		if(pawnSquare instanceof Wall)
                        		{
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"wall.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        		else if(pawnSquare instanceof Dragon)
                        		{
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"red_dragon.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        		else if(pawnSquare instanceof Lion)
                        		{
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"red_lion.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        		else if(pawnSquare instanceof Monkey)
                        		{
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"red_monkey.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        		else {
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"square_empty.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        	}
                        }
                        else
                        {
                            if(square instanceof Portal)
                            {
                            	Portal portal = (Portal) square;
                            	if(portal.getTeamId() == Board.FIRST_TEAM_ID)
                            	{
				                    System.out.println("test");
    								try {
    									imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"blue_portal.png"), 70, 70, false, false);
    								} catch (FileNotFoundException e) {
    				                    System.out.println(e.getMessage());
    								}
                            	}
                            	else
                            	{
    								try {
    									imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"red_portal.png"), 70, 70, false, false);
    								} catch (FileNotFoundException e) {
    				                    System.out.println(e.getMessage());
    								}
                            	}
                            }
                            else
                            {
                            	try {
    								imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"square_empty.png"), 70, 70, false, false);
    							} catch (FileNotFoundException e) {
    			                    System.out.println(e.getMessage());
    							}
                            }
                        }

                        lesBoutons[i][j] = new Button("");
                        lesBoutons[i][j].setGraphic(new ImageView(imageSquare));
                        lesBoutons[i][j].setStyle("-fx-padding:0;-fx-background-size:0;");
                    }
                    boardPane.addRow(i,  lesBoutons[i]);
                }
                /*
                // Traitement à transférer au bouton
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
                }*/
            }
        }

        @Override
        public void newScoreHandler(int score) {
        	// Label a update
            System.out.println("First NOUVEAU SCORE: "+score);
        }


        @Override
        public void endGameHandler(int teamWinnerID) {
        	// Alertbox
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
        private GridPane boardPane;
        SecondPlayerObserver(Game game, GridPane boardPane){
            this.game = game;
            this.boardPane = boardPane;
        }
        @Override
        public void nextRoundHandler(Board board) {
            boolean repeat = true;
            while (repeat){
                repeat = false;
                
             // Print board
                for (int i = 0; i < COLS; i++){
                    for (int j = 0; j < ROWS; j++){
                        Square square = null;
						try {
							square = board.getSquare(j,i);
						} catch (WrongCoordsException e) {
		                    System.out.println(e.getMessage());
						}
						Image imageSquare = null;
						try {
							imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"square_empty.png"), 70, 70, false, false);
						} catch (FileNotFoundException e) {
		                    System.out.println(e.getMessage());
						}
                        
                        if(square != null && square.hasPawn())
                        {
                        	Pawn pawnSquare = square.getPawn();
                        	if(pawnSquare.getTeamId() == Board.FIRST_TEAM_ID)
                        	{
                        		if(pawnSquare instanceof Wall)
                        		{
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"wall.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        		else if(pawnSquare instanceof Dragon)
                        		{
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"blue_dragon.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        		else if(pawnSquare instanceof Lion)
                        		{
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"blue_lion.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        		else if(pawnSquare instanceof Monkey)
                        		{
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"blue_monkey.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        		else {
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"square_empty.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        	}
                        	else
                        	{
                        		if(pawnSquare instanceof Wall)
                        		{
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"wall.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        		else if(pawnSquare instanceof Dragon)
                        		{
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"red_dragon.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        		else if(pawnSquare instanceof Lion)
                        		{
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"red_lion.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        		else if(pawnSquare instanceof Monkey)
                        		{
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"red_monkey.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        		else {
									try {
										imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"square_empty.png"), 70, 70, false, false);
									} catch (FileNotFoundException e) {
					                    System.out.println(e.getMessage());
									}
                        		}
                        	}
                        }
                        else
                        {
                            if(square instanceof Portal)
                            {
                            	Portal portal = (Portal) square;
                            	if(portal.getTeamId() == Board.FIRST_TEAM_ID)
                            	{
				                    System.out.println("test");
    								try {
    									imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"blue_portal.png"), 70, 70, false, false);
    								} catch (FileNotFoundException e) {
    				                    System.out.println(e.getMessage());
    								}
                            	}
                            	else
                            	{
    								try {
    									imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"red_portal.png"), 70, 70, false, false);
    								} catch (FileNotFoundException e) {
    				                    System.out.println(e.getMessage());
    								}
                            	}
                            }
                            else
                            {
                            	try {
    								imageSquare = new Image(new FileInputStream("shingshang"+File.separator+"images"+File.separator+"square_empty.png"), 70, 70, false, false);
    							} catch (FileNotFoundException e) {
    			                    System.out.println(e.getMessage());
    							}
                            }
                        }

                        lesBoutons[i][j] = new Button("");
                        lesBoutons[i][j].setGraphic(new ImageView(imageSquare));
                        lesBoutons[i][j].setStyle("-fx-padding:0;-fx-background-size:0;");
                    }
                    boardPane.addRow(i,  lesBoutons[i]);
                }

                // Traitement à transférer au bouton
                /*
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
                }*/
            }
        }

        @Override
        public void newScoreHandler(int score) {
        	// Label a update
            System.out.println("Second NOUVEAU SCORE: "+score);
        }

        @Override
        public void endGameHandler(int teamWinnerID) {
        	// Alertbox
            System.out.println("FIN de partie");
            if (teamWinnerID == Board.SECOND_TEAM_ID){
                System.out.println("Second joueur tu es GAGNANT");
            }else if(teamWinnerID == Board.FIRST_TEAM_ID){
                System.out.println("Second joueur tu es PERDANT");
            }
        }
    }
}
