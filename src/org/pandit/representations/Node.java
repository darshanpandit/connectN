/**
 * 
 */
package org.pandit.representations;

/**
 * @author darshan
 *
 */
public class Node {
	private GameState currentGameState;
	private GameState childGameState;
	private int heuristicValue;
	public Node(GameState currentGameState) {
		super();
		heuristicValue = 0;
		childGameState = null;
		this.currentGameState = currentGameState;
	}

	/**
	 * @return the childGameState
	 */
	public GameState getChildGameState() {
		return childGameState;
	}

	/**
	 * @param childGameState the childGameState to set
	 */
	public void setChildGameState(GameState childGameState) {
		this.childGameState = childGameState;
	}

	/**
	 * @return the heuristicValue
	 */
	public int getHeuristicValue() {
		return heuristicValue;
	}

	/**
	 * @param heuristicValue the heuristicValue to set
	 */
	public void setHeuristicValue(int heuristicValue) {
		this.heuristicValue = heuristicValue;
	}

	/**
	 * @return the currentGameState
	 */
	public GameState getCurrentGameState() {
		return currentGameState;
	}

	/**
	 * @param currentGameState the currentGameState to set
	 */
	public void setCurrentGameState(GameState currentGameState) {
		this.currentGameState = currentGameState;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Node [currentGameState=" + currentGameState
				+ ", childGameState=" + childGameState + ", heuristicValue="
				+ heuristicValue + "]";
	}
	
	
	
}
