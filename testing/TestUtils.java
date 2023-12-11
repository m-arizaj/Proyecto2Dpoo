package testing;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TestUtils {

	public static void escribirContenidoEnArchivo(String rutaArchivo, List<String[]> contenido) {
		try {
			FileWriter fileWriter = new FileWriter(rutaArchivo);
			for (String[] linea : contenido) {
				StringBuilder line = new StringBuilder();
				for (int i = 0; i < linea.length; i++) {
					line.append(linea[i]);
					if (i < linea.length - 1) {
						line.append(",");
					}
				}
				fileWriter.write(line.toString() + "\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
