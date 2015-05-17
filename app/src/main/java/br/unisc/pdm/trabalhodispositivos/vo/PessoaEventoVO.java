package br.unisc.pdm.trabalhodispositivos.vo;

/**
 * Created by Diego on 17/05/2015.
 */
public class PessoaEventoVO {
    private String fk_evento_id;
    private String fk_pessoa_id;

    public String getFk_evento_id() {
        return fk_evento_id;
    }

    public String getFk_pessoa_id() {
        return fk_pessoa_id;
    }

    public void setFk_pessoa_id(String fk_pessoa_id) {
        this.fk_pessoa_id = fk_pessoa_id;
    }

    public void setFk_evento_id(String fk_evento_id) {
        this.fk_evento_id = fk_evento_id;
    }
}
