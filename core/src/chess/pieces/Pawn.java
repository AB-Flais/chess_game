package chess.pieces;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Pawn extends Piece {
	private final static Texture lightImage = new Texture(Gdx.files.internal("white_pawn.png"));
	private final static Texture darkImage = new Texture(Gdx.files.internal("black_pawn.png"));
	
	public Pawn(Team team) {
		super(team, lightImage, darkImage);
	}

	@Override
	public boolean[][] possibleMoves(Team[][] reducedBoard, int x, int y) {
		int direction = getTeam()==Team.WHITE?1:-1;
		int width = reducedBoard.length, height = reducedBoard[0].length;
		boolean[][] possibleMoves = new boolean[width][height];
		
		for (boolean[] row: possibleMoves)
			Arrays.fill(row, false);
		
		if (reducedBoard[x][y + 1*direction] == null)
			possibleMoves[x][y + 1*direction] = true;
		
		// If pawn's in start position
		if (((y == 2 && getTeam() == Team.WHITE)||(y == 7 && getTeam() == Team.BLACK))  && reducedBoard[x][y + 2*direction] == null) 
			possibleMoves[x][y + 2*direction] = true;
		
		// Pawn's options to kill
		if (x - 1 >= 0 && reducedBoard[x-1][y+1*direction] != getTeam() && reducedBoard[x-1][y+1*direction] != null)
			possibleMoves[x-1][y + 1*direction] = true;
		
		if (x + 1 >= 0 && reducedBoard[x-1][y+1*direction] != getTeam() && reducedBoard[x+1][y+1*direction] != null)
			possibleMoves[x+1][y + 1*direction] = true;
		return possibleMoves;
	}
}
