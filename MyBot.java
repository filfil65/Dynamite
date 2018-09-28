import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.softwire.dynamite.bot.Bot;
import com.softwire.dynamite.game.Gamestate;
import com.softwire.dynamite.game.Move;

public class MyBot implements Bot {
	private int dynamiteUsed;
	private ArrayList<Move> myMoves;
	private ArrayList<Move> enemyMoves;

	// Constructor
	public MyBot(){
		myMoves = new ArrayList<Move>();
		enemyMoves = new ArrayList<Move>();
	}
	
	@Override
	public Move makeMove(Gamestate gamestate) {
		gamestate.getRounds().get(0); // Get the first round
		Move myMove = getRandom();
		return myMove;
	}
	 
	private static Move getDynamite() {
		return Move.D;
	}
	
	private static Move getRandom() {
		int rand = new Random().nextInt();
		return Move.values()[rand];
	}
}
