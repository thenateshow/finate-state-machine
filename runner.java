import java.io.File;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.lang.String;

class runner{

	public static State findState(int id, List<State> stateList){
		for(State s : stateList){
			if(s.getID() == id){
				return s;
			}
		}
		return null;
	}

	public static State[] parseFile(File f){

		List<State> statesList = new ArrayList<State>();
		State[] states = new State[0];
		String firstTransition = "";

		try{
			Scanner s = new Scanner(f);
			int stateCount = 0;

			while(s.hasNextLine()){

				String line = s.nextLine();

				//state
				if(line.substring(0,1).equals("s")){
					//stateCount++;
					line = line.substring(5).replaceAll("\\s+","");
					int idlen = 0;
					while(line.length() > idlen){
						if(Character.isDigit(line.charAt(idlen))){
							idlen++;
						}
						else{
							
							break;
						}
					}
					int currID = Integer.parseInt(line.substring(0, idlen));
					State newState = new State(currID);
					if(line.length() > idlen){
						if(line.substring(idlen,idlen+1).equals("s")){
							newState.setIsStart(true);
						}
						if(line.substring(idlen,idlen+1).equals("a")){
							newState.setIsAccept(true);
						}
						if(line.substring(idlen,idlen+1).equals("r")){
							newState.setIsReject(true);
						}
					}

					statesList.add(newState);
					if(newState.getID() > stateCount){
						stateCount = newState.getID();
					}
				}

				//transition
				else{
					firstTransition = line;
					break;
				}
			}

			List<Transition> transitionsList = new ArrayList<Transition>();

			//first transition
			firstTransition = firstTransition.substring(10).trim();
			Transition firstT = new Transition();
			String[] values = firstTransition.split("\\s+");
			//start state
			//firstT.setStart(states[Integer.parseInt(values[0])]);
			int tempStartID = Integer.parseInt(values[0]);
			State startTemp = findState(tempStartID, statesList);
			if(startTemp == null){
				//stateCount++;
				State newState = new State(tempStartID);
				statesList.add(newState);
				startTemp = newState;
				if(newState.getID() > stateCount){
					stateCount = newState.getID();
				}
			}
			firstT.setStart(startTemp);
			//read
			firstT.setRead(values[1].charAt(0));
			//end state
			//firstT.setEnd(states[Integer.parseInt(values[2])]);
			int tempEndID = Integer.parseInt(values[2]);
			State endTemp = findState(tempEndID, statesList);
			if(endTemp == null){
				//stateCount++;
				State newState = new State(tempEndID);
				statesList.add(newState);
				endTemp = newState;
				if(newState.getID() > stateCount){
					stateCount = newState.getID();
				}
			}
			firstT.setEnd(endTemp);
			//write
			firstT.setWrite(values[3].charAt(0));
			//move
			firstT.setMove(values[4].charAt(0));

			transitionsList.add(firstT);
			
			
			
			//transitions
			while(s.hasNextLine()){
				String line = s.nextLine();
				line = line.substring(10).trim();
				Transition temp = new Transition();
				String[] val = line.split("\\s+");
				//start state
				//temp.setStart(states[Integer.parseInt(val[0])]);
				tempStartID = Integer.parseInt(val[0]);
				startTemp = findState(tempStartID, statesList);
				if(startTemp == null){
					//stateCount++;
					State newState = new State(tempStartID);
					statesList.add(newState);
					startTemp = newState;
					if(newState.getID() > stateCount){
						stateCount = newState.getID();
					}
				}
				temp.setStart(startTemp);
				//read
				temp.setRead(val[1].charAt(0));
				//end state
				//temp.setEnd(states[Integer.parseInt(val[2])]);
				tempEndID = Integer.parseInt(val[2]);
				endTemp = findState(tempEndID, statesList);
				if(endTemp == null){
					//stateCount++;
					State newState = new State(tempEndID);
					statesList.add(newState);
					endTemp = newState;
					if(newState.getID() > stateCount){
						stateCount = newState.getID();
					}
				}
				temp.setEnd(endTemp);
				//write
				temp.setWrite(val[3].charAt(0));
				//move
				temp.setMove(val[4].charAt(0));
	
				transitionsList.add(temp);
				//states[temp.getStart().getID()].addTransition(temp);
			}

			states = new State[stateCount + 1];
			
			for (State i : statesList){
				states[i.getID()] = i;
			}

			for(Transition t : transitionsList){
				states[t.getStart().getID()].addTransition(t);
			}
			
			s.close();

		}

		catch (FileNotFoundException e){
			System.out.println("no file found");
		}

		return states;
	}

	public static String execute(String input, State[] states, int maxSteps){
		List<Character> tape = new ArrayList<Character>();
		String output = "";
		String outputResult = ""; //accept, reject, or quit
		State start = null;
		State current = null;
		int pos = 0;
		int steps = 0;

		for(State s : states){
			if(s != null && s.getIsStart() == true){
				start = s;
				break;
			}
		}
		current = start;
		char[] inputTape = input.toCharArray();
		for(char c : inputTape){
			tape.add(c);
		}

		while(steps <= maxSteps){
			if(current.getIsAccept()){
				outputResult = "accept";
				break;
			}
			if(current.getIsReject()){
				outputResult = "reject";
				break;
			}

			Transition currentTransition = null;
			for(Transition t : current.getTransitions()){
				if(pos >= tape.size()){
					if(t.getRead() == '_'){
						currentTransition = t;
						break;
					}
				}
				else if(t.getRead() == tape.get(pos) || (t.getRead() == '_' && tape.get(pos) == ' ')){
					currentTransition = t;
					break;
				}
			}

			current = currentTransition.getEnd();
			if(pos < tape.size()){
				if(currentTransition.getWrite() == '_'){
					tape.set(pos, ' ');
				}
				else{
					tape.set(pos, currentTransition.getWrite());
				}
			}
			else{
				if(currentTransition.getWrite() == '_'){
					tape.add(' ');
				}
				else{
					tape.add(currentTransition.getWrite());
				}
			}

			if(currentTransition.getMove() == 'R'){
				pos++;
			}
			else if(currentTransition.getMove() == 'L'){
				pos--;
			}

			steps++;
		}

		if(steps > maxSteps){
			outputResult = "quit";
		}

		List<Character> tape2 = new ArrayList<Character>();
		if(outputResult.equals("quit") && pos - 1 >= 0){
			tape2.add(tape.get(pos - 1));
		}
		for(int i = pos; i < tape.size(); i++){
			tape2.add(tape.get(i));
		}
		for(char c : tape2){
			output = output + c;
		}

		output = output.trim() + " " + outputResult;

		return output;
	}

	public static void main(String[] args){
		if (args.length != 3){
			System.out.println("\nnot the correct arguments!\n");
			throw new IllegalArgumentException();
		}

		File file = new File(args[0]);
		String input = args[1];
		int maxSteps = Integer.parseInt(args[2]);

		State[] states = parseFile(file);
		/*
		for (int i = 0; i < states.length; i++){
			states[i].printState();
		}
		System.out.println(input);
		System.out.println(maxSteps);
		*/
		System.out.println(execute(input, states, maxSteps));

	}

}





















