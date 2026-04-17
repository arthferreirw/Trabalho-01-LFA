public class Transicao {
    private String origem;
    private char letra;
    private String destino;

    public Transicao() {}

    public Transicao(String origem, char letra, String destino) {
        this.origem = origem;
        this.letra = letra;
        this.destino = destino;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public char getLetra() {
        return letra;
    }

    public void setLetra(char letra) {
        this.letra = letra;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}

