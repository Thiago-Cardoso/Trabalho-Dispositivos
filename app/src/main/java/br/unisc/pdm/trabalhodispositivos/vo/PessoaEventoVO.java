package br.unisc.pdm.trabalhodispositivos.vo;

/**
 * Created by Diego on 17/05/2015.
 */
public class PessoaEventoVO {
    private int fk_evento_id;
    private int fk_pessoa_id;

    public int getFk_evento_id() {
        return fk_evento_id;
    }

    public int getFk_pessoa_id() {
        return fk_pessoa_id;
    }

    public void setFk_pessoa_id(int fk_pessoa_id) {
        this.fk_pessoa_id = fk_pessoa_id;
    }

    public void setFk_evento_id(int fk_evento_id) {
        this.fk_evento_id = fk_evento_id;
    }
}
