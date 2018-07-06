package br.ufla.dcc.ppoo.todolist.gui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public abstract class FabricaAbstrataElementosGraficos {

    public abstract JPanel criarPainel();

    public abstract JLabel criarRotulo();

    public abstract JTextArea criarAreaTexto();

    public abstract JTextField criarCampoTexto();

    public abstract JButton criarBotao();

    public abstract JCheckBox criarCaixaMarcacao();
    
    public abstract JTable criarTabela();
}
