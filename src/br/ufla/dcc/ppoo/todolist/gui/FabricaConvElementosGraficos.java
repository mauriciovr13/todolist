package br.ufla.dcc.ppoo.todolist.gui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FabricaConvElementosGraficos extends FabricaAbstrataElementosGraficos {

    @Override
    public JLabel criarRotulo() {
        return new JLabel();
    }

    @Override
    public JTextArea criarAreaTexto() {
        return new JTextArea();
    }

    @Override
    public JTextField criarCampoTexto() {
        return new JTextField();
    }

    @Override
    public JButton criarBotao() {
        return new JButton();
    }

    @Override
    public JCheckBox criarCaixaMarcacao() {
        return new JCheckBox();
    }

    @Override
    public JPanel criarPainel() {
        return new JPanel();
    }

    @Override
    public JTable criarTabela() {
        return new JTable();
    }
    
}
