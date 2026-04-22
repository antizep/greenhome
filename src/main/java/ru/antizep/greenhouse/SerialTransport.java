package ru.antizep.greenhouse;

public interface SerialTransport {
	 void write(String data);
	    String readLine();
	    boolean isOpen();
	    void connect();
}
