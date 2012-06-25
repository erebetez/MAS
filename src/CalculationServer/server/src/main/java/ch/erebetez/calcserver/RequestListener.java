package ch.erebetez.calcserver;

import java.net.Socket;
import java.util.EventListener;

public interface RequestListener extends EventListener {
	void newRequest(Socket socket);

}
