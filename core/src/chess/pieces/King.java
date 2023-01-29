package chess.pieces;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class King extends Piece {
	private final static Texture lightImage = new Texture(Gdx.files.internal("white_king.png"));
	private final static Texture darkImage = new Texture(Gdx.files.internal("black_king.png"));
	
	public King(Team team) {
		super(team, lightImage, darkImage);
	}
	
	@Override
	public boolean[][] possibleMoves(Team[][] reducedBoard, int x, int y) {
		int width = reducedBoard.length, height = reducedBoard[0].length;
		boolean[][] possibleMoves = new boolean[width][height];
		
		for (boolean[] row: possibleMoves)
			Arrays.fill(row, false);
		
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++) {
				if (x + i >= 0 && x + i < width && y + j >= 0 && y + j < height && reducedBoard[x + i][y + j] != getTeam())
					possibleMoves[x + i][y + j] = true;
			}
				
				
		return possibleMoves;
	}
}
