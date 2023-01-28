package chess.pieces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Queen extends Piece {
	private final static Texture lightImage = new Texture(Gdx.files.internal("white_queen.png"));
	private final static Texture darkImage = new Texture(Gdx.files.internal("black_queen.png"));
	
	public Queen(Team team) {
		super(team, lightImage, darkImage);
	}
}
