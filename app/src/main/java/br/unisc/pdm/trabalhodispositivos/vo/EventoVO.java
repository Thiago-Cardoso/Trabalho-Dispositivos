package br.unisc.pdm.trabalhodispositivos.vo;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Thiago Cardoso on 01/05/2015.
 * Classe Evento
 */
public class EventoVO {
    public static final String STORE_MODE = "DB";  //opcoes: DB ou WEB

    private int id_evento;
    private String nome;
    private String data_inicio;
    private String data_fim;


    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
        Variaveis.evento_fk = id_evento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(String data_inicio) {
        this.data_inicio = data_inicio;
    }

    public String getData_fim() {
        return data_fim;
    }

    public void setData_fim(String data_fim) {
        this.data_fim = data_fim;
    }

    @Override
    public String toString(){
        return id_evento + " | " + nome;
    }
}