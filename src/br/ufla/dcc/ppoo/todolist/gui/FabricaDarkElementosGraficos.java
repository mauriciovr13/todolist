package br.ufla.dcc.ppoo.todolist.gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FabricaDarkElementosGraficos extends FabricaAbstrataElementosGraficos {

    @Override
    public JLabel criarRotulo() {
        JLabel lb = new JLabel();
        lb.setBackground(Color.BLACK);
        //lb.setForeground(Color.BLACK);
        return lb;
    }

    @Override
    public JTextArea criarAreaTexto() {
        JTextArea ta = new JTextArea();
        ta.setBackground(Color.WHITE);
        ta.setForeground(Color.BLACK);
        return ta;
    }

    @Override
    public JTextField criarCampoTexto() {
        JTextField tf = new JTextField();
        tf.setBackground(Color.WHITE);
        tf.setForeground(Color.BLACK);
        return tf;
    }

    @Override
    public JButton criarBotao() {
        JButton btn = new JButton();
        btn.setBackground(Color.GRAY);
        btn.setForeground(Color.WHITE);     
        btn.setFocusPainted(false);
        return btn;
    }

    @Override
    public JCheckBox criarCaixaMarcacao() {
        JCheckBox chk = new JCheckBox();
        chk.setBackground(Color.BLACK);
        chk.setForeground(Color.WHITE);           
        return chk;
    }

    @Override
    public JPanel criarPainel() {
        JPanel painel = new JPanel();
        painel.setBackground(Color.LIGHT_GRAY);
        return painel;
    }

    @Override
    public JTable criarTabela() {
        JTable tabela = new JTable();
        tabela.setBackground(Color.LIGHT_GRAY);
        tabela.setGridColor(Color.BLACK);
        tabela.setForeground(Color.WHITE);
        return tabela;
    }
    
    

}
