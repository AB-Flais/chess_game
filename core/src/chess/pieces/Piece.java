package chess.pieces;

import com.badlogic.gdx.graphics.Texture;

public abstract class Piece {
	private Team team;
	private int x;
	private int y;
	private Texture lightTexture;
	private Texture darkTexture;
	
	public Piece(Team team, Texture light, Texture dark) {
		this.team = team;
		lightTexture = light;
		darkTexture = dark;
	}
	
	public Team getTeam() { return team; }
	public int getX() { return x; }
	public int getY() { return y; }
	public Texture getTexture() { return (team == Team.WHITE)?lightTexture:darkTexture; }

	public void dispose() {
		lightTexture.dispose();
		darkTexture.dispose();
	}
}
