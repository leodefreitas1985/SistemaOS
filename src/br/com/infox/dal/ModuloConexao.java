package br.com.infox.dal;

import java.sql.*;

public class ModuloConexao {
    //Método responsável por estabelecer a conexão com o banco

    public static Connection conector() {
        java.sql.Connection conexao = null;
        //Linha abaixo "chama" o driver de mysql
        String driver = "com.mysql.jdbc.Driver";
        // Armazendando informações ref. ao banco
        String url = "jdbc:mysql://localhost:3306/SistemaOS";
        String user = "root";
        String password = "";
        //Estabelecendo a conexão com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url,user,password);
            return conexao;
        } catch (Exception e) {
            //a linha abaixo serve de apoio para esclarecer o erro
           // System.out.println(e);
            return null;
//teste
        }
    }
}
