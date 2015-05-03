package br.unisc.pdm.trabalhodispositivos.vo;

import android.text.format.Time;

import java.text.DateFormat;

/**
 * Created by Thiago Cardoso on 01/05/2015.
 * Classe Encontro
 */
public class EncontroVO {

       private int id_encontro;
       private DateFormat data;
       private Time hora;
       private String descricao;
       private int qtd;

    public DateFormat getData() {
        return data;
    }

    public void setData(DateFormat data) {
        this.data = data;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId_encontro() {
        return id_encontro;
    }

    public void setId_encontro(int id_encontro) {
        this.id_encontro = id_encontro;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }
}
