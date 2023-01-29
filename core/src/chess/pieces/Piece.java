package chess.pieces;

import com.badlogic.gdx.graphics.Texture;

public abstract class Piece {
	private Team team;
	private Texture lightTexture;
	private Texture darkTexture;
	
	public Piece(Team team, Texture light, Texture dark) {
		this.team = team;
		lightTexture = light;
		darkTexture = dark;
	}
	
	public Team getTeam() { return team; }
	public Texture getTexture() { return (team == Team.WHITE)?lightTexture:darkTexture; }

	public void dispose() {
		lightTexture.dispose();
		darkTexture.dispose();
	}

	public abstract boolean[][] possibleMoves(Team[][] reducedBoard, int x, int y);
}
