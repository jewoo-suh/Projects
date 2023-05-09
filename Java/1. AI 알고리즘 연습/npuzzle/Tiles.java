package npuzzle;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import search.Action;
import search.State;

public class Tiles implements State {
	public static final int EMPTY_TILE = 0;
	
	protected final int width;
	protected final int[] tiles;
	protected final int emptyTileRow;
	protected final int emptyTileColumn;
	
	public Tiles(int[][] tiles) {
		width = tiles.length;
		this.tiles = new int[width * width];
		int emptyRow = -1;
		int emptyColumn = -1;
		int index=0;
		for (int row = 0; row < width; row++)
			for (int column = 0; column < width; column++) {
				int tile = tiles[row][column];
				if (tile == EMPTY_TILE) {
					emptyRow = row;
					emptyColumn = column;
				}
				this.tiles[index++] = tile;
			}
		emptyTileRow = emptyRow;
		emptyTileColumn = emptyColumn;
	}
	protected Tiles(int width, int[] tiles, int emptyTileRow, int emptyTileColumn) {
		this.width = width;
		this.tiles = tiles;
		this.emptyTileRow = emptyTileRow;
		this.emptyTileColumn = emptyTileColumn;
	}
	public int getWidth() {
		return width;
	}
	public int getEmptyTileRow() {
		return emptyTileRow;
	}
	public int getEmptyTileColumn() {
		return emptyTileColumn;
	}
	public int getTile(int row, int column) {
		return tiles[row * width + column];
	}
	public Set<Action> getApplicableActions() {
		Set<Action> actions = new LinkedHashSet<Action>();
		for (Movement movement : Movement.values()) {
			int newEmptyTileRow = emptyTileRow + movement.deltaRow;
			int newEmptyTileColumn = emptyTileColumn + movement.deltaColumn;
			if (0 <= newEmptyTileRow && newEmptyTileRow < width && 0 <= newEmptyTileColumn & newEmptyTileColumn < width)
				actions.add(movement);
		}
		return actions;
	}
	public State getActionResult(Action action) {
		Movement movement=(Movement)action;
		int newEmptyTileRow = emptyTileRow + movement.deltaRow;
		int newEmptyTileColumn = emptyTileColumn + movement.deltaColumn;
		int[] newTiles = tiles.clone();
		newTiles[emptyTileRow * width + emptyTileColumn] = getTile(newEmptyTileRow, newEmptyTileColumn);
		newTiles[newEmptyTileRow * width + newEmptyTileColumn] = EMPTY_TILE;
		return new Tiles(width, newTiles, newEmptyTileRow, newEmptyTileColumn);
	}

	@Override
	public boolean equals(Object that) {

		// Check if we are comparing the same object
		if (this == that) {
			return true;
		}

		//Check if the compared object is null, or in a different class
		if (that == null || getClass() != that.getClass()) {
			return false;
		}

		//There may be multiple sub-classes, thus cast into higher class
		Tiles thatTiles = (Tiles) that;

		//If either the width of the tile board is different,
		//or if the empty tiles are in different locations return false
		if (width != thatTiles.width || emptyTileRow != thatTiles.emptyTileRow
				|| emptyTileColumn != thatTiles.emptyTileColumn) {
			return false;
		}

		//Else if the widths are the same and the empty tiles are in the same locations,
		//Check if the two tiles have the same tile arrangement and return the respective result
		return Arrays.equals(tiles, thatTiles.tiles);
	}

	@Override
	public int hashCode() {

		//Create appropriate variables; In our function, we use 31 (a popular prime)
		int result = width;
		int prime = 31;

		//The hashCode of a tile will be made by multiplying the prime to the width, adding the
		//hashcode of the titles, and repeating the add-multiply sequence for the row/column of
		//the empty tile
		result = ((prime * result + Arrays.hashCode(tiles)) * prime + emptyTileRow) * prime + emptyTileColumn;

		return result;

	}
}
