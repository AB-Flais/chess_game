package chess.pieces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Bishop extends Piece {
	private final static Texture lightImage = new Texture(Gdx.files.internal("white_bishop.png"));
	private final static Texture darkImage = new Texture(Gdx.files.internal("black_bishop.png"));
	
	public Bishop(String coordinate, Team team) {
		super(coordinate, team, lightImage, darkImage);
	}
}
