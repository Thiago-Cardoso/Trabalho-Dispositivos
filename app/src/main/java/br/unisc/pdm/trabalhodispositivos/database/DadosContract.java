package br.unisc.pdm.trabalhodispositivos.database;

import android.provider.BaseColumns;

/**
 * Created by Thiago Cardoso on 01/05/2015.
 */
public final class DadosContract {

    //Criando um construtor vazio para garantir que ninguém vai instanciar a classe
    public DadosContract(){}

    /**
     * Tabela Pessoa
     */
    public static abstract class Pessoa implements BaseColumns {
        public final static String TABLE_NAME = "PESSOA";
        public final static String NOME = "nome";
        public final static String IDADE = "idade";
        public final static String MATRICULA = "matricula";
        public final static String EMAIL = "email";
        public final static String BLOB = "blob";
        public final static String EVENTO_ID = "evento_id";
    }


    /**
     * Tabela PessoaEvento
     */
    public static abstract class PessoaEvento implements BaseColumns {
        public final static String TABLE_NAME = "PESSOAEVENTO";
        public final static String FK_EVENTO_ID = "evento_id";
        public final static String FK_PESSOA_ID = "pessoa_id";
    }

    /**
     * Tabela encontro
     */
    public static abstract class Encontro implements BaseColumns {
        public final static String TABLE_NAME = "ENCONTRO";
        public final static String DATA = "data";
        public final static String HORA = "hora";
        public final static String DESCRICAO = "descricao";
        public final static String EVENTO_ID = "evento_id";
    }

    /**
     * Tabela evento
     */
    public static abstract class Evento implements BaseColumns {
        public final static String TABLE_NAME = "EVENTO";
        public final static String NOME = "nome";
        public final static String DATA_INICIO = "data_inicio";
        public final static String DATA_FIM = "data_fim";
    }

    /**
     * Tabela intermediaria PessoaEncontro
     */
    public static abstract class PessoaEncontro implements BaseColumns {
        public final static String TABLE_NAME = "PESSOAENCONTRO";
        public final static String HORA_CHEGADA = "hora_chegada";
        public final static String HORA_SAIDA = "hora_saida";
        public final static String EVENTO_ID = "evento_id";
    }


}
