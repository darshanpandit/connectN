package org.pandit.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.pandit.ai.AlphaBetaPrunning;
import org.pandit.ai.CustomHeuristic;
import org.pandit.ai.Heuristic;
import org.pandit.representations.GameConfig;
import org.pandit.representations.GameState;

public class GameMain {
	public static void main(String[] args) throws IOException,
			CloneNotSupportedException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		System.out.println("Enter the number of rows");
		int row = Integer.parseInt(reader.readLine());
		System.out.println("Enter the number of columns");
		int column = Integer.parseInt(reader.readLine());
		System.out.println("Enter the Difficulty level:");
		int lookupLevel = Integer.parseInt(reader.readLine());
		System.out.println("Enter the Connect level:");
		int connectN = Integer.parseInt(reader.readLine());

		System.out.println("Do you want to play first?");
		boolean computerTurn = true;
		if (reader.readLine().equalsIgnoreCase("yes"))
			computerTurn = false;

		GameConfig config = new GameConfig(connectN, row, column);
		Heuristic heuristic = new CustomHeuristic(config);
		GameState gameState = new GameState(config.getRowSize(),
				config.getColumnSize());
		AlphaBetaPrunning alphaBetaPrunning = new AlphaBetaPrunning(
				lookupLevel, config, heuristic);
		boolean notGameEnd = true;
		while (notGameEnd) {
			if (heuristic.isTerminal(gameState)) {
				int heuristicValue = heuristic.getHeuristicValue(gameState);
				if (heuristicValue > Math.pow(10, (connectN)))
					System.out.println("Computer Wins with score : "
							+ heuristicValue);
				else if (heuristicValue == 0)
					System.out
							.println("The game is drawed. No more possibe moves.");
				else if (heuristicValue < -(Math.pow(10, (connectN))))
					System.out.println("You win with a score of "
							+ heuristicValue);
				else
					System.out
							.println("Cant determine who won or the game was drawn");
				notGameEnd = false;
			} else {
				if (computerTurn) {
					gameState = alphaBetaPrunning.alphaBeta(gameState);
					System.out.println("Board State after computer's move:");
					System.out.println(gameState);
					computerTurn = false;
				} else {
					System.out.println("***********************************************************************");
					System.out.println("Current State of the board");
					System.out.println(gameState);
					boolean notValidMove = true;
					while (notValidMove) {
						System.out.println("Do you want to p[U]sh/p[O]p");
						String temp = reader.readLine();

						if (temp.equalsIgnoreCase("u")) {
							// Check if we can push/ else continue to ask for
							// push/pop options
							System.out
									.println("Enter the Column Number to push : ");
							int col = Integer.parseInt(reader.readLine());
							if(col>=config.getColumnSize()){
								System.out.println("Invalid Number Entered. Please Reselect your option");
								break;
								
							}
							int i = config.findTopIndex(
									gameState.getOccupancyMatrix(), col);
							if (i != -1) {
								notValidMove = false;
								gameState.setBlack(i, col);
								computerTurn = true;
							} else
								System.out
										.println("Input Invalid. Please re-enter.");
						}
						if (temp.equalsIgnoreCase("o")) {
							// Check if we can pop else continue to ask for
							// push/pop options
							System.out
									.println("Enter the Column Number to pop put : ");
							int col = Integer.parseInt(reader.readLine());
							if(col>=config.getColumnSize()){
								System.out.println("Invalid Number Entered. Please Reselect your option");
								break;
								
							}
							if (gameState.getBlackBlocks()[0][col] == true) {
								// Pop out and modify game state
								GameState tempState = (GameState) gameState
										.clone();
								boolean[][] whiteBlocks = tempState
										.getWhiteBlocks();
								boolean[][] blackBlocks = tempState
										.getBlackBlocks();
								for (int i = 1; i < config.getRowSize(); i++) {
									if (whiteBlocks[i][col]) {
										whiteBlocks[i - 1][col] = whiteBlocks[i][col];
										blackBlocks[i - 1][col] = false;
									} else if (blackBlocks[i][col]) {
										blackBlocks[i - 1][col] = blackBlocks[i][col];
										whiteBlocks[i - 1][col] = false;
									} else {
										blackBlocks[i - 1][col] = false;
										whiteBlocks[i - 1][col] = false;
									}
									notValidMove = false;
									whiteBlocks[config.getRowSize() - 1][col] = false;
									blackBlocks[config.getRowSize() - 1][col] = false;
									gameState = tempState;
									computerTurn = true;
									
								}
							} else
								System.out
										.println("Invalid Popout. Please re-enter.");
						}

					}

				}
			}

		}

	}
}
