package Model.dao;

import Model.bean.PessoaFisica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JOptionPane;

import Connection.BankConnection;

public class PessoaFisicaDAO {
    public void create(PessoaFisica pessoaFisica){
        Connection con = BankConnection.getConnection();
        PreparedStatement stmt = null;

        try{
            stmt = con.prepareStatement("INSERT INTO pessoaJuridica (naturalidade, cpf) VALUES(?,?)");
            stmt.setString(1, pessoaFisica.getNaturalidade());
            stmt.setString(2, pessoaFisica.getCpf());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao criar: " + ex);
        } finally {
            BankConnection.closeConnection(con, stmt);
        }
    }

    public List<PessoaFisica> read() {
        Connection con = BankConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<PessoaFisica> listPessoaFisica = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM pessoaFisica");
            rs = stmt.executeQuery();

            while(rs.next()){
                PessoaFisica pessoaFisica = new PessoaFisica();

                pessoaFisica.setIdResponsavel(rs.getInt("idResponsavel"));
                pessoaFisica.setNaturalidade(rs.getString("naturalidade"));
                pessoaFisica.setCpf(rs.getString("cpf"));  
                listPessoaFisica.add(pessoaFisica);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na leitura no banco: " + ex);
        } finally {
            BankConnection.closeConnection(con, stmt, rs);
        }
        return listPessoaFisica;
    }

    public void update(PessoaFisica pessoaFisica){
        Connection con = BankConnection.getConnection();
        PreparedStatement stmt = null;

        try{
            stmt = con.prepareStatement("UPDATE pessoaFisica SET naturalidade = ?, cpf = ? WHERE idResponsavel = ?");
            stmt.setString(1, pessoaFisica.getNaturalidade());
            stmt.setString(2, pessoaFisica.getCpf());
            stmt.setInt(3, pessoaFisica.getIdResponsavel());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
        } finally {
            BankConnection.closeConnection(con, stmt);
        }
    }

    public void delete(PessoaFisica pessoaFisica){
        Connection con = BankConnection.getConnection();
        PreparedStatement stmt = null;

        try{
            stmt = con.prepareStatement("DELETE FROM pessoaFisica WHERE id = ?");
            stmt.setInt(1, pessoaFisica.getIdResponsavel());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Deletado com sucesso!");
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Deletado ao excluir: " + ex);
        } finally {
            BankConnection.closeConnection(con, stmt);
        }
    } 
}
