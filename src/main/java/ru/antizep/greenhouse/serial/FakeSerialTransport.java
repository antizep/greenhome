package ru.antizep.greenhouse.serial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class FakeSerialTransport implements SerialTransport {

	private static final Logger LOG = LoggerFactory.getLogger(FakeSerialTransport.class);

	@Override
	public void write(String data) {
		LOG.info("[FAKE SERIAL] Отправка данных: {}", data);
	}

	@Override
	public String readLine() {
		LOG.info("[FAKE SERIAL] Чтение ответа...");
		return "OK#";
	}

	@Override
	public boolean isOpen() {
		return true;
	}

	@Override
	public void connect() {
		LOG.info("[FAKE SERIAL] Имитация подключения к порту");

	}

}
