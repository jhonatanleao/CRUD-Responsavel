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
            stmt = con.prepareStatement("INSERT INTO pessoaFisica (idResponsavel, naturalidade, cpf) VALUES(?,?,?)");
            stmt.setInt(1, pessoaFisica.getIdResponsavel());
            stmt.setString(2, pessoaFisica.getNaturalidade());
            stmt.setString(3, pessoaFisica.getCpf());

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
    
    public List<PessoaFisica> readForDesc(String desc) {
        Connection con = BankConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<PessoaFisica> listPessoaFisica = new ArrayList<>();

        try {
            stmt = con.prepareStatement("select pessoaFisica.idResponsavel AS id, responsavel.nomeResponsavel AS nome, "
                    + "responsavel.dtNascimento AS data, pessoaFisica.cpf AS cpf, pessoaFisica.naturalidade AS natu from pessoaFisica "
                    + "INNER JOIN responsavel ON pessoaFisica.idResponsavel = responsavel.idResponsavel "
                    + "where dtNascimento LIKE ? OR "
                    + "nomeResponsavel LIKE ? OR "
                    + "cpf LIKE ? OR "
                    + "naturalidade LIKE ? OR "
                    + "pessoaFisica.idResponsavel LIKE ?");
            stmt.setString(1, "%"+desc+"%");
            stmt.setString(2, "%"+desc+"%");
            stmt.setString(3, "%"+desc+"%");
            stmt.setString(4, "%"+desc+"%");
            stmt.setString(5, "%"+desc+"%");
            rs = stmt.executeQuery();

            while(rs.next()){
                PessoaFisica pessoaFisica = new PessoaFisica();
                pessoaFisica.setIdResponsavel(rs.getInt("id"));
                pessoaFisica.setNaturalidade(rs.getString("natu"));
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
            stmt = con.prepareStatement("DELETE FROM pessoaFisica WHERE idResponsavel = ?");
            stmt.setInt(1, pessoaFisica.getIdResponsavel());
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
            stmt = con.prepareStatement("SELECT MAX(idResponsavel) id from pessoaFisica");
            rs = stmt.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
            }
            System.out.println(id);
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao consultar: " + ex);
        } finally {
            BankConnection.closeConnection(con, stmt, rs);
        }
        return id;
    }
}