import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Automato {
    private String estadoInicial;
    private Set<String> estadosFinais;
    private List<Transicao> transicoes;

    public Automato(String estadoInicial, Set<String> estadosFinais, List<Transicao> transicoes) {
        this.estadoInicial = estadoInicial;
        this.estadosFinais = estadosFinais;
        this.transicoes = transicoes;
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public Set<String> getEstadosFinais() {
        return estadosFinais;
    }

    public List<Transicao> getTransicoes() {
        return transicoes;
    }

    public boolean aceita(String sentenca) {
        return verificarSentenca(sentenca).isAceita();
    }

    public ResultadoProcessamento verificarSentenca(String sentenca) {
        if (sentenca == null || sentenca.isEmpty()) {
            return new ResultadoProcessamento(true, "Sentenca nula ou vazia: aceita por definição");
        }

        validarDeterminismo();
        List<Transicao> tabela = transicoes;
        String estadoAtual = estadoInicial;

        for (int i = 0; i < sentenca.length(); i++) {
            char simbolo = sentenca.charAt(i); // Pega o símbolo atual

            if (simbolo != 'a' && simbolo != 'b') {
                return new ResultadoProcessamento(false,
                        "Simbolo invalido '" + simbolo + "' na posicao " + i + ". Apenas 'a' e 'b' sao permitidos");
            }

            Transicao transicao = buscarTransicao(tabela, estadoAtual, simbolo);
            if (transicao == null) {
                return new ResultadoProcessamento(false,
                        "Nao existe transicao para (" + estadoAtual + ", " + simbolo);
            }

            estadoAtual = transicao.getDestino();
        }

        if (estadosFinais.contains(estadoAtual)) {
            return new ResultadoProcessamento(true,
                    "Sentenca aceita: terminou no estado final " + estadoAtual);
        }

        return new ResultadoProcessamento(false,
                "Sentenca rejeitada: terminou no estado " + estadoAtual + ", que nao eh final.");
    }

    private void validarDeterminismo() {
        List<Transicao> tabelaParcial = new ArrayList<>();

        for (Transicao transicao : transicoes) {
            if (buscarTransicao(tabelaParcial, transicao.getOrigem(), transicao.getLetra()) != null) {
                System.err.println("Automato invalido: duas transicoes para ("
                        + transicao.getOrigem() + ", " + transicao.getLetra() + ").");
            }
            tabelaParcial.add(transicao);
        }
    }

    private Transicao buscarTransicao(List<Transicao> tabela, String origem, char simbolo) {
        for (Transicao transicao : tabela) {
            if (transicao.getOrigem().equals(origem) && transicao.getLetra() == simbolo) {
                return transicao;
            }
        }
        return null;
    }

    public static class ResultadoProcessamento {
        private  boolean aceita;
        private  String mensagem;

        public ResultadoProcessamento(boolean aceita, String mensagem) {
            this.aceita = aceita;
            this.mensagem = mensagem;
        }

        public boolean isAceita() {
            return aceita;
        }

        public String getMensagem() {
            return mensagem;
        }
    }
}
