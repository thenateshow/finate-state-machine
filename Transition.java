import java.util.List;
import java.util.ArrayList;

public class Transition{

	State start, end;
	char read, write, move;

	public Transition(){
		start = null;
		end = null;
		read = ' ';
		write = ' ';
		move = ' ';
	}

	public Transition(State s, State e, char r, char w, char m){
		start = s;
		end = e;
		read = r;
		write = w;
		move = m;
	}

	public State getStart(){
		return start;
	}
	public State getEnd(){
		return end;
	}
	public char getRead(){
		return read;
	}
	public char getWrite(){
		return write;
	}
	public char getMove(){
		return move;
	}

	public void setStart(State s){
		start = s;
	}
	public void setEnd(State s){
		end = s;
	}
	public void setRead(char c){
		read = c;
	}
	public void setWrite(char c){
		write = c;
	}
	public void setMove(char c){
		move = c;
	}

	public void printTransition(){
		System.out.println(start.getID() + "," + read + "->" + end.getID() + "," + write + "," + move + ", ");
	}
}