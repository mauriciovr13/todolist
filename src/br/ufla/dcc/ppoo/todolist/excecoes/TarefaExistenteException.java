/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufla.dcc.ppoo.todolist.excecoes;

/**
 *
 * @author Maurício Vieira
 */
public class TarefaExistenteException extends Exception {

    public TarefaExistenteException(String message) {
        super(message);
    }
    
}
