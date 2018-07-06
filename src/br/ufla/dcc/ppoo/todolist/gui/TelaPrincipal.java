package br.ufla.dcc.ppoo.todolist.gui;

import br.ufla.dcc.ppoo.todolist.util.Configuracao;
import br.ufla.dcc.ppoo.todolist.excecoes.DeadlineInvalidoException;
import br.ufla.dcc.ppoo.todolist.excecoes.TarefaExistenteException;
import br.ufla.dcc.ppoo.todolist.excecoes.TarefaInvalidaException;
import br.ufla.dcc.ppoo.todolist.tarefa.Tarefa;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class TelaPrincipal extends JFrame implements Runnable {

    // Componentes referentes ao layout da tela
    private GridBagConstraints gbc;
    private GridBagLayout gbl;

    private FabricaAbstrataElementosGraficos fabricaElementosGraficos;

    // Rótulos
    private JLabel lbDescricao;
    private JLabel lbDeadline;
    private JLabel lbrelogio;

    // Caixas de texto
    private JTextField tfTarefa;
    private JTextField tfDeadline;

    // Componentes necessários para uso da tabela de dados
    // Para saber mais sobre como usar JTable, 
    //acesse: https://www.devmedia.com.br/jtable-utilizando-o-componente-em-interfaces-graficas-swing/28857
    private JTable tbTarefas;
    private DefaultTableModel mdDados;
    private JScrollPane painelTarefas;

    // Botões
    private JButton btSalvar;
    private JButton btCopiar;
    private JButton btRemover;
    private JButton btLimpar;
    private JPanel painelBotoes; // container para os botões da tela
    private JPanel painelLimpar; //botao limpar

    //barra de menu
    private JMenuBar jmbBarraMenu;
    private JMenu jmMenuArquivo;
    private JMenuItem jmiExportTXT;
    private JMenuItem jmiExportHTML;
    private JMenuItem jmiImportarTXT;

    //janela salvar
    JFileChooser jfcExport;
    JFileChooser jfcImport;

    //JToolBar
    public TelaPrincipal(FabricaAbstrataElementosGraficos fabricaElementosGraficos) {
        // Define o título da tela
        super("Lista de Tarefas");

        this.fabricaElementosGraficos = fabricaElementosGraficos;

        // Define que fechar a janela, a execução aplicação será encerrada
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Evita que a tela possa ser redimensionada pelo usuário
        setResizable(false);

        // Invoca o método que efetivamente constrói a tela
        construirTela();

        // inicia a thred do relogio
        iniciarRelogio();

        // Redimensiona automaticamente a tela, com base nos componentes existentes na mesma
        pack();

        // Coloca a tela para abir no centro da screen
        setLocationRelativeTo(null);

    }

    private void iniciarRelogio() {
        Thread t = new Thread(this);
        t.start();
    }

    private void construirTela() {

        setContentPane(fabricaElementosGraficos.criarPainel());

        // Instancia os objetos de layout da tela
        gbc = new GridBagConstraints();
        gbl = new GridBagLayout();

        // Configura o layout da tela
        setLayout(gbl);

        adicionarBarraMenu();

        // Instancia os objetos referentes aos componentes da tela
        lbDescricao = fabricaElementosGraficos.criarRotulo();
        lbDescricao.setText("Tarefa");
        lbDeadline = fabricaElementosGraficos.criarRotulo();
        lbDeadline.setText("Deadline");

        tfTarefa = fabricaElementosGraficos.criarCampoTexto();
        tfTarefa.setColumns(29); // 29 refere-se ao tamanho da caixa de texto 

        tfDeadline = fabricaElementosGraficos.criarCampoTexto();
        tfDeadline.setColumns(7); // 7 refere-se ao tamanho da caixa de texto

        btSalvar = fabricaElementosGraficos.criarBotao();
        btSalvar.setText("Salvar");
        btSalvar.setIcon(new ImageIcon(getClass().getResource("../../../../../../icones/save.png")));
        //btSalvar = new JButton("Salvar", new ImageIcon(getClass().getResource("../../../../../../icones/save.png")));
        // Cria uma classe interna anônima para tratar o evento de clique sobre o botão "Salvar"
        btSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                salvarTarefa();
            }
        });

        btCopiar = fabricaElementosGraficos.criarBotao();
        btCopiar.setText("Copiar");
        btCopiar.setIcon(new ImageIcon(getClass().getResource("../../../../../../icones/copy.png")));
        //btCopiar = new JButton("Copiar", new ImageIcon(getClass().getResource("../../../../../../icones/copy.png")));
        btCopiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                copiarTarefa();
            }
        });

        btRemover = fabricaElementosGraficos.criarBotao();
        btRemover.setText("Remover");
        btRemover.setIcon(new ImageIcon(getClass().getResource("../../../../../../icones/trash.png")));
        //btRemover = new JButton("Remover", new ImageIcon(getClass().getResource("../../../../../../icones/trash.png")));
        btRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                removerTarefa();
            }
        });

        btLimpar = fabricaElementosGraficos.criarBotao();
        btLimpar.setText("Limpar");
        btLimpar.setIcon(new ImageIcon(getClass().getResource("../../../../../../icones/clear.png")));
        // btLimpar = new JButton("Limpar", new ImageIcon(getClass().getResource("../../../../../../icones/clear.png")));
        btLimpar.setToolTipText("Limpar todas as tarefas");
        btLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //limpar toda a tabela
                limparTarefas();
            }
        });

        configurarBotoesEstadoInicial();

        // Instancia o painel (container) de botões e adiciona os botões a ele
        painelBotoes = fabricaElementosGraficos.criarPainel();
        painelBotoes.setLayout(new GridLayout(1, 4, 5, 5));
        //painelBotoes = new JPanel(new GridLayout(1, 4, 5, 5));
        painelBotoes.add(btSalvar);
        painelBotoes.add(btCopiar);
        painelBotoes.add(btRemover);

        painelBotoes.add(btLimpar);

        // Constrói o modelo de dados 
        // Toda tabela possui um modelo de dados, que é onde ficam as informações exibidas pela tabela
        mdDados = new DefaultTableModel();

        // Adicionando colunas ao modelo de dados. 
        mdDados.addColumn("Tarefa");
        mdDados.addColumn("Deadline");

        // Constrói a tabela, com base no modelo de dados
        tbTarefas = fabricaElementosGraficos.criarTabela();
        tbTarefas.setModel(mdDados);
        //tbTarefas = new JTable(mdDados);
        tbTarefas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                configurarBotoesEstadoSelecao();
            }
        });

        // Configura o tamanho das colunas da tabela
        tbTarefas.getColumnModel().getColumn(0).setMaxWidth(400);
        tbTarefas.getColumnModel().getColumn(1).setMaxWidth(100);

        // Uma tabela precisam estar inserida em um componente JScrollPane, para que barras de rolagem sejam adicionadas a ela
        painelTarefas = new JScrollPane(tbTarefas);
        lbrelogio = fabricaElementosGraficos.criarRotulo();

        // Adicionando os componentes recém-criados à tela
        adicionarComponente(lbDescricao, GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 0, 1, 1);
        adicionarComponente(lbDeadline, GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 1, 1, 1);
        adicionarComponente(tfTarefa, GridBagConstraints.EAST, GridBagConstraints.BOTH, 1, 0, 1, 1);
        adicionarComponente(tfDeadline, GridBagConstraints.EAST, GridBagConstraints.BOTH, 1, 1, 1, 1);
        adicionarComponente(painelBotoes, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 2, 0, 2, 1);
        adicionarComponente(painelTarefas, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 3, 0, 2, 1);
        adicionarComponente(lbrelogio, GridBagConstraints.CENTER, GridBagConstraints.NONE, 4, 0, 2, 1);

    }

    private void adicionarBarraMenu() {

        jmbBarraMenu = new JMenuBar();
        jmMenuArquivo = new JMenu("Arquivo");
        jmiExportTXT = new JMenuItem("Exportar para TXT");
        jmiExportTXT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarTXT();
            }
        });

        jmiExportHTML = new JMenuItem("Exportar para HTML");
        jmiExportHTML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarHTML();
            }
        });

        jmiImportarTXT = new JMenuItem("Importar arq TXT");
        jmiImportarTXT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importarTXT();
            }
        });

        jmMenuArquivo.add(jmiExportTXT);
        jmMenuArquivo.add(jmiExportHTML);
        jmMenuArquivo.add(jmiImportarTXT);

        jmbBarraMenu.add(jmMenuArquivo);

        setJMenuBar(jmbBarraMenu);

    }

    private void exportarTXT() {
        jfcExport = new JFileChooser("C:/Documents/");
        jfcExport.setMultiSelectionEnabled(false);
        if (jfcExport.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            escreverArquivoTXT(jfcExport.getSelectedFile());
        }

    }

    private void incluirListaTarefas(List<Tarefa> tarefas) {
        for (Tarefa t : tarefas) {
            // Adiciona a tarefa na tabela.
            String[] dados = new String[2];
            dados[0] = t.getTarefa();
            dados[1] = t.getDeadline();
            mdDados.addRow(dados);
        }
    }

    private List<Tarefa> obterListaTarefas() {
        List<Tarefa> tarefas = new ArrayList<Tarefa>();
        for (int i = 0; i < mdDados.getRowCount(); i++) {
            // Cria uma tarefa, a partir dos dados que estão na linha "i" 
            // da tabela de tarefas
            Tarefa t = new Tarefa(
                    (String) mdDados.getValueAt(i, 0),
                    (String) mdDados.getValueAt(i, 1));

            tarefas.add(t);
        }
        return tarefas;
    }

    private void escreverArquivoTXT(File arq) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(arq));
            List<Tarefa> t = obterListaTarefas();
            for (Tarefa tarefa : t) {
                bw.write(tarefa.getTarefa() + "; " + tarefa.getDeadline() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Tratar excessao");
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioex) {
                    System.out.println("Erro de escrita no arquivo");
                }
            }
        }
    }

    private void exportarHTML() {
        jfcExport = new JFileChooser("C:/Documents/");
        jfcExport.setMultiSelectionEnabled(false);
        if (jfcExport.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            escreverArquivoHTML(jfcExport.getSelectedFile());
        }
    }

    private void escreverArquivoHTML(File arq) {
        BufferedWriter bw = null;
        String tabelaHTML = null;
        try {
            bw = new BufferedWriter(new FileWriter(arq));
            String paginaHTML = "<!DOCTYPE html><html lang=\"pt-BR\"><head><title>Lista de Tarefas</title><meta "
                    + "charset=\"UTF-8\" \\><style>table {width: 600px;}table, th, td, caption {border: 1px "
                    + "solid #B6B6B6;border-collapse: collapse;font-family: Arial, Helvetica, sans-serif;}th, .posicao, "
                    + ".deadline {padding: 5px;text-align: center;}th, caption {font-weight: bold;}th {background-color:"
                    + " #FFF2CC;font-style: italic;}caption {background-color: #EFEFEF;}td {padding: 5px;text-align: "
                    + "left;background-color: #FFFFFF;}.posicao {width: 10%;}.tarefa {width: 70%;}.deadline {width: "
                    + "20%;}</style></head><body><table><caption>Lista de Tarefas</caption><tr><th class=\"posicao\""
                    + ">#</th><th class=\"tarefa\">Tarefa</th><th class=\"deadline\">Deadline</th></tr>";

            List<Tarefa> t = obterListaTarefas();
            int i = 1;
            for (Tarefa tarefa : t) {
                tabelaHTML += "<tr><td>" + i + "</td><td>" + tarefa.getTarefa() + "</td><td>" + tarefa.getDeadline() + "</td></tr>\n";
            }
            tabelaHTML += "</table></body></html>";
            bw.write(tabelaHTML);
        } catch (IOException e) {
            System.out.println("Tratar excessao");
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioex) {
                    System.out.println("Erro de escrita no arquivo");
                }
            }
        }
    }

    private void importarTXT() {
        jfcImport = new JFileChooser("C:/Documents/");
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        jfcImport.setFileFilter(filtro);

        jfcImport.setMultiSelectionEnabled(false);
        if (jfcImport.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            lerArquivo(jfcImport.getSelectedFile());
            //System.out.println(jfcImport.getTypeDescription(jfcImport.getSelectedFile()));
            //String nomeArquivo = jfcImport.getSelectedFile().getName();
            //System.out.println(nomeArquivo);
        }
    }

    private void lerArquivo(File arquivo) {
        BufferedReader br = null;
        String linha[] = null;
        try {
            br = new BufferedReader(new FileReader(arquivo));
            while (br.ready()) {
                try {
                    linha = br.readLine().split(";");
                    String tarefa = linha[0].trim();
                    String deadline = linha[1].trim();
                    validarTarefa(tarefa, deadline);

                    Tarefa t = new Tarefa(tarefa, deadline);

                    tarefaExiste(t);

                    adicionarTarefa(t);

                } catch (ArrayIndexOutOfBoundsException | TarefaExistenteException | TarefaInvalidaException
                        | DeadlineInvalidoException e) {
                    //nao faz nada
                }

            }
        } catch (IOException e) {
            System.out.println("Tratar excessao");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ioex) {
                    System.out.println("Erro de leitura no arquivo");
                }
            }

        }

    }

    private void configurarBotoesEstadoInicial() {
        btCopiar.setEnabled(false);
        btRemover.setEnabled(false);
        btSalvar.setEnabled(true);
        btLimpar.setEnabled(false);
    }

    private void configurarBotoesEstadoInsercao() {
        btCopiar.setEnabled(false);
        btRemover.setEnabled(false);
        btSalvar.setEnabled(true);
        if (mdDados.getRowCount() > 0) {
            btLimpar.setEnabled(true);
        } else {
            btLimpar.setEnabled(false);
        }
    }

    private void configurarBotoesEstadoSelecao() {
        btCopiar.setEnabled(true);
        btRemover.setEnabled(true);
        btSalvar.setEnabled(false);
    }

    private void adicionarTarefa(Tarefa t) {
        String[] dados = new String[2];

        dados[0] = t.getTarefa();
        dados[1] = t.getDeadline();
        mdDados.addRow(dados);

    }

    private void copiarTarefa() {
        Tarefa t = obterTarefaSelecionada();
        if (t != null) {
            tfTarefa.setText(t.getTarefa());
            tfDeadline.setText(t.getDeadline());

            // Desmarca a linha selecionada na tabela pelo usuário
            tbTarefas.getSelectionModel().clearSelection();

            configurarBotoesEstadoInsercao();
        }
    }

    private void removerTarefa() {
        // Captura a linha da tabela que foi selecionada pelo usuário
        int linhaSelecionada = tbTarefas.getSelectedRow();

        // Garante que a linha seleciona está dentro de um limite aceito: [0, quantidade de linhas - 1]
        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Deseja realmente remover esta tarefa?",
                "Confirmar remoção", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
            mdDados.removeRow(linhaSelecionada);

            // Desmarca a linha selecionada na tabela pelo usuário
            tbTarefas.getSelectionModel().clearSelection();

            configurarBotoesEstadoInsercao();
        }
    }

    private Tarefa obterTarefaSelecionada() {
        // Captura a linha da tabela que foi selecionada pelo usuário
        int linhaSelecionada = tbTarefas.getSelectedRow();

        // Garante que a linha seleciona está dentro de um limite aceito: [0, quantidade de linhas - 1]
        if (linhaSelecionada < 0 || linhaSelecionada >= mdDados.getRowCount()) {
            return null;
        }

        /* 
        * Obtém os dados daquela linha, a partir do modelo de dados.
        * A coluna 0 representa a descrição da tarefa e a coluna 1, seu deadline
        * Foi necessário fazer o casting explícito para a classe "String", pois
        * o método "getValueAt" retorna um objeto da classe "Object"
         */
        Tarefa t = new Tarefa((String) mdDados.getValueAt(linhaSelecionada, 0),
                (String) mdDados.getValueAt(linhaSelecionada, 1));

        return t;
    }

    private void tarefaExiste(Tarefa t) throws TarefaExistenteException {
        int nLinhas = mdDados.getRowCount();
        String descricao;
        String deadLine;
        for (int i = 0; i < nLinhas; i++) {
            descricao = (String) mdDados.getValueAt(i, 0);
            deadLine = (String) mdDados.getValueAt(i, 1);
            if ((descricao.equalsIgnoreCase(t.getTarefa())) && deadLine.equalsIgnoreCase(t.getDeadline())) {
                throw new TarefaExistenteException("Tarefa já existe!");
            }
        }
    }

    private void validarTarefa(String tarefa, String deadLine) throws TarefaInvalidaException, DeadlineInvalidoException {
        //se possui mais de 50 caracteres
        if (tarefa.length() > 50) {
            throw new TarefaInvalidaException("Limite de caracteres excedido, maximo 50 caracteres!");
        }
        if (deadLine.trim().isEmpty()) {
            throw new DeadlineInvalidoException("Deadline invalido!");
        }

    }

    private void salvarTarefa() {
        /* 
        * Verifica se a descrição ou o deadline da tarefa não foram informados pelo usuário.
        * O método getText() retorna uma string que representa o texto digitado pelo usuário na caixa de texto.
        * O método trim() remove espaços em branco do início e do fim de uma string.
        * O método isEmpty() retorna "true" caso uma string esteja vazia (sem caracteres) e "false", caso contrário.
         */
        if (!tfTarefa.getText().trim().isEmpty()) {
            try {
                // verifica deadline e tarefa
                validarTarefa(tfTarefa.getText(), tfDeadline.getText());

                Tarefa t = new Tarefa(tfTarefa.getText(), tfDeadline.getText());

                tarefaExiste(t);

                adicionarTarefa(t);

                // Envia mensagem na tela
                JOptionPane.showMessageDialog(this, "Tarefa adicionada com sucesso!",
                        "Parabéns", JOptionPane.INFORMATION_MESSAGE);

                // Limpa os campos de texto.
                tfTarefa.setText("");
                tfDeadline.setText("");

            } catch (TarefaInvalidaException | DeadlineInvalidoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao adicionar tarefa",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (TarefaExistenteException exc) {
                JOptionPane.showMessageDialog(this, exc.getMessage(),
                        "Erro ao adicionar tarefa", JOptionPane.ERROR_MESSAGE);
            } finally {
                // Configura estado dos botões
                configurarBotoesEstadoInsercao();
            }
        }
    }

    private void limparTarefas() {
        if (mdDados.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Lista de tarefas já vazia!");
        } else {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Deseja realmente remover todas as tarefa?",
                    "Confirmar remoção", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                mdDados.setRowCount(0);
                JOptionPane.showMessageDialog(this, "Sucesso!");
                configurarBotoesEstadoInsercao();
            }
        }
    }

    private void adicionarComponente(Component comp, int anchor, int fill, int linha, int coluna, int larg, int alt) {
        gbc.anchor = anchor; // posicionamento do componente na tela (esquerda, direita, centralizado, etc)
        gbc.fill = fill; // define se o tamanho do componente será expandido ou não
        gbc.gridy = linha; // linha do grid onde o componente será inserido
        gbc.gridx = coluna; // coluna do grid onde o componente será inserido
        gbc.gridwidth = larg; // quantidade de colunas do grid que o componente irá ocupar
        gbc.gridheight = alt; // quantidade de linhas do grid que o componente irá ocupar
        gbc.insets = new Insets(3, 3, 3, 3); // espaçamento (em pixels) entre os componentes da tela
        gbl.setConstraints(comp, gbc); // adiciona o componente "comp" ao layout com as restrições previamente especificadas
        add(comp); // efetivamente insere o componente na tela
    }

    @Override
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            while (true) {
                lbrelogio.setText(sdf.format(Calendar.getInstance().getTime()));
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
            System.out.println("Erro ao atualizar relógio da tela!");
        }
    }

    public static void main(String[] args) {
        try {
            // Lê arquivo de configuração
            String tema = Configuracao.obterInstancia("config.txt").obterNomeFabrica();

            // Constrói a tela com o tema escolhido
            FabricaAbstrataElementosGraficos fabrica = (FabricaAbstrataElementosGraficos) Class.forName(tema).newInstance();
            new TelaPrincipal(fabrica).setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar o tema da aplicação. Favor verificar o aquivo de configurações!",
                    "Ops...", JOptionPane.ERROR_MESSAGE);
            new TelaPrincipal(new FabricaConvElementosGraficos()).setVisible(true);
        }
    }
}
