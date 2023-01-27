package chess.pieces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class King extends Piece {
	private final static Texture lightImage = new Texture(Gdx.files.internal("white_king.png"));
	private final static Texture darkImage = new Texture(Gdx.files.internal("black_king.png"));
	
	public King(String coordinate, Team team) {
		super(coordinate, team, lightImage, darkImage);
	}
}
