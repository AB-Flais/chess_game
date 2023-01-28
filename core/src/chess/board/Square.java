package chess.board;

import chess.pieces.Piece;

public class Square {
	
	Piece piece;
	
	public Square() {
		piece = null;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public void render(int x, int y, int scale) {
		if (piece != null) 
			Board.batch.draw(piece.getTexture(),x*scale, y*scale, scale, scale);
	}

	public void dispose() {
		if (piece != null)
			piece.dispose();
	}
}
