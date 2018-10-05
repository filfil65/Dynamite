package Dynamighty.Dynamighty;

import java.util.ArrayList;
import java.util.List;

import com.softwire.dynamite.bot.Bot;
import com.softwire.dynamite.game.Gamestate;
import com.softwire.dynamite.game.Move;
import com.softwire.dynamite.game.Round;

public class Main 
{
	public static void main(String arg[]) {
		Bot b1 = new NewMyBotOld();
		Bot b2 = new MyBotOld();
		fight(b1, b2);
	}

	public static void fight(Bot SamBotOne, Bot SamBotTwo) {
		System.out.println("start");
		Gamestate gamestate = new Gamestate();
		gamestate.setRounds(new ArrayList<Round>());
		int p1score = 0;
		int p2score = 0;
		int i = 0;
		int pointstoget = 1;
		while (i < 2500 && p1score<1000 && p2score<1000) {
			Move p1move = SamBotOne.makeMove(gamestate);
			Move p2move = SamBotTwo.makeMove(gamestate);
			Round r = new Round();
			r.setP1(p1move);
			r.setP2(p2move);
			List<Round> newRoundList = gamestate.getRounds();
			newRoundList.add(r);
			gamestate.setRounds(newRoundList);
			if (p1move == Move.R) {
				if (p2move == Move.S || p2move == Move.W) {
					p1score = p1score+pointstoget;
					pointstoget = 1;
				} else if (p2move == Move.P || p2move == Move.D) {
					p2score = p2score+pointstoget;
					pointstoget = 1;
				} else {pointstoget++;}
			}
			if (p1move == Move.P) {
				if (p2move == Move.R || p2move == Move.W) {
					p1score = p1score+pointstoget;
					pointstoget = 1;
				} else if (p2move == Move.S || p2move == Move.D) {
					p2score = p2score+pointstoget;
					pointstoget = 1;
				} else {pointstoget++;}
			}
			if (p1move == Move.S) {
				if (p2move == Move.P || p2move == Move.W) {
					p1score = p1score+pointstoget;
					pointstoget = 1;
				} else if (p2move == Move.R || p2move == Move.D) {
					p2score = p2score+pointstoget;
					pointstoget = 1;
				} else {pointstoget++;}
			}
			if (p1move == Move.D) {
				if (p2move == Move.S || p2move == Move.R || p2move == Move.P) {
					p1score = p1score+pointstoget;
					pointstoget = 1;
				} else if (p2move == Move.W) {
					p2score = p2score+pointstoget;
					pointstoget = 1;
				} else {pointstoget++;}
			}
			if (p1move == Move.W) {
				if (p2move == Move.D) {
					p1score = p1score+pointstoget;
					pointstoget = 1;
				} else if (p2move == Move.P || p2move == Move.R || p2move == Move.S) {
					p2score = p2score+pointstoget;
					pointstoget = 1;
				} else {pointstoget++;}
			}
			i++;
			Round round = gamestate.getRounds().get(gamestate.getRounds().size()-1);
				System.out.println(round.getP1() + " " + round.getP2());
			
		}
		if (p1score>p2score) {
			System.out.println("Player one wins " + p1score + " to " + p2score);
		} else if (p1score<p2score) {
			System.out.println("Player two wins " + p2score + " to " + p1score);
		} else {System.out.println("IT WAS A DRAW! " + p1score + " POINTS EACH!!");}

	}
}
