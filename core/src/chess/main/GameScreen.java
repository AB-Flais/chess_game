package chess.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.FitViewport;

import chess.board.Board;

public class GameScreen implements Screen {
	static final boolean delay = false;
	
	SpriteBatch batch;
	OrthographicCamera camera;
	private FitViewport fitViewport;
	
	private Board board;
	
	public GameScreen () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		board = GameMode.normal(this);
		fitViewport = new FitViewport(board.getWidth(),board.getHeight());
	}
	
	@Override
	public void show() {
		if (delay) {
			float delay = 10; // seconds

			Timer.schedule(new Task(){
			    public void run() {
			        hide();
			    }
			}, delay);
		}
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean touchDown (int x, int y, int pointer, int button) {
				int horizontalGutter = fitViewport.getLeftGutterWidth();
				int verticalGutter = fitViewport.getBottomGutterHeight();
				if (x > horizontalGutter && x < Gdx.graphics.getWidth() - horizontalGutter &&
					y > verticalGutter && y < Gdx.graphics.getHeight() - verticalGutter) {
					float width = (float) Gdx.graphics.getWidth() - horizontalGutter*2f;
					float height = (float) Gdx.graphics.getHeight() - verticalGutter*2f;
					width = (float) width/(float) board.getWidth();
					height = (float) height/(float) board.getHeight();
					
					board.select((x-horizontalGutter)/(int)width, 7-(y-verticalGutter)/(int)height);
				}
				return true;
			}
		});
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(49/255f,46/255f,43/255f,1);
		
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		board.render();	
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		fitViewport.update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		dispose();
		Gdx.app.exit();
	}

	@Override
	public void dispose() {
		board.dispose();
	}

}
