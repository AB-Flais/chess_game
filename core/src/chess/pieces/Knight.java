package chess.pieces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Knight extends Piece {
	private final static Texture lightImage = new Texture(Gdx.files.internal("white_knight.png"));
	private final static Texture darkImage = new Texture(Gdx.files.internal("black_knight.png"));
	
	public Knight(String coordinate, Team team) {
		super(coordinate, team, lightImage, darkImage);
	}
}
