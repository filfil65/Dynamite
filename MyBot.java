import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.softwire.dynamite.bot.Bot;
import com.softwire.dynamite.game.Gamestate;
import com.softwire.dynamite.game.Move;

public class MyBot implements Bot {
	private Gamestate gamestate;
	private int myDynamite;
	private int enemyDynamite;
	private ArrayList<Move> myMoves;
	private ArrayList<Move> enemyMoves;
	// public Move myMove;
	private int round;
	private int tie;
	private boolean antiDynamite;
	private boolean antiWater;
	private boolean antiDvD;
	private int inaRow;

	// Constructor
	public MyBot() {
		myMoves = new ArrayList<Move>();
		enemyMoves = new ArrayList<Move>();
		gamestate = new Gamestate();
		myDynamite = 100;
		enemyDynamite = 100;
		round = 0;
		antiDynamite = false;
		antiWater = false;
		antiDvD = false;
		tie = 0;
	}

	public Move makeMove(Gamestate gamestate) {
		if (round != 0) {

			// Record all moves
			myMoves.add(gamestate.getRounds().get(round - 1).getP1());
			enemyMoves.add(gamestate.getRounds().get(round - 1).getP2());

			// Count Dynamites
			if (myMoves.get(myMoves.size() - 1) == Move.D) {
				myDynamite--;
			}
			if (enemyMoves.get(enemyMoves.size() - 1) == Move.D) {
				enemyDynamite--;
			}

			// Check if tie
			if (myMoves.get(myMoves.size() - 1) == enemyMoves.get(enemyMoves.size() - 1)) {
				tie = tie + 1;
			} else {
				tie = 0;
			}
			
			// checks if antiDynamite
			if ((tie == 2) && (enemyMoves.get(enemyMoves.size() - 1) == Move.W)) {
				antiDynamite = true;
			}
			
			if(antiDvD) {
				if (myMoves.get(myMoves.size() - 1) == Move.W) {
					if((enemyMoves.get(enemyMoves.size() - 1) != Move.D)) {
						antiDvD = false;
					}
				}
			}
			
			// check if D v D
			if ((tie == 2) && (myMoves.get(myMoves.size() - 1) == enemyMoves.get(enemyMoves.size() - 1))){
				antiDvD = true;
				antiDynamite = false;
			}
			
			
//			// check if antiWater
//			if (tie == 3 && (enemyMoves.get(enemyMoves.size() - 1) == Move.R
//					|| enemyMoves.get(enemyMoves.size() - 1) == Move.S || enemyMoves.get(enemyMoves.size() - 1) == Move.P)) {
//				antiWater = true;
//			}

			// Tie strategy
			if ((tie == 1) && (antiDvD == false)) {
				return getDynamite(); // First Tie
			} else if ((tie == 1) && (antiDvD == true)) {
				return getWater();
//			} else if (tie >= 1 && enemyMoves.get(myMoves.size() - 1) == Move.D) { // On second matched dynamite
//				return getWater(); //Extinguish the dynamite
			} else if (tie >= 1 && antiDynamite) {
				return getWater();
//			} else if (tie > 1 && antiDynamite && antiWater) {
//				return getRandom();
			} 
			

//			// Dynamite Barrage
//			if (myDynamite!=1 && round>1500) {
//				if (myMoves.get(myMoves.size()-2) != Move.D) {
//					return getDynamite();
//				}
//			}

			return getRandom();
		}

		else {

			return getRandom();
		}

	}

	private Move getWater() {
		round++;
		return Move.W;
	}

	private Move getDynamite() {
		if (myDynamite == 1) {
			round++;
			return Move.S;
		}
		round++;
		return Move.D;
	}

//	private Move getRandom() {
//		int randNo = new Random().nextInt(3);
//		Move myMove = Move.R;
//
//		switch (randNo) {
//		case 0:
//			myMove = Move.R;
//			break;
//		case 1:
//			myMove = Move.P;
//			break;
//		case 2:
//			myMove = Move.S;
//			break;
//		}
//		round++;
//		return myMove;
//	}

	private Move getRandom() {
		int rand = new Random().nextInt(3);
		Move myMove = Move.R;

		if (myDynamite == 1) {
			rand = new Random().nextInt(3);
		}
		else {
			rand = new Random().nextInt(4);
		}
		
			switch (rand) {
			case 0:
				myMove = Move.R;
				break;
			case 1:
				myMove = Move.P;
				break;
			case 2:
				myMove = Move.S;
				break;
			case 3:
				myMove = Move.D;
				break;
			} 	
		
		round++;
		return myMove;
	}

}
