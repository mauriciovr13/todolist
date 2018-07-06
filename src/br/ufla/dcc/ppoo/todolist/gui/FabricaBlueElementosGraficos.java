/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufla.dcc.ppoo.todolist.gui;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Maurício Vieira
 */
public class FabricaBlueElementosGraficos extends FabricaAbstrataElementosGraficos {

    @Override
    public JPanel criarPainel() {
        JPanel painel = new JPanel();
        painel.setBackground(Color.BLUE);
        painel.setForeground(Color.red);
        return painel;
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
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);     
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
    public JTable criarTabela() {
        JTable tabela = new JTable();
        tabela.setBackground(Color.WHITE);
        tabela.setGridColor(Color.BLUE);
        tabela.setForeground(Color.BLACK);
        return tabela;
    }

    @Override
    public JLabel criarRotulo() {
        JLabel lb = new JLabel();
        lb.setBackground(Color.WHITE);
        lb.setForeground(Color.WHITE);
        return lb;
    }
    
    

}
