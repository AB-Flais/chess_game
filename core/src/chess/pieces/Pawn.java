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

	public int getDirection() { return getTeam()==Team.WHITE?1:-1; }
	
	@Override
	public boolean[][] possibleMoves(Team[][] reducedBoard, int x, int y) {
		int width = reducedBoard.length, height = reducedBoard[0].length;
		boolean[][] possibleMoves = new boolean[width][height];
		
		for (boolean[] row: possibleMoves)
			Arrays.fill(row, false);
		
		if (reducedBoard[x][y + 1*getDirection()] == null)
			possibleMoves[x][y + 1*getDirection()] = true;
		
		// If pawn's in start position
		if (((y == 1 && getTeam() == Team.WHITE)||(y == width - 2 && getTeam() == Team.BLACK))  && reducedBoard[x][y + 2*getDirection()] == null) 
			possibleMoves[x][y + 2*getDirection()] = true;
		
		// Pawn's options to kill
		if (x - 1 >= 0 && reducedBoard[x-1][y+1*getDirection()] != getTeam() && reducedBoard[x-1][y+1*getDirection()] != null)
			possibleMoves[x-1][y + 1*getDirection()] = true;
		
		if (x + 1 >= 0 && reducedBoard[x-1][y+1*getDirection()] != getTeam() && reducedBoard[x+1][y+1*getDirection()] != null)
			possibleMoves[x+1][y + 1*getDirection()] = true;
		return possibleMoves;
	}
}
