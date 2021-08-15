package my.proj.jsongenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class JsonGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsonGeneratorApplication.class, args);

		JsonFileWriter jsonFileWriter = new JsonFileWriter();
		jsonFileWriter.writeToFile();

	}
}
