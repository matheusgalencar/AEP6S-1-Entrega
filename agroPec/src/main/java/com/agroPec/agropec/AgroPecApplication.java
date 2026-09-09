package com.agroPec.agropec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@SpringBootApplication
public class AgroPecApplication {
	public static void main(String[] args) throws IOException {
		loadEnv();
		SpringApplication.run(AgroPecApplication.class, args);

	}
	private static void loadEnv() throws IOException {
		Path envFile = Path.of(".env");
		if (!Files.exists(envFile)) {
			System.out.println("AVISO: .env não encontrado em " + envFile.toAbsolutePath());
			return;
		}
		List<String> linhas = Files.readAllLines(envFile);
		for (String linha : linhas) {
			linha = linha.trim();
			if (linha.isEmpty() || linha.startsWith("#")) continue;
			int idx = linha.indexOf('=');
			if (idx == -1) continue;
			String chave = linha.substring(0, idx).trim();
			String valor = linha.substring(idx + 1).trim();
			System.setProperty(chave, valor);
		}
	}
}
