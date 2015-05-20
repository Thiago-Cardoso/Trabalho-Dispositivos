package br.unisc.pdm.trabalhodispositivos.vo;

import android.text.format.Time;

import java.text.DateFormat;

/**
 * Created by Thiago Cardoso on 01/05/2015.
 * Classe Encontro
 */
public class EncontroVO {

    public static final String STORE_MODE = "DB";  //opcoes: DB ou WEB

       private int id_encontro;
       private int id;
       private String data;
       private String hora;
       private String descricao;
       private int qtd;



    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
