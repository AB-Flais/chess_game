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
	static SpriteBatch batch;
	OrthographicCamera camera;
	
	private BitmapFont lightFont, darkFont;

	private Square[][] squares;
	private Square lastSquareSelected;
	
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
		
		Board.batch = batch;
		this.camera = camera;
		
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
	
	public Square getSquare(int x, int y) {
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
				squares[i][j].render(i,j,scale);
	}

	public void select(int x, int y) {
		if (lastSquareSelected == getSquare(x,y)) {
			for (Square[] row: squares)
				for (Square square: row)
					square.setTexture(SquareState.DEFAULT);
			lastSquareSelected = null;
		} else {
			if (lastSquareSelected != null)
				for (Square[] row: squares)
					for (Square square: row)
						square.setTexture(SquareState.DEFAULT);
			getSquare(x,y).select();
			lastSquareSelected = getSquare(x,y);
			
			boolean[][] possibleMoves = getSquare(x,y).showPossibleMoves(reduceBoard(),x,y);
			
			for (int i = 0; i < width; i++)
				for (int j = 0; j < height; j++)
					if (possibleMoves[i][j])
						if (squares[i][j].getPiece() == null)
							squares[i][j].setTexture(SquareState.POSSIBLE_MOVE);
						else 
							squares[i][j].setTexture(SquareState.POSSIBLE_CAPTURE);
		}
	}
	
	private Team[][] reduceBoard() {
		Team[][] boardReduced = new Team[width][height];
		
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				boardReduced[i][j] = squares[i][j].getTeam();
				
		return boardReduced;
	}

	public void dispose() {
		Board.disposeBatch();
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
	
	public static void disposeBatch() {
		if (batch != null)
			
			batch.dispose();
	}
}
