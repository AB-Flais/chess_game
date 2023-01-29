package chess.pieces;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Knight extends Piece {
	private final static Texture lightImage = new Texture(Gdx.files.internal("white_knight.png"));
	private final static Texture darkImage = new Texture(Gdx.files.internal("black_knight.png"));
	
	public Knight(Team team) {
		super(team, lightImage, darkImage);
	}
	
	@Override
	public boolean[][] possibleMoves(Team[][] reducedBoard, int x, int y) {
		int width = reducedBoard.length, height = reducedBoard[0].length;
		boolean[][] possibleMoves = new boolean[width][height];
		
		for (boolean[] row: possibleMoves)
			Arrays.fill(row, false);
		
		for (int i = -2; i <= 2; i++) {
			if (i == 0)
				continue;
			for (int j = -2; j <= 2; j++) {  
				if (j == 0)
					continue;
				if (x + i >= 0 && x + i < width && y + j >= 0 && y + j < height && reducedBoard[x + i][y + j] != getTeam()&& 
					((i>0)?i:-1*i)-((j>0)?j:-1*j) != 0) // Note that the conditional is exact as the king one, apart from the last condition.
														// As the algorythm is done with nested for loops, it normally would check the diagonals too.
														// The last condition checks that |i] - |j] != 0 (what would be a diagonal)
					possibleMoves[x + i][y + j] = true;
			}
		}
		
		return possibleMoves;
	}
}