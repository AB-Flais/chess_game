package chess.pieces;

import com.badlogic.gdx.graphics.Texture;

public abstract class Piece {
	private Team team;
	private int x;
	private int y;
	private Texture lightTexture;
	private Texture darkTexture;
	
	public Piece(String coordinate, Team team, Texture light, Texture dark) {
		if (coordinate.length() < 2)
			throw new IllegalArgumentException();
		
		x = coordinate.charAt(0) - 97;
		y = coordinate.charAt(1) - 49;
		this.team = team;
		lightTexture = light;
		darkTexture = dark;
	}
	
	public Team getTeam() { return team; }
	public int getX() { return x; }
	public int getY() { return y; }
	public Texture getTexture() { return (team == Team.White)?lightTexture:darkTexture; };
}
