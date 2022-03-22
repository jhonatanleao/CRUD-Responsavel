package Model.dao;

import Model.bean.PessoaJuridica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JOptionPane;

import Connection.BankConnection;

public class PessoaJuridicaDAO {
    public void create(PessoaJuridica pessoaJuridica){
        Connection con = BankConnection.getConnection();
        PreparedStatement stmt = null;

        try{
            stmt = con.prepareStatement("INSERT INTO pessoaJuridica (idResponsavel, cnpj) VALUES(?,?)");
            stmt.setInt(1, pessoaJuridica.getIdResponsavel());
            stmt.setString(2, pessoaJuridica.getCnpj());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao criar: " + ex);
        } finally {
            BankConnection.closeConnection(con, stmt);
        }
    }

    public List<PessoaJuridica> read() {
        Connection con = BankConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<PessoaJuridica> listPessoasJuridica = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM pessoaJuridica");
            rs = stmt.executeQuery();

            while(rs.next()){
                PessoaJuridica pessoaJuridica = new PessoaJuridica();

                pessoaJuridica.setIdResponsavel(rs.getInt("idResponsavel"));
                pessoaJuridica.setCnpj(rs.getString("cnpj"));   
                listPessoasJuridica.add(pessoaJuridica);          
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na leitura no banco: " + ex);
        } finally {
            BankConnection.closeConnection(con, stmt, rs);
        }
        return listPessoasJuridica;
    }
    
    public List<PessoaJuridica> readForDesc(String desc) {
        Connection con = BankConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<PessoaJuridica> listPessoasJuridica = new ArrayList<>();

        try {
            stmt = con.prepareStatement("select pessoaJuridica.idResponsavel AS id, responsavel.nomeResponsavel AS nome, "
                    + "responsavel.dtNascimento AS data, pessoaJuridica.cnpj AS cnpj from pessoaJuridica "
                    + "INNER JOIN responsavel ON pessoaJuridica.idResponsavel= responsavel.idResponsavel  "
                    + "where dtNascimento LIKE ? OR "
                    + "nomeResponsavel LIKE ? OR "
                    + "cnpj LIKE ? OR "
                    + "pessoaJuridica.idResponsavel LIKE ?");
            stmt.setString(1, "%"+desc+"%");
            stmt.setString(2, "%"+desc+"%");
            stmt.setString(3, "%"+desc+"%");
            stmt.setString(4, "%"+desc+"%");
            rs = stmt.executeQuery();

            while(rs.next()){
                PessoaJuridica pessoaJuridica = new PessoaJuridica();
                pessoaJuridica.setIdResponsavel(rs.getInt("id"));
                pessoaJuridica.setCnpj(rs.getString("cnpj"));   
                listPessoasJuridica.add(pessoaJuridica);          
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na leitura no banco: " + ex);
        } finally {
            BankConnection.closeConnection(con, stmt, rs);
        }
        return listPessoasJuridica;
    }

    public void update(PessoaJuridica pessoaJuridica){
        Connection con = BankConnection.getConnection();
        PreparedStatement stmt = null;

        try{
            stmt = con.prepareStatement("UPDATE pessoaJuridica SET cnpj = ? WHERE idResponsavel = ?");
            stmt.setString(1, pessoaJuridica.getCnpj());
            stmt.setInt(2, pessoaJuridica.getIdResponsavel());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
        } finally {
            BankConnection.closeConnection(con, stmt);
        }
    }

    public void delete(PessoaJuridica pessoaJuridica){
        Connection con = BankConnection.getConnection();
        PreparedStatement stmt = null;

        try{
            stmt = con.prepareStatement("DELETE FROM pessoaJuridica WHERE idResponsavel = ?");
            stmt.setInt(1, pessoaJuridica.getIdResponsavel());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Deletado com sucesso!");
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Deletado ao excluir: " + ex);
        } finally {
            BankConnection.closeConnection(con, stmt);
        }
    }
    
    public int maxId(){
        Connection con = BankConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int id = -1;
        try{
            stmt = con.prepareStatement("SELECT MAX(idResponsavel) id from pessoaJuridica");
            rs = stmt.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao consultar: " + ex);
        } finally {
            BankConnection.closeConnection(con, stmt, rs);
        }
        return id;
    }
}