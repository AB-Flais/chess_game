package chess.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Team;
import chess.pieces.Tower;
import chess.square.Square;

/**
 * Board to play the traditional mode of chess
 * @author Aarón
 */
public class DefaultBoard {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	/**
	 * White font
	 */
	private BitmapFont lightFont; 
	/**
	 * Green font 
	 */
	private BitmapFont darkFont;
	
	
	private Square[][] squares;
	private Array<Piece> pieces;
	
	private int SQUARE_SIZE = 64;
	private int WIDTH = 8;
	private int HEIGHT = 9;

	/**
	 * White square
	 */
	private Texture lightSquareImage;
	/**
	 * Green square
	 */
	private Texture darkSquareImage;
	
	
	/**
	 * Creates a board with the traditional mode initial settings
	 */
	public DefaultBoard() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		
		lightFont = new BitmapFont();
		lightFont.setColor(0.95f,0.93f,0.832f,1); // Very light grey
		
		darkFont = new BitmapFont();
		darkFont.setColor(0.488f, 0.578f, 0.363f, 1); // Subdued green
		
		squares = new Square[WIDTH][HEIGHT];
		pieces = new Array<>();
		
		lightSquareImage = new Texture(Gdx.files.internal("light_square.png"));
		darkSquareImage = new Texture(Gdx.files.internal("dark_square.png"));
		
		buildBoardSquares();
		placePieces();
	}
	
	// This method is built with constants so that I'm able to build boards with other sizes with it, just changing the constants
	/**
	 * Build an empty board with the dimensions depending on board's WIDTH and HEIGHT variables
	 */
	private void buildBoardSquares() {
		camera.setToOrtho(false,SQUARE_SIZE*WIDTH,SQUARE_SIZE*HEIGHT);
		
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				squares[i][j] = new Square(SQUARE_SIZE * i, SQUARE_SIZE * j);
			}
		}
	}
	
	// Other boards usually use other pieces so this method is just meant to be used for this class. Making the method adaptable to other game modes
	// seems more difficult than just extending the class and overriding it
	/**
	 * Creates a classic pieces disposition for the board
	 */
	private void placePieces() {
		// Pawns
		for (int i = 0; i < 8; i++) 
			pieces.add(new Pawn((char)('a' + i) + "2",Team.White));
		
		for (int i = 0; i < 8; i++) 
			pieces.add(new Pawn((char)('a' + i) + "7",Team.Black));
		
		// Towers
		pieces.add(new Tower("a1",Team.White));
		pieces.add(new Tower("h1",Team.White));
		pieces.add(new Tower("a8",Team.Black));
		pieces.add(new Tower("h8",Team.Black));
		
		// Knights
		pieces.add(new Knight("b1",Team.White));
		pieces.add(new Knight("g1",Team.White));
		pieces.add(new Knight("b8",Team.Black));
		pieces.add(new Knight("g8",Team.Black));
		
		// Bishops
		pieces.add(new Bishop("c1",Team.White));
		pieces.add(new Bishop("f1",Team.White));
		pieces.add(new Bishop("c8",Team.Black));
		pieces.add(new Bishop("f8",Team.Black));

		// Kings
		pieces.add(new King("e1",Team.White));
		pieces.add(new King("e8",Team.Black));

		// Queens
		pieces.add(new Queen("d1",Team.White));
		pieces.add(new Queen("d8",Team.Black));
	}

	public void render() {
		ScreenUtils.clear(0.61f, 0.70f, 0.48f,1);
		
		camera.update();
	
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		// Shows the squares
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				if ((i+ j%2)%2 == 0) 
					batch.draw(darkSquareImage,squares[i][j].getX() + 1, squares[i][j].getY() + 1, SQUARE_SIZE - 2, SQUARE_SIZE - 2);
				else		
					batch.draw(lightSquareImage,squares[i][j].getX() + 1, squares[i][j].getY() + 1, SQUARE_SIZE - 2, SQUARE_SIZE - 2);
			}
		}
		
		// Shows the pieces
		for (Piece piece: pieces)
			batch.draw(piece.getTexture(), piece.getX()*SQUARE_SIZE, piece.getY()*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

		// Shows row numbers on the left column
		for (int i = 0; i < HEIGHT; i++) 
			if (i%2 == 0)
				lightFont.draw(batch, "" + (i + 1), 3, SQUARE_SIZE * i + 54);
			else
				darkFont.draw(batch, "" + (i + 1), 3, SQUARE_SIZE * i + 54);
		
		// Shows column letters on the down row
		for (int i = 0; i < WIDTH; i++) 
			if (i%2 == 0)
				lightFont.draw(batch, "" + (char) (i + 97), SQUARE_SIZE * i + 55, 15);
			else
				darkFont.draw(batch, "" + (char) (i + 97), SQUARE_SIZE * i + 55, 15);
		batch.end();
	}
	
}
