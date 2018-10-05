package Dynamighty.Dynamighty;

import java.util.ArrayList;

import com.softwire.dynamite.game.Gamestate;
import com.softwire.dynamite.game.Move;
import com.softwire.dynamite.game.Round;

public class GamestateAnalyzer {
	private Gamestate gamestate;
	private ArrayList<Move> myMoves;
	private ArrayList<Move> enemyMoves;
	
	public GamestateAnalyzer(Gamestate gamestate) {
		this.gamestate = gamestate;
	}
	
	private void moveRecorder() {
		myMoves.add(gamestate.getRounds().get(gamestate.getRounds().size() - 1).getP1());
		enemyMoves.add(gamestate.getRounds().get(gamestate.getRounds().size() - 1).getP2());
	}
	
}
