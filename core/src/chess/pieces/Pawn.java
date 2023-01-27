package chess.pieces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Pawn extends Piece {
	private final static Texture lightImage = new Texture(Gdx.files.internal("white_pawn.png"));
	private final static Texture darkImage = new Texture(Gdx.files.internal("black_pawn.png"));
	
	public Pawn(String coordinate, Team team) {
		super(coordinate, team, lightImage, darkImage);
	}
}
