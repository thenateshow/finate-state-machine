import java.util.List;
import java.util.ArrayList;

public class State{

	List<Transition> t;
	int id;
	Boolean isStart, isAccept, isReject;

	public State(){
		t = new ArrayList<Transition>();
		id = -1;
		isStart = false;
		isAccept = false;
		isReject = false;
	}

	public State(int i){
		t = new ArrayList<Transition>();
		id = i;
		isStart = false;
		isAccept = false;
		isReject = false;
	}

	public List<Transition> getTransitions(){
		return t;
	}
	public int getID(){
		return id;
	}
	public Boolean getIsStart(){
		return isStart;
	}
	public Boolean getIsAccept(){
		return isAccept;
	}
	public Boolean getIsReject(){
		return isReject;
	}

	public void setTransitions(List<Transition> tr){
		t = tr;
	}
	public void setID(int i){
		id = i;
	}
	public void setIsStart(Boolean b){
		isStart = b;
	}
	public void setIsAccept(Boolean b){
		isAccept = b;
	}
	public void setIsReject(Boolean b){
		isReject = b;
	}

	public void addTransition(Transition tr){
		t.add(tr);
	}

	public void printState(){
		System.out.println("transitions: " + t + "\nid: " + id + "\nisStart: " + isStart + "\nisAccept: " + isAccept + "\nisReject: " + isReject + "\n");
	}
}