package chess.main;

import com.badlogic.gdx.Game;

public class Main extends Game {
	
	@Override
	public void create () {
		GameScreen gameScreen = new GameScreen();
		this.setScreen(gameScreen);
	}

	public void render () {
		super.render();
	}
	
	public void dispose () {
	}
}
