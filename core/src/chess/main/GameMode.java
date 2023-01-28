package chess.main;

import chess.board.Board;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Team;
import chess.pieces.Tower;

public class GameMode {
	public static Board normal(GameScreen game) {
		Board board = new Board(8,8,game.batch,game.camera);
		
		// Pawns
		for (int i = 0; i < 8; i++)
			board.putPiece(new Pawn(Team.WHITE), "" + ((char)('a' + i)) + 2);
		
		for (int i = 0; i < 8; i++)
			board.putPiece(new Pawn(Team.BLACK), "" + ((char)('a' + i)) + 7);
		
		// Towers
		board.putPiece(new Tower(Team.WHITE), "a1");
		board.putPiece(new Tower(Team.WHITE), "h1");
		board.putPiece(new Tower(Team.BLACK), "a8");
		board.putPiece(new Tower(Team.BLACK), "h8");
		
		// Knights
		board.putPiece(new Knight(Team.WHITE), "b1");
		board.putPiece(new Knight(Team.WHITE), "g1");
		board.putPiece(new Knight(Team.BLACK), "b8");
		board.putPiece(new Knight(Team.BLACK), "g8");
		
		
		// Bishops
		board.putPiece(new Bishop(Team.WHITE), "c1");
		board.putPiece(new Bishop(Team.WHITE), "f1");
		board.putPiece(new Bishop(Team.BLACK), "c8");
		board.putPiece(new Bishop(Team.BLACK), "f8");
		
		
		// Queens
		board.putPiece(new Queen(Team.WHITE), "d1");
		board.putPiece(new Queen(Team.BLACK), "d8");
		
		
		// Kings
		board.putPiece(new King(Team.WHITE), "e1");
		board.putPiece(new King(Team.BLACK), "e8");
		
		return board;
	}
}
