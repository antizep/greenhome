package ru.antizep.greenhouse.serial;

public interface SerialTransport {
	 void write(String data);
	    String readLine();
	    boolean isOpen();
	    void connect();
}
