import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        FilePersistence persistence = new FilePersistence();
        SerializadorCSVAutomato serializador = new SerializadorCSVAutomato();

        Map<Integer, Automato> automatos = carregarAutomatos(persistence, serializador);
        if (automatos.size() < 4) {
            System.out.println("Nao foi possivel carregar os 4 automatos. Verifique os arquivos CSV.");
            return;
        }

        menu(automatos, persistence, serializador);
    }

    private static Map<Integer, Automato> carregarAutomatos(FilePersistence persistence,
                                                             SerializadorCSVAutomato serializador) {
        Map<Integer, Automato> automatos = new HashMap<>();

        for (int i = 1; i <= 4; i++) {
            String arquivo = "automato" + i + ".csv";
            String conteudo = persistence.loadFromFile(arquivo);
            if (conteudo == null || conteudo.isEmpty()) {
                System.out.println("Erro ao carregar " + arquivo + ": arquivo vazio ou inexistente.");
                continue;
            }
            Automato automato = serializador.fromCSV(conteudo);
            if (automato != null) {
                automatos.put(i, automato);
                System.out.println("Carregado: " + arquivo);
            } else {
                System.out.println("Erro ao carregar " + arquivo + ": automato nulo ou erro de conversao.");
            }
        }

        return automatos;
    }

    private static void menu(Map<Integer, Automato> automatos,
                            FilePersistence persistence,
                            SerializadorCSVAutomato serializador) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nSimulador de automatos");
        System.out.println("Escolha um automato de 1 a 4 para testar sentencas.");
        System.out.println("Digite 9 para exportar um automato para CSV.");
        System.out.println("Digite 8 para verificar palavras de um arquivo.");
        System.out.println("Digite 0 para sair.\n");

        while (true) {
            System.out.print("Opcao (0-4, 8 ou 9): ");
            String opcao = scanner.nextLine().trim();

            if ("0".equals(opcao)) {
                System.out.println("Encerrando.");
                break;
            }

            if ("9".equals(opcao)) {
                exportarAutomato(scanner, automatos, persistence, serializador);
                continue;
            }

            if ("8".equals(opcao)) {
                verificarArquivoPalavras(scanner, automatos);
                continue;
            }

            if ("1".equals(opcao) || "2".equals(opcao) || "3".equals(opcao) || "4".equals(opcao)) {
                int indice = Integer.parseInt(opcao);
                Automato automato = automatos.get(indice);
                if (automato == null) {
                    System.out.println("Automato inexistente. Escolha entre 1 e 4.");
                    continue;
                }

                System.out.print("Digite a sentenca (alfabeto {a,b}): ");
                String sentenca = scanner.nextLine().trim();
                Automato.ResultadoProcessamento resultado = automato.verificarSentenca(sentenca);

                if (resultado.isAceita()) {
                    System.out.println("ACEITA - " + resultado.getMensagem());
                } else {
                    System.out.println("REJEITA - " + resultado.getMensagem());
                }
                System.out.println();
            } else {
                System.out.println("Opcao invalida. Digite 0, 1, 2, 3, 4 ou 9.");
            }
        }

        scanner.close();
    }

    private static void exportarAutomato(Scanner scanner,
                                         Map<Integer, Automato> automatos,
                                         FilePersistence persistence,
                                         SerializadorCSVAutomato serializador) {
        System.out.print("Qual automato deseja exportar (1-4)? ");
        String valor = scanner.nextLine().trim();

        if (!"1".equals(valor) && !"2".equals(valor) && !"3".equals(valor) && !"4".equals(valor)) {
            System.out.println("Valor invalido.\n");
            return;
        }

        int indice = Integer.parseInt(valor);
        Automato automato = automatos.get(indice);
        if (automato == null) {
            System.out.println("Automato inexistente.\n");
            return;
        }
        String nomeArquivo = "automato" + indice + "_exportado.csv";
        String csv = serializador.toCSV(automato);
        persistence.saveToFile(csv, nomeArquivo);
        System.out.println("Automato exportado para: " + nomeArquivo + "\n");
    }

    private static void verificarArquivoPalavras(Scanner scanner, Map<Integer, Automato> automatos) {
        System.out.print("Qual automato deseja usar (1-4)? ");
        String valor = scanner.nextLine().trim();
        if (!"1".equals(valor) && !"2".equals(valor) && !"3".equals(valor) && !"4".equals(valor)) {
            System.out.println("Valor invalido.\n");
            return;
        }
        int indice = Integer.parseInt(valor);
        Automato automato = automatos.get(indice);
        if (automato == null) {
            System.out.println("Automato inexistente.\n");
            return;
        }
        System.out.print("Digite o nome do arquivo de palavras: ");
        String nomeArquivo = scanner.nextLine().trim();
        java.io.File file = new java.io.File(nomeArquivo);
        if (!file.exists() || !file.isFile()) {
            System.out.println("Arquivo nao encontrado: " + nomeArquivo + "\n");
            return;
        }
        try (java.util.Scanner fileScanner = new java.util.Scanner(file)) {
            int linha = 0;
            while (fileScanner.hasNextLine()) {
                String palavra = fileScanner.nextLine().trim();
                linha++;
                if (palavra.isEmpty()) continue;
                Automato.ResultadoProcessamento resultado = automato.verificarSentenca(palavra);
                if (resultado.isAceita()) {
                    System.out.println("[Linha " + linha + "] " + palavra + ": ACEITA - " + resultado.getMensagem());
                } else {
                    System.out.println("[Linha " + linha + "] " + palavra + ": REJEITA - " + resultado.getMensagem());
                }
            }
            System.out.println("\nFim da verificacao do arquivo.\n");
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage() + "\n");
        }
    }
}