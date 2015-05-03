package br.unisc.pdm.trabalhodispositivos.vo;

import android.text.format.Time;


/**
 * Created by Thiago Cardoso on 01/05/2015.
 * Classe intermediaria Pessoa Encontro
 */
public class PessoaEncontroVO {

    private int id_pessoa_encontro;
    private Time hora_chegada;
    private Time hora_saida;

    public int getId_pessoa_encontro() {
        return id_pessoa_encontro;
    }

    public void setId_pessoa_encontro(int id_pessoa_encontro) {
        this.id_pessoa_encontro = id_pessoa_encontro;
    }

    public Time getHora_chegada() {
        return hora_chegada;
    }

    public void setHora_chegada(Time hora_chegada) {
        this.hora_chegada = hora_chegada;
    }

    public Time getHora_saida() {
        return hora_saida;
    }

    public void setHora_saida(Time hora_saida) {
        this.hora_saida = hora_saida;
    }
}
