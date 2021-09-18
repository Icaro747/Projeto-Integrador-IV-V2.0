package br.com.process.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * <b>Gerenciador de conexão</b> com banco de dados.
 * @author Icaro
 */
public class Conexao {
    
    private static String STATUS = "Não conectado";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private final static String SERVER = "localhost";
    private final static String DATABASE = "NewManSQL";
    private final static String PORTA = "3306";
    private final static String LOGIN = "root";
    private final static String SENHA = "";
    private static String URL = "";
    
    private static Connection CONEXAO;
    
    /**
     * método que abre a conexão com banco de dados
     * @return retorna a conexão
     */
    public static Connection abrirConexao(){
 
        URL = "jdbc:mysql://" + SERVER + ":" + PORTA + "/" + DATABASE + "?useTimezone=true&serverTimezone=UTC&useSSL=false";
        
        if(CONEXAO==null){    
            try{
                
                Class.forName(DRIVER);
                CONEXAO = DriverManager.getConnection(URL, LOGIN, SENHA);

                if (CONEXAO != null) {
                    STATUS = "Conexão realizada com sucesso!";
                }else{
                    STATUS = "Não foi possivel realizar a conexão";
                }

            }catch (ClassNotFoundException e) {  //Driver não encontrado

                throw new IllegalArgumentException("O driver expecificado nao foi encontrado.");

            }catch (SQLException e) {  //Erro ao estabelecer a conexão (Ex: login ou senha errados)

                //Outra falha de conexão
                throw new IllegalArgumentException("Erro ao estabelecer a conexão com o banco de dados");
            }
        }else{
            try {
                //Se a conexão estiver fechada, reabro a conexão
                if(CONEXAO.isClosed())
                    CONEXAO = DriverManager.getConnection(URL, LOGIN, SENHA);
            } catch (SQLException ex) {
                throw new IllegalArgumentException("Falha ao fechar a conexão.");
            }
        }
        return CONEXAO;
    }
    
    /**
     * método que mostra os status da conexão
     * @return retorna o status da conexão
     */
    public static String getStatusConexao() {
        return STATUS;
    }
    
    /**
     * método que fechar conexão com banco de dados
     * @return <b>true</b> se conexão foi fechada com sucesso <b>false</b> se não for.
     * @throws SQLException 
     */
    public static boolean fecharConexao() throws SQLException {
 
        boolean retorno;
        
        try{
            
            if(CONEXAO!=null){
                if(!CONEXAO.isClosed())
                    CONEXAO.close();
            }
            
            STATUS = "Não conectado";
            retorno = true;
            
        }catch (SQLException e) {
            retorno = false;
        }
        return retorno;
    }
}