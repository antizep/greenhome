package ru.antizep.greenhouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ArduinoGateway {
	Logger log = LoggerFactory.getLogger(getClass());

	private final SerialTransport transport;
	private static final int MAX_RETRIES = 3;

	public ArduinoGateway(SerialTransport transport) {
		this.transport = transport;
	}

	public synchronized String sendAndReceive(String command) {
		String result = null;
		int i = 0;
		while(result == null && i< MAX_RETRIES) {
			i++;
			try {
				if (!transport.isOpen()) {
					transport.connect();
				}
				transport.write(command);
				String response = transport.readLine();
				if (isValidResponse(response)) {
					result = response;
				} else {
					handleRetry(i, command);
				}
			} catch (InterruptedException e) {
				log.error("Во время отправки команды произошла непредвиденная ошибка",e);
			}

		}
        if (result == null) {
            throw new RuntimeException("Arduino не ответила после " + MAX_RETRIES + " попыток на команду: " + command);
        }
		return result;
	}

	private boolean isValidResponse(String response) {
		return response != null && !response.isBlank();
	}

	private void handleRetry(int attempt, String command) throws InterruptedException {
		log.warn("Попытка {}/{} не удалась для команды: {}", attempt, MAX_RETRIES, command);
		if (attempt < MAX_RETRIES) {
			Thread.sleep(200L * attempt);
		}
	}
}
