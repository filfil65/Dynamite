package Dynamighty.Dynamighty;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.softwire.dynamite.bot.Bot;
import com.softwire.dynamite.game.Gamestate;
import com.softwire.dynamite.game.Move;

public class MyBot implements Bot {
	private Gamestate gamestate;
	private int myDynamite;
	private int enemyDynamite;
	private ArrayList<Move> myMoves;
	private ArrayList<Move> enemyMoves;
	private ArrayList<String> resultList;
	// public Move myMove;
	private int round;
	private int tieCount;
	private boolean antiDynamite;
	private boolean antiWater;
	private boolean antiDvD;
	private int inaRow;
	private HashMap<Move, Move> counterMove;
	private boolean dynamiteFlag;
	private boolean enemyDynamiteFlag;
	private ArrayList<Move> tiePattern;
	private ArrayList<Move> nextMove;
	
	public GamestateAnalyzer GA;

	// Constructor
	public MyBot() {
		tiePattern = new ArrayList<Move>();
		myMoves = new ArrayList<Move>();
		enemyMoves = new ArrayList<Move>();
		resultList = new ArrayList<String>();
		gamestate = new Gamestate();
		myDynamite = 98;
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
		counterMove.put(Move.S, Move.R);
		counterMove.put(Move.D, Move.W);
		//counterMove.put(Move.W, getRandom(false));
		nextMove = new ArrayList<Move>();
	}
	
	

	public Move makeMove(Gamestate gamestate) {
		this.gamestate = gamestate;
		if (gamestate.getRounds().size() != 0) {

			moveRecorder();
			scoreRecorder();
			dynamiteCounter();
// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ WIP @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//			if(tieCheck() && tiePattern.isEmpty()){ //if there is a check and we don't have a pattern
//				tiePatternRecorder();
//			} else if (tieCheck() && tiePattern.isEmpty()==false) { // if a tie and we do have a pattern
//				getCounterMove(tiePattern.get(tieCount-1));
//			}
			
			// if we have a previousely set move
			if(!nextMove.isEmpty()) {
				Move myMove = nextMove.get(0);
				nextMove.remove(0);
				roundAdder();
				return myMove;
			}
			
			
//			if is a tie
			if(tieCheck()) {
//				ArrayList<Move> oneAfter = new ArrayList<Move>();
////				findMostCommon(oneAfter);
//				ArrayList<Move> twoAfter = new ArrayList<Move>();
//				ArrayList<Move> threeAfter = new ArrayList<Move>();
				Move one;// = getCounterMove(findMostCommon(oneAfter));
				Move two;// = getCounterMove(findMostCommon(twoAfter));
				Move three;// = getCounterMove(findMostCommon(threeAfter));

				for(int i = resultList.size()-1; i > 0 ; i--) {
					if(resultList.get(i).equals("tie") && (!resultList.get(i-1).equals("tie"))) {
						if((i+3) < enemyMoves.size()) {
							one = enemyMoves.get(i + 1);
							two = enemyMoves.get(i + 2);
							three = enemyMoves.get(i + 3);
							nextMove.add(getCounterMove(one));
							nextMove.add(getCounterMove(two));
							nextMove.add(getCounterMove(three));
							break;
						}
					}
				}
//				Move one = getCounterMove(findMostCommon(oneAfter));
//				Move two = getCounterMove(findMostCommon(twoAfter));
//				Move three = getCounterMove(findMostCommon(threeAfter));

			}
			
			// ################ OLD #########################
//			// checks if antiDynamite
//			if ((tieCount == 2) && (enemyMoves.get(enemyMoves.size() - 1) == Move.W)) {
//				antiDynamite = true;
//			}
//			if (antiDvD) {
//				if (myMoves.get(myMoves.size() - 1) == Move.W) {
//					if ((enemyMoves.get(enemyMoves.size() - 1) != Move.D)) {
//						antiDvD = false;
//					}
//				}
//			}
//
//			// check if D v D
//			if ((tieCount == 2) && (myMoves.get(myMoves.size() - 1) == enemyMoves.get(enemyMoves.size() - 1))) {
//				antiDvD = true;
//				antiDynamite = false;
//			}
//
//			// // check if antiWater
//			// if (tie == 3 && (enemyMoves.get(enemyMoves.size() - 1) == Move.R
//			// || enemyMoves.get(enemyMoves.size() - 1) == Move.S ||
//			// enemyMoves.get(enemyMoves.size() - 1) == Move.P)) {
//			// antiWater = true;
//			// }
//
//			// Tie strategy
//			if ((tieCount == 1) && (antiDvD == false)) {
//				return getDynamite(); // First Tie
//			} else if ((tieCount == 1) && (antiDvD == true)) {
//				return getMove("w");
//				// } else if (tie >= 1 && enemyMoves.get(myMoves.size() - 1) == Move.D) { // On
//				// second matched dynamite
//				// return getWater(); //Extinguish the dynamite
//			} else if (tieCount >= 1 && antiDynamite) {
//				return getMove("w");
//				// } else if (tie > 1 && antiDynamite && antiWater) {
//				// return getRandom();
//			}
//
//			// // Dynamite Barrage
//			// if (myDynamite!=1 && round>1500) {
//			// if (myMoves.get(myMoves.size()-2) != Move.D) {
//			// return getDynamite();
//			// }
//			// }
			roundAdder();
			return getRandom(false);
		}

		else {
			roundAdder();
			return getRandom(false);
		}

	}
	private Move findMostCommon(ArrayList<Move> MoveList) {
		Move myMove = Move.R;
		Map<Move, Integer> counter = new HashMap<Move, Integer>();
		counter.put(Move.R, 0);
		counter.put(Move.P, 0);		
		counter.put(Move.S, 0);
		counter.put(Move.W, 0);
		counter.put(Move.D, 0);
		for(Move each : MoveList) {
			counter.replace(each, counter.get(each)+1);
		}
		int out = 0;
		for(Move each : counter.keySet()) {
			if(counter.get(each) > out) {
				out = counter.get(each);
				myMove = each;
			}
		}
		return myMove;
		
	}



	private void moveRecorder() {
		myMoves.add(gamestate.getRounds().get(gamestate.getRounds().size() - 1).getP1());
		enemyMoves.add(gamestate.getRounds().get(gamestate.getRounds().size() - 1).getP2());
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
			return true;
		} else {
			tieCount = 0;
			return false;
		}
	}

	private void scoreRecorder() {
//		int points = 1;
		if (myMoves.get(myMoves.size() - 1) == enemyMoves.get(enemyMoves.size() - 1)) { // if tie
			resultList.add("tie");
		} else if(myMoves.get(myMoves.size() - 1) == getCounterMove(enemyMoves.get(enemyMoves.size() - 1))){ // if I throw a counter to enemy, I win
			resultList.add("win");
		} else {
			resultList.add("loss"); 
		}
	}
	
	private void tiePatternRecorder() {
		tiePattern.add(enemyMoves.get(enemyMoves.size()-1));
	}

	private Move getDynamite() {
		if (myDynamite == 1) {
			return Move.S;
		}
		round++;
		return Move.D;
	}

	private void roundAdder() {
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
		Move myMove;
		if(enemyMove == Move.W) {
			myMove = getRandom(false);
		} else {
			myMove = counterMove.get(enemyMove);
		}
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
