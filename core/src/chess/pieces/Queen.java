package chess.pieces;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Queen extends Piece {
	private final static Texture lightImage = new Texture(Gdx.files.internal("white_queen.png"));
	private final static Texture darkImage = new Texture(Gdx.files.internal("black_queen.png"));
	
	public Queen(Team team) {
		super(team, lightImage, darkImage);
	}
	
	@Override
	public boolean[][] possibleMoves(Team[][] reducedBoard, int x, int y) {
		int auxX = x, auxY = y;
		int width = reducedBoard.length, height = reducedBoard[0].length;
		boolean[][] possibleMoves = new boolean[width][height];
		
		for (boolean[] row: possibleMoves)
			Arrays.fill(row, false);
		
		// Bishop algorythm
		for (int i = -1; i <= 1; i = i + 2)
			for (int j = -1; j <= 1; j = j + 2) {   
				while (true) {
					x = x + i; y = y + j;
					if (x < 0 || y < 0 || x >= width || y >= height || reducedBoard[x][y] == getTeam())
						break;
					else
						possibleMoves[x][y] = true;
					if (reducedBoard[x][y] != null) // Therefore it's an enemy piece
						break;
				}
				x = auxX; y = auxY;
			}
		
		// Tower algorythm
		for (int i = -1; i <= 1; i = i + 2) {
			while (true) {
				x = x + i;
				if (x < 0 || x >= width || reducedBoard[x][y] == getTeam())
					break;
				else
					possibleMoves[x][y] = true;
				if (reducedBoard[x][y] != null) // Therefore it's an enemy piece
					break;
			}
			x = auxX;
		}
			
		for (int i = -1; i <= 1; i = i + 2) {
			while (true) {
				y = y + i;
				if (y < 0 || y >= height || reducedBoard[x][y] == getTeam())
					break;
				else
					possibleMoves[x][y] = true;
				if (reducedBoard[x][y] != null) // Therefore it's an enemy piece
					break;
			}
			y = auxY;
		}
		
		return possibleMoves;
	}
}
