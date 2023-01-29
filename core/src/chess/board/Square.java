package chess.board;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import chess.pieces.Piece;
import chess.pieces.Team;

public class Square {
	
	private SquareState state;
	private Piece piece;
	
	static Texture light = new Texture(Gdx.files.internal("light_square.png"));
	static Texture dark = new Texture(Gdx.files.internal("dark_square.png"));
	
	static Texture lightSelected = new Texture(Gdx.files.internal("light_selected_square.png"));
	static Texture darkSelected = new Texture(Gdx.files.internal("dark_selected_square.png"));
	
	static Texture lightPossibleMove = new Texture(Gdx.files.internal("light_possible_move.png"));
	static Texture darkPossibleMove = new Texture(Gdx.files.internal("dark_possible_move.png"));
	
	static Texture possibleCapture = new Texture(Gdx.files.internal("possible_capture.png"));
	
	public Square() {
		state = SquareState.DEFAULT;
		piece = null;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	/**
	 * Renders the square's piece if it's not null
	 * @param x	row
	 * @param y column 
	 * @param scale size of the piece, and number that is going to multiply x and y
	 */
	public void render(int x, int y, int scale) {
		if (piece != null) 
			Board.batch.draw(piece.getTexture(),x*scale, y*scale, scale, scale);
	}

	public void select() {
		state = SquareState.SELECTED;
	}
	
	public void unSelect() {
		state = SquareState.DEFAULT;
	}
	
	public Texture getDarkTexture() { 
		switch (state) {
		case DEFAULT: return Square.dark;
		case SELECTED: return Square.darkSelected;
		case POSSIBLE_CAPTURE: return Square.possibleCapture;
		case POSSIBLE_MOVE: return Square.darkPossibleMove;
		default: return null;
		}
	}
	
	public Texture getLightTexture() { 
		switch (state) {
		case DEFAULT: return Square.light;
		case SELECTED: return Square.lightSelected;
		case POSSIBLE_CAPTURE: return Square.possibleCapture;
		case POSSIBLE_MOVE: return Square.lightPossibleMove;
		default: return null;
		}
	}

	public void dispose() {
		if (piece != null)
			piece.dispose();
	}

	public void setTexture(SquareState state) {
		this.state = state;
	}

	public Team getTeam() {
		if (piece == null)
			return null;
		return piece.getTeam();
	}

	public boolean[][] showPossibleMoves(Team[][] reducedBoard, int x, int y) {
		if (piece != null)
			return piece.possibleMoves(reducedBoard,x,y);
		boolean[][] possibleMoves = new boolean[reducedBoard.length][reducedBoard[0].length];
		for (boolean[] row: possibleMoves)
			Arrays.fill(row, false);
		return possibleMoves;
	}
}
