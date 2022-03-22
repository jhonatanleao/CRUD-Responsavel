package Model.dao;

import Model.bean.Responsavel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JOptionPane;

import Connection.BankConnection;

public class ResponsavelDAO {
    
    public void create(Responsavel responsavel){
        Connection con = BankConnection.getConnection();
        PreparedStatement stmt = null;

        try{
            stmt = con.prepareStatement("INSERT INTO responsavel (idResponsavel, nomeResponsavel,dtNascimento) VALUES(?,?,?)");
            stmt.setInt(1, responsavel.getIdResponsavel());
            stmt.setString(2, responsavel.getNomeResponsavel());
            stmt.setString(3, responsavel.getDtNascimento());
            stmt.executeUpdate();

        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao criar: " + ex);
        } finally {
            BankConnection.closeConnection(con, stmt);
        }
    }

    public List<Responsavel> read() {
        Connection con = BankConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Responsavel> listResponsavel = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM responsavel");
            rs = stmt.executeQuery();

            while(rs.next()){
                Responsavel responsavel = new Responsavel();

                responsavel.setIdResponsavel(rs.getInt("idResponsavel"));
                responsavel.setNomeResponsavel(rs.getString("nomeResponsavel"));
                responsavel.setDtNascimento(rs.getString("dtNascimento"));
                listResponsavel.add(responsavel);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na leitura no banco: " + ex);
        } finally {
            BankConnection.closeConnection(con, stmt, rs);
        }
        return listResponsavel;
    }
    
    public List<Responsavel> readForDesc(String desc) {
        Connection con = BankConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Responsavel> listResponsavel = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM responsavel WHERE nomeResponsavel LIKE ? OR dtNascimento LIKE ?");
            stmt.setString(1, "%"+desc+"%");
            stmt.setString(2, "%"+desc+"%");
            rs = stmt.executeQuery();

            while(rs.next()){
                Responsavel responsavel = new Responsavel();

                responsavel.setIdResponsavel(rs.getInt("idResponsavel"));
                responsavel.setNomeResponsavel(rs.getString("nomeResponsavel"));
                responsavel.setDtNascimento(rs.getString("dtNascimento"));
                listResponsavel.add(responsavel);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na leitura no banco: " + ex);
        } finally {
            BankConnection.closeConnection(con, stmt, rs);
        }
        return listResponsavel;
    }
    
    public List<Responsavel> readForDescJuridica(String desc) {
        Connection con = BankConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Responsavel> listResponsavel = new ArrayList<>();

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
                Responsavel responsavel = new Responsavel();

                responsavel.setIdResponsavel(rs.getInt("id"));
                responsavel.setNomeResponsavel(rs.getString("nome"));
                responsavel.setDtNascimento(rs.getString("data"));
                listResponsavel.add(responsavel);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na leitura no banco: " + ex);
        } finally {
            BankConnection.closeConnection(con, stmt, rs);
        }
        return listResponsavel;
    }
    
    public List<Responsavel> readForDescFisica(String desc) {
        Connection con = BankConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Responsavel> listResponsavel = new ArrayList<>();

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
                Responsavel responsavel = new Responsavel();

                responsavel.setIdResponsavel(rs.getInt("id"));
                responsavel.setNomeResponsavel(rs.getString("nome"));
                responsavel.setDtNascimento(rs.getString("data"));
                listResponsavel.add(responsavel);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na leitura no banco: " + ex);
        } finally {
            BankConnection.closeConnection(con, stmt, rs);
        }
        return listResponsavel;
    }

    public void update(Responsavel responsavel){
        Connection con = BankConnection.getConnection();
        PreparedStatement stmt = null;

        try{
            stmt = con.prepareStatement("UPDATE responsavel SET nomeResponsavel = ? ,dtNascimento = ? WHERE idResponsavel = ?");
            stmt.setString(1, responsavel.getNomeResponsavel());
            stmt.setString(2, responsavel.getDtNascimento());
            stmt.setInt(3, responsavel.getIdResponsavel());

            stmt.executeUpdate();

        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
        } finally {
            BankConnection.closeConnection(con, stmt);
        }
    }

    public void delete(Responsavel responsavel){
        Connection con = BankConnection.getConnection();
        PreparedStatement stmt = null;

        try{
            stmt = con.prepareStatement("DELETE FROM responsavel WHERE idResponsavel = ?");
            stmt.setInt(1, responsavel.getIdResponsavel());
            stmt.executeUpdate();

        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex);
        } finally {
            BankConnection.closeConnection(con, stmt);
        }
    }
}