import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SerializadorCSVAutomato {
    public String toCSV(Automato automato) {
        StringBuilder csv = new StringBuilder();
        csv.append("inicial;").append(automato.getEstadoInicial()).append("\n");

        csv.append("finais;");
        int i = 0;
        for (String estadoFinal : automato.getEstadosFinais()) {
            csv.append(estadoFinal);
            if (i < automato.getEstadosFinais().size() - 1) {
                csv.append(",");
            }
            i++;
        }
        csv.append("\n");

        for (Transicao transicao : automato.getTransicoes()) {
            csv.append(transicao.getOrigem()).append(";")
                    .append(transicao.getLetra()).append(";")
                    .append(transicao.getDestino()).append("\n");
        }

        return csv.toString();
    }

    public Automato fromCSV(String data) {
        String estadoInicial = null;
        Set<String> estadosFinais = new HashSet<>();
        List<Transicao> transicoes = new ArrayList<>();

        String[] linhas = data.split("\\r?\\n");
        for (String linhaBruta : linhas) {
            String linha = linhaBruta.trim();
            if (linha.isEmpty()) {
                continue;
            }

            String[] partes = linha.split(";", -1);
            if (partes.length >= 2 && "inicial".equalsIgnoreCase(partes[0].trim())) {
                estadoInicial = partes[1].trim();
                continue;
            }

            if (partes.length >= 2 && "finais".equalsIgnoreCase(partes[0].trim())) {
                String[] finais = partes[1].split(",");
                for (String finalEstado : finais) {
                    String valor = finalEstado.trim();
                    if (!valor.isEmpty()) {
                        estadosFinais.add(valor);
                    }
                }
                continue;
            }

            if (partes.length < 3 || partes[1].trim().isEmpty()) {
                throw new RuntimeException("Linha de transicao invalida: " + linha);
            }

            char simbolo = partes[1].trim().charAt(0);
            transicoes.add(new Transicao(partes[0].trim(), simbolo, partes[2].trim()));
        }

        if (estadoInicial == null || estadoInicial.isEmpty()) {
            throw new RuntimeException("CSV invalido: estado inicial nao informado.");
        }

        return new Automato(estadoInicial, estadosFinais, transicoes);
    }
}
