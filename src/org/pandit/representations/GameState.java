/**
 * 
 */
package org.pandit.representations;

import java.util.Arrays;
import java.util.BitSet;

import org.pandit.ai.AlphaBetaPrunning;
import org.pandit.ai.CustomHeuristic;
import org.pandit.ai.Heuristic;

/**
 * @author darshan
 * 
 */
public class GameState implements Cloneable {
	private boolean[][] blackBlocks;
	private boolean[][] whiteBlocks;

	/**
	 * Constructor Initiates a state of n*m. Each row on the board is
	 * represented as a BitSet There are m (number of rows) then we initialize
	 * an array of size m. Each BitSet in this array is of size 'n'
	 * (numberOfColumns)
	 * 
	 * @param numberOfRows
	 * @param numberOfColumns
	 */
	public GameState(int numberOfRows, int numberOfColumns) {
		super();

		// We use this representation as we can then exploit bitSet.get
		this.blackBlocks = new boolean[numberOfRows][numberOfColumns];
		this.whiteBlocks = new boolean[numberOfRows][numberOfColumns];

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GameState [blackBlocks=" + Arrays.deepToString(blackBlocks)
				+ ", whiteBlocks=" + Arrays.deepToString(whiteBlocks) + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(blackBlocks);
		result = prime * result + Arrays.deepHashCode(whiteBlocks);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameState other = (GameState) obj;
		if (!Arrays.equals(blackBlocks, other.blackBlocks))
			return false;
		if (!Arrays.equals(whiteBlocks, other.whiteBlocks))
			return false;
		return true;
	}
	/**
	 * Sets the blackCoordinate
	 * @param rowCoordinate
	 * @param columnCoordinate
	 */
	public void setBlack(int rowCoordinate, int columnCoordinate){
		blackBlocks[rowCoordinate][columnCoordinate] = true;
	}
	
	/**
	 * Sets the whiteCoordinate
	 * @param rowCoordinate
	 * @param columnCoordinate
	 */
	public void setWhite(int rowCoordinate, int columnCoordinate){
		whiteBlocks[rowCoordinate][columnCoordinate] = true;
	}
	
	/**
	 * @return the blackBlocks
	 */
	public boolean[][] getBlackBlocks() {
		return blackBlocks;
	}

	/**
	 * @param blackBlocks
	 *            the blackBlocks to set
	 */
	public void setBlackBlocks(boolean[][] blackBlocks) {
		this.blackBlocks = blackBlocks;
	}

	/**
	 * @return the whiteBlocks
	 */
	public boolean[][] getWhiteBlocks() {
		return whiteBlocks;
	}

	/**
	 * @param whiteBlocks
	 *            the whiteBlocks to set
	 */
	public void setWhiteBlocks(boolean[][] whiteBlocks) {
		this.whiteBlocks = whiteBlocks;
	}

	/**
	 * Returns true if the block is set to white.
	 * 
	 * @param rowCordinate
	 * @param columnCordinate
	 * @return
	 */
	public boolean isWhite(int rowCordinate, int columnCordinate) {
		return this.whiteBlocks[rowCordinate][columnCordinate];
	}

	/**
	 * Returns true if block is set to black
	 * 
	 * @param rowCordinate
	 * @param columnCordinate
	 * @return
	 */
	public boolean isBlack(int rowCordinate, int columnCordinate) {
		return this.blackBlocks[rowCordinate][columnCordinate];
	}

	/**
	 * returns true if the cell is empty
	 * 
	 * @param rowCordinate
	 * @param columnCordinate
	 * @return
	 */
	public boolean isEmpty(int rowCordinate, int columnCordinate) {
		return !(isBlack(rowCordinate, columnCordinate) || isWhite(
				rowCordinate, columnCordinate));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		GameState gameState = new GameState(this.blackBlocks.length, this.blackBlocks[0].length);
		boolean[][] newBlackBlocks = gameState.getBlackBlocks();
		boolean[][] newWhiteBlocks = gameState.getWhiteBlocks();
		for(int i=0;i<this.blackBlocks.length;i++){
			newBlackBlocks[i] = Arrays.copyOf(blackBlocks[i], blackBlocks[i].length);
			newWhiteBlocks[i] = Arrays.copyOf(whiteBlocks[i], whiteBlocks[i].length);
		}
		gameState.setBlackBlocks(newBlackBlocks);
		gameState.setWhiteBlocks(newWhiteBlocks);
		return gameState;
	}

	public boolean[][] getOccupancyMatrix() {
		boolean[][] occupancyMatrix = new boolean[whiteBlocks.length][whiteBlocks[0].length];
		for (int i = 0; i < whiteBlocks.length; i++)
			for (int j = 0; j < whiteBlocks[0].length; j++)
				occupancyMatrix[i][j] = (whiteBlocks[i][j] || blackBlocks[i][j]);

		return occupancyMatrix;

	}

	public static void main(String[] args) throws CloneNotSupportedException {
		GameConfig config = new GameConfig(3, 3,3);
		Heuristic heuristic = new CustomHeuristic(config);

		GameState gameState = new GameState(config.getRowSize(),
				config.getColumnSize());
		
		gameState.setBlack(0,0);
		gameState.setWhite(0,1);
		gameState.setBlack(0,2);
		
		gameState.setWhite(1,0);
		
		gameState.setBlack(2, 0);
		
		
		AlphaBetaPrunning alphaBetaPrunning = new AlphaBetaPrunning(1, config,
				heuristic);
		System.out.println("Root State : " + gameState);
		GameState childGameState = alphaBetaPrunning.alphaBeta(gameState);
		System.out.println("Next State: " + childGameState);
		
	}
}
