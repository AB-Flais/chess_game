package chess.pieces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Tower extends Piece {
	private final static Texture lightImage = new Texture(Gdx.files.internal("white_tower.png"));
	private final static Texture darkImage = new Texture(Gdx.files.internal("black_tower.png"));
	
	public Tower(Team team) {
		super(team, lightImage, darkImage);
	}
}
