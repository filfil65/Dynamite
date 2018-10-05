package Dynamighty.Dynamighty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.softwire.dynamite.bot.Bot;
import com.softwire.dynamite.game.Gamestate;
import com.softwire.dynamite.game.Move;

public class NewMyBotOld implements Bot {
	private Gamestate gamestate;
	private int myDynamite;
	private int enemyDynamite;
	private ArrayList<Move> myMoves;
	private ArrayList<Move> enemyMoves;
	private ArrayList<String> resultList;
	// public Move myMove;
	private static int round;
	private int tieCount;
	private boolean antiDynamite;
	private boolean antiWater;
	private boolean antiDvD;
	private int inaRow;
	private HashMap<Move, Move> counterMove;
	private boolean dynamiteFlag;
	private boolean enemyDynamiteFlag;
	private ArrayList<Move> nextMove;

	// Constructor
	public NewMyBotOld() {
		myMoves = new ArrayList<Move>();
		enemyMoves = new ArrayList<Move>();
		resultList = new ArrayList<String>();
		gamestate = new Gamestate();
		myDynamite = 96;
		enemyDynamite = 100;
		round = 0;
		antiDynamite = false;
		antiWater = false;
		antiDvD = false;
		tieCount = 0;
		dynamiteFlag = true; // true if we have dynamites left
		counterMove = new HashMap<Move, Move>();
		counterMove.put(Move.R, Move.P);
		counterMove.put(Move.P, Move.S);
		counterMove.put(Move.P, Move.S);
		counterMove.put(Move.D, Move.W);
		counterMove.put(Move.W, getRandom(false));
		nextMove = new ArrayList<Move>();

	}

	public Move makeMove(Gamestate gamestate) {
		this.gamestate = gamestate;
		
		if (gamestate.getRounds().size() != 0) {

			moveRecorder();
			scoreRecorder();
			dynamiteCounter();
			tieCheck();


			
			if(tieCount >= 1) { // begin Barrage!
				int rand = new Random().nextInt(4);
				switch(rand) {
				case 0:
					nextMove = new ArrayList<Move>();
					nextMove.add(getDynamite());
					nextMove.add(getDynamite());
					nextMove.add(getDynamite());
					break;
				case 1:
					nextMove = new ArrayList<Move>();
					nextMove.add(Move.W);
					nextMove.add(Move.W);
					nextMove.add(Move.W);
					break;
				case 2: 
					nextMove = new ArrayList<Move>();
					nextMove.add(getRandom(false)); //no dynamite
					nextMove.add(getRandom(false));
					nextMove.add(getRandom(false));
					break;

				case 3:
					nextMove = new ArrayList<Move>();
					nextMove.add(getRandom(dynamiteFlag)); //maybe dynamite
					nextMove.add(getRandom(dynamiteFlag));
					nextMove.add(getRandom(dynamiteFlag));
					break;
				}
			}
			
			// if we have a previousely set move
			if(!nextMove.isEmpty()) {
				Move myMove = nextMove.get(0);
				nextMove.remove(0);
				roundAdder();
				return myMove;
			}

			roundAdder();
			return getRandom(dynamiteFlag);
		}

		else {
			roundAdder();
			return getRandom(dynamiteFlag);
		}

	}

	private void moveRecorder() {
		myMoves.add(gamestate.getRounds().get(round - 1).getP1());
		enemyMoves.add(gamestate.getRounds().get(round - 1).getP2());
	}

	private void dynamiteCounter() {
		if (myMoves.get(myMoves.size() - 1) == Move.D) {
			myDynamite--;
			if (myDynamite == 0) {
				dynamiteFlag = false;
			}
		}
		if (enemyMoves.get(enemyMoves.size() - 1) == Move.D) {
			enemyDynamite--;
			if (enemyDynamite == 0) {
				enemyDynamiteFlag = false;
			}
		}
	}

	private boolean tieCheck() {
		if (myMoves.get(myMoves.size() - 1) == enemyMoves.get(enemyMoves.size() - 1)) {
			tieCount++;
//			if (tieCount > 1)
//				nextMove.clear();
			return true;
		} else {
			tieCount = 0;
			return false;
		}
	}

	private void scoreRecorder() {
		int points = 1;
		if(myMoves.get(myMoves.size() - 1) == enemyMoves.get(enemyMoves.size() - 1)) {
			
		}
		// TODO Auto-generated method stub

	}

	private Move getDynamite() {
		if (myDynamite == 0) {
			Move myMove = getRandom(false);
			return myMove;
		}
		return Move.D;
	}

	private static void roundAdder() {
		round++;
	}

	private Move getMove(String move) {
		Move myMove;
		switch (move) {
		case "r":
			myMove = Move.R;
			break;
		case "p":
			myMove = Move.P;
			break;
		case "s":
			myMove = Move.S;
			break;
		case "w":
			myMove = Move.W;
			break;
		case "d":
			myMove = Move.D;
			break;
		default:
			myMove = Move.R;
		}
		return myMove;
	}

	private Move getCounterMove(Move enemyMove) {
		Move myMove = counterMove.get(enemyMove);
		return myMove;
	}

	private Move getRandom(boolean dynamiteFlag) {
		int rand;// = new Random().nextInt(3);
		Move myMove;// = Move.R;

		if (dynamiteFlag == true) {
			rand = new Random().nextInt(4);
		} else {
			rand = new Random().nextInt(3);
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
		default:
			myMove = Move.R;
		}
		return myMove;
	}

}
