package chess.pieces;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Bishop extends Piece {
	private final static Texture lightImage = new Texture(Gdx.files.internal("white_bishop.png"));
	private final static Texture darkImage = new Texture(Gdx.files.internal("black_bishop.png"));
	
	public Bishop(Team team) {
		super(team, lightImage, darkImage);
	}

	@Override
	public boolean[][] possibleMoves(Team[][] reducedBoard, int x, int y) {
		int auxX = x, auxY = y;
		int width = reducedBoard.length, height = reducedBoard[0].length;
		boolean[][] possibleMoves = new boolean[width][height];
		
		for (boolean[] row: possibleMoves)
			Arrays.fill(row, false);
		
		for (int i = -1; i <= 1; i = i + 2)
			for (int j = -1; j <= 1; j = j + 2) {   // This 2 for loops are for the next algorythm to execute four times, one where both x and y are increasing 
													// by one each iteration, one where both are decreasing one, and two where one is decreasing while the other is increasing.
													// This is, of course, to run the 4 diagonals a bishop can move
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
		
		return possibleMoves;
	}
}
