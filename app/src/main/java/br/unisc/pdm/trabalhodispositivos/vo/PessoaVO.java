package br.unisc.pdm.trabalhodispositivos.vo;

/**
 * Created by Thiago Cardoso on 01/05/2015.
 * Classe Participante
 */
public class PessoaVO {
    public static final String STORE_MODE = "DB";  //opcoes: DB ou WEB

    private int id_pessoa;
    private String nome;
    private String idade;
    private String matricula;
    private String email;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(int id_pessoa) {
        this.id_pessoa = id_pessoa;
    }

    @Override
    public String toString(){
        return id_pessoa + " | " + nome + " - " + email;
    }
}
