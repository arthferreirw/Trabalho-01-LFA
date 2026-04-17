import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FilePersistence {

    public void saveToFile(String texto, String filePath) {  //salva o arquivo e escreve dentro do arquivo
        try {
            FileWriter arq = new FileWriter(filePath);
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.print(texto);
            arq.close();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo: " + filePath, e);
        }
    }

    public String loadFromFile(String filePath) { //le o arquivo e retorna o conteudo
        String conteudoLido = "";
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("\\Z");
            while (scanner.hasNext()) {
                conteudoLido += scanner.next();
            }
            scanner.close();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo: " + filePath, e);
        }
        return conteudoLido;
    }
}
