# Simulador de Autômatos (AFD)

Projeto Java para ler autômatos por arquivo CSV, processar sentenças no alfabeto `{a,b}` e informar `ACEITA` ou `REJEITA` com mensagem explicativa.

## Novidades e Funcionalidades

- **Processamento interativo e em lote**: Teste sentenças digitadas ou várias sentenças de um arquivo texto.
- **Mensagens explicativas detalhadas**: O sistema informa o motivo da aceitação ou rejeição de cada sentença.
- **Exportação de autômatos**: Exporte qualquer autômato carregado para um novo arquivo CSV.
- **Validação de determinismo**: O sistema alerta se houver transições duplicadas para o mesmo par (estado, símbolo).
- **Tratamento robusto de arquivos**: Mensagens claras para erros de leitura/escrita de arquivos.

## Estrutura CSV

Cada arquivo deve seguir este formato:

```csv
inicial;0
finais;3
0;a;1
0;b;2
1;a;3
1;b;2
```

- Linha `inicial;...`: estado inicial.
- Linha `finais;...`: um ou mais estados finais.
- Demais linhas: `origem;letra;destino`.

## Arquivos incluídos

- `automato1.csv`: `w` possui `aa` ou `bb` como subpalavra.
- `automato2.csv`: entre dois `a`'s, há quantidade par de `b`'s.
- `automato3.csv`: `w` possui `aa` ou `aba` como subpalavra.
- `automato4.csv`: entre dois `b`'s, há quantidade ímpar de `a`'s.

## Executar

Compile e execute na raiz do projeto:

```bash
javac src/*.java
java -cp src Main
```

### Menu interativo

- `1..4`: seleciona o autômato e testa uma sentença digitada.
- `8`: testa várias sentenças de um arquivo texto (um por linha).
- `9`: exporta o autômato selecionado para `automatoX_exportado.csv`.
- `0`: encerra.

### Testar sentenças em lote (arquivo de palavras)

Crie um arquivo texto (ex: `palavras_teste.txt`) com uma sentença por linha. Use a opção `8` no menu, informe o número do autômato e o nome do arquivo. O sistema mostrará o resultado de cada sentença, linha a linha, com mensagem explicativa.

## Modo automático (arquivo `testes.csv`)

Para rodar uma bateria de testes para os 4 autômatos:

```bash
java -cp src Main --auto
```

Esse modo imprime `esperado`, `obtido`, `OK/FALHOU` e a mensagem explicativa de cada processamento.

Formato de `testes.csv`:

```csv
automato;sentenca;esperado;descricao
1;aa;ACEITA;contem aa
1;ab;REJEITA;nao contem aa nem bb
2;<vazia>;ACEITA;cadeia vazia
```

- `automato`: número de `1` a `4`.
- `sentenca`: use `<vazia>` para cadeia vazia.
- `esperado`: `ACEITA`/`REJEITA` (também aceita `true/false` e `1/0`).
- `descricao`: opcional.

## Observações

- O sistema aceita apenas sentenças com símbolos `a` e `b`.
- Mensagens de erro detalham problemas como símbolos inválidos, estados inexistentes ou arquivos não encontrados.
- Ao exportar, o arquivo gerado segue o mesmo padrão CSV de entrada.
