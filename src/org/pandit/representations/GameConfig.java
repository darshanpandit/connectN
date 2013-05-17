/**
 * 
 */
package org.pandit.representations;

import java.util.BitSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.text.GapContent;

/**
 * @author darshan
 *
 */
public class GameConfig {
	private int blockSizeToWin = 4;
	private int columnSize = 4;
	private int rowSize = 3;

	
	public GameConfig(int blockSizeToWin, int rowSize, int columnSize) {
		super();
		this.blockSizeToWin = blockSizeToWin;
		this.columnSize = columnSize;
		this.rowSize = rowSize;
	}

	public int findTopIndex(boolean[][] occupancyMatrix,int columnNumber){
		for(int i = 0; i < occupancyMatrix.length; i++){
			if(!occupancyMatrix[i][columnNumber])
				return i;
		}
		return -1;
	}
	
	public List<GameState> generateChildStates(GameState gameState, boolean aiTurn) throws CloneNotSupportedException{
		List<GameState> childList = new LinkedList<GameState>();
		GameState tempState;
		int index;
		boolean[][] bitSets = gameState.getOccupancyMatrix();
		
		// We assume gravity is turned on by default
		// Here we add to the top of the stack in the board
		for(int i=0; i<columnSize;i++){
			try {
				tempState = (GameState) gameState.clone();
				index = findTopIndex(bitSets, i);
				if(index>-1 && index<rowSize){
					if(aiTurn)
						tempState.setWhite(index, i);
					else
						tempState.setBlack(index, i);
					childList.add(tempState);
				}
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			
		}
		
		//This part tries to generate the states resulting due to pop moves
		
		for(int j=0; j<columnSize;j++){
			if(aiTurn && gameState.getWhiteBlocks()[0][j]){
				//Means we can pop out a white block from the stack
				tempState = (GameState) gameState.clone();
				boolean[][] whiteBlocks = tempState.getWhiteBlocks();
				boolean[][] blackBlocks = tempState.getBlackBlocks();
				for(int i=1; i<rowSize; i++){
					if(whiteBlocks[i][j]){
						whiteBlocks[i-1][j] = whiteBlocks[i][j];
						blackBlocks[i-1][j] = false;
					}
					else if(blackBlocks[i][j]){
						blackBlocks[i-1][j] = blackBlocks[i][j];
						whiteBlocks[i-1][j] = false;
					}
					else {
						blackBlocks[i-1][j] = false;
						whiteBlocks[i-1][j] = false;
					}
				}
				whiteBlocks[rowSize-1][j] = false;
				blackBlocks[rowSize-1][j] = false;
				childList.add(tempState);
			}
			else if((!aiTurn) && gameState.getBlackBlocks()[0][j]){
				//Means we can pop out a black block from the stack during the user turn
				tempState = (GameState) gameState.clone();
				boolean[][] whiteBlocks = tempState.getWhiteBlocks();
				boolean[][] blackBlocks = tempState.getBlackBlocks();
				for(int i=1; i<rowSize; i++){
					if(whiteBlocks[i][j]){
						whiteBlocks[i-1][j] = whiteBlocks[i][j];
						blackBlocks[i-1][j] = false;
					}
					else if(blackBlocks[i][j]){
						blackBlocks[i-1][j] = blackBlocks[i][j];
						whiteBlocks[i-1][j] = false;
					}
					else{
						blackBlocks[i-1][j] = false;
						whiteBlocks[i-1][j] = false;
					}
				}
				whiteBlocks[rowSize-1][j] = false;
				blackBlocks[rowSize-1][j] = false;
				childList.add(tempState);
			}
		}
		
		
		//System.out.println("Total childs : "+childList.size());	
		//returns the set
		return childList;
		
	}
	
	/**
	 * @return the blockSizeToWin
	 */
	public int getBlockSizeToWin() {
		return blockSizeToWin;
	}

	/**
	 * @param blockSizeToWin the blockSizeToWin to set
	 */
	public void setBlockSizeToWin(int blockSizeToWin) {
		this.blockSizeToWin = blockSizeToWin;
	}

	/**
	 * @return the columnSize
	 */
	public int getColumnSize() {
		return columnSize;
	}

	/**
	 * @param columnSize the columnSize to set
	 */
	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	/**
	 * @return the rowSize
	 */
	public int getRowSize() {
		return rowSize;
	}

	/**
	 * @param rowSize the rowSize to set
	 */
	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

	public GameConfig() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
