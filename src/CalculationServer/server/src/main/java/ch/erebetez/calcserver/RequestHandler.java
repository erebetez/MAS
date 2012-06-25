package ch.erebetez.calcserver;


import java.net.Socket;


import java.util.*;

import ch.erebetez.eatstarter.util.FinishedListener;

public class RequestHandler implements RequestListener, FinishedListener{

	private List<Request> requestList = new Vector<Request>();
		
	private boolean isRequestRunning = false;

	public void runRequests(){
		
		if(isRequestRunning){
			return;
		}
		
		runNextRequest();
	}
	
    private void runNextRequest(){
    	
		Iterator<Request> requests = requestList.iterator();
		
		if (requests.hasNext()){
			isRequestRunning = true;
			requests.next().run();
			requests.remove();
		} else {
			isRequestRunning = false;
		}
    }

	public void newRequest(Socket socket) {
		requestList.add(new Request(socket, this));
		runRequests();
	}

	public void finished() {
		runNextRequest();
	}
	
	
}
