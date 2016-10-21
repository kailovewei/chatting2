package util;

import java.net.ServerSocket;
import java.util.Vector;

import window.ServerWindow;

public class Constant {
	public static final String SERVER_HOST = "127.0.0.1";
	public static final int LISTEN_PORT = 8000;
	public static final String CONNECT_QUIT = "quit";
	public static ServerSocket currentServer = null;
	public static Vector<Thread> T = new Vector<Thread>();
}
