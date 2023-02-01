package chess.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Team;

public class Board {
	
	private class Position {
		private Square square;
		private int x;
		private int y;
		
		public Position() {
		}
		
		public Position(Square square, int x, int y) {
			this.square = square;
			this.x = x;
			this.y = y;
		}
		
		public Position(Position position) {
			square = position.square;
			x = position.x;
			y = position.y;
		}
		
		public void setPosition (int x, int y) { this.x = x; this.y = y; }
	}
	
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private Team turn;
	
	private BitmapFont lightFont, darkFont;

	private Square[][] squares;
	private Position lastSquareSelected;
	private Position lastMoveOrigin, lastMoveDestination;
	
	private int width, height;
	private final int scale = 100;
	
	/**
	 * 
	 * @param height
	 * @param width
	 * @param batch
	 * @param camera
	 */
	public Board(int height, int width, SpriteBatch batch, OrthographicCamera camera) {
		if (width <= 0 || height <= 0 || batch == null || camera == null) 
			throw new IllegalArgumentException();
		
		this.batch = batch;
		this.camera = camera;
		
		turn = Team.WHITE;
		
		lastSquareSelected = new Position();
		lastMoveDestination = new Position();
		lastMoveOrigin = new Position();
		
		lightFont = new BitmapFont();
		lightFont.setColor(0.95f,0.93f,0.832f,1); // Very light grey
		lightFont.getData().setScale(1.5f);
		
		darkFont = new BitmapFont();
		darkFont.setColor(0.488f, 0.578f, 0.363f, 1); // Subdued green
		darkFont.getData().setScale(1.5f);
		
		this.width = width;
		this.height = height;
		
		camera.setToOrtho(false, scale*width, scale*height);
		buildSquares();
	}

	private void buildSquares() {
		squares = new Square[width][height];
		StringBuilder coordinate = new StringBuilder();
		
		for (int i = 0; i < width; i++) 
			for (int j = 0; j < height; j++) { 
				coordinate.setLength(0);
				squares[i][j] = new Square();
			}
	}
	
	public Square getSquare(String coordinate) {
		int x, y;
		x = coordinate.charAt(0) - 97;
		y = coordinate.charAt(1) - 49;
		return squares[x][y];
	}

	public void putPiece(Piece piece, String coordinate) {
		int x, y;
		x = coordinate.charAt(0) - 97;
		y = coordinate.charAt(1) - 49;
		squares[x][y].setPiece(piece);
	}

	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	public void render() {
		camera.update();
		
		// Renders the squares
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				if ((i+ j%2)%2 == 0) 
					batch.draw(squares[i][j].getDarkTexture(), i*scale, j*scale, scale, scale);
				else
					batch.draw(squares[i][j].getLightTexture(), i*scale, j*scale, scale, scale);
		
		// Renders row numbers on the left column
		for (int i = 1; i <= height; i++) 
			if (i%2 != 0)
				lightFont.draw(batch, "" + i, scale/20, scale * i - scale/10);
			else
				darkFont.draw(batch, "" + i, scale/20, scale * i - scale/10);
		
		// Shows column letters on the down row
		for (int i = 0; i < width; i++) 
			if (i%2 == 0)
				lightFont.draw(batch, "" + (char) (i + 97), scale*i + scale*4/5, scale/5);
			else
				darkFont.draw(batch, "" + (char) (i + 97), scale*i + scale*4/5, scale/5);
		
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				squares[i][j].render(i,j,scale,batch);
	}

	public void select(int x, int y) {
		Square square = squares[x][y]; // Square the user has selected
		
		// If the square selected is the same already selected, deselect it
		if (lastSquareSelected.square == square) {
			clearBoard();
			lastSquareSelected.square = null;
			
		// If the square selected is a valid move
		} else if (square.getState() == SquareState.POSSIBLE_CAPTURE || square.getState() == SquareState.POSSIBLE_MOVE) {
			// En passant
			if (square.getPiece() == null && square.getState() == SquareState.POSSIBLE_CAPTURE)
				squares[x][y - 1 * ((Pawn) lastSquareSelected.square.getPiece()).getDirection()].setPiece(null);
			
			// Move the pieces
			square.setPiece(lastSquareSelected.square.getPiece());
			lastSquareSelected.square.setPiece(null);
			lastSquareSelected.square.setState(SquareState.SELECTED);
			
			// Save last positions
			lastMoveOrigin = new Position(lastSquareSelected);
			lastMoveDestination = new Position(square,x,y);

			// Clean up the board
			lastMoveDestination.square.setState(SquareState.SELECTED);
			lastSquareSelected.square = null;
			clearBoard();
			turn = turn==Team.WHITE?Team.BLACK:Team.WHITE;
			
		// It's not the same square neither a valid move
		} else {
			clearBoard();
			square.setState(SquareState.SELECTED);
			lastSquareSelected.square = square;
			lastSquareSelected.x = x; lastSquareSelected.y = y;
			
			// If the square selected contains a piece and it's team turn show the possible moves
			if (square.getPiece() != null && square.getTeam() == turn) {
				boolean[][] possibleMoves = squares[x][y].showPossibleMoves(reduceBoard(),x,y);
				
				for (int i = 0; i < width; i++)
					for (int j = 0; j < height; j++)
						if (possibleMoves[i][j])
							// En passant
							if (lastSquareSelected.square.getPiece() instanceof Pawn && squares[i][j].getPiece() == null && lastSquareSelected.x != i && lastSquareSelected.y != j)
								squares[i][j].setState(SquareState.POSSIBLE_CAPTURE); 
							else
								squares[i][j].setState(squares[i][j].getPiece() == null?SquareState.POSSIBLE_MOVE:SquareState.POSSIBLE_CAPTURE);
				
			}
		}
	}
	
	/**
	 * Set all squares' state to default, apart from lastMovementOrigin and lastMovementDestination
	 */
	// BUG
	private void clearBoard() {
		for (Square[] row: squares)
			for (Square square: row)
				if (square != lastMoveOrigin.square && square != lastMoveDestination.square)
					square.setState(SquareState.DEFAULT);
	}
	
	private Team[][] reduceBoard() {
		Team[][] boardReduced = new Team[width][height];
		
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				boardReduced[i][j] = squares[i][j].getTeam();
		
		// En passant 
		if (lastMoveDestination.square != null && lastMoveDestination.square.getPiece() instanceof Pawn && Math.abs(lastMoveDestination.y - lastMoveOrigin.y) == 2) {
			int x = lastMoveDestination.x; int y = lastMoveDestination.y;
			
			boardReduced[x][y] = null;
			boardReduced[x][y - 1 * ((Pawn)squares[x][y].getPiece()).getDirection()] = squares[x][y].getTeam();
		}
		
		return boardReduced;
	}

	public void dispose() {
		batch.dispose();
		lightFont.dispose();
		darkFont.dispose();
		for (Square[] row: squares) 
			for (Square square: row)
				square.dispose();
		Square.light.dispose();
		Square.dark.dispose();
		Square.darkSelected.dispose();
		Square.lightSelected.dispose();
		Square.possibleCapture.dispose();
		Square.lightPossibleMove.dispose();
		Square.darkPossibleMove.dispose();
	}
}
