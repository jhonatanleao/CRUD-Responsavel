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
            stmt = con.prepareStatement("INSERT INTO responsavel (nomeResponsavel,dtNascimento) VALUES(?,?)");
            stmt.setString(1, responsavel.getNomeResponsavel());
            stmt.setString(2, responsavel.getDtNascimento());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
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

    public void update(Responsavel responsavel){
        Connection con = BankConnection.getConnection();
        PreparedStatement stmt = null;

        try{
            stmt = con.prepareStatement("UPDATE responsavel SET nomeResponsavel = ? ,dtNascimento = ? WHERE idResponsavel = ?");
            stmt.setString(1, responsavel.getNomeResponsavel());
            stmt.setString(2, responsavel.getDtNascimento());
            stmt.setInt(3, responsavel.getIdResponsavel());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
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
            stmt = con.prepareStatement("DELETE FROM responsavel WHERE id = ?");
            stmt.setInt(1, responsavel.getIdResponsavel());
            stmt.executeUpdate();

            //Lembrar de excluir a pessoa Fisica ou Juridica dependendo do ID que foi chamado aqui.

            JOptionPane.showMessageDialog(null, "Deletado com sucesso!");
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Deletado ao excluir: " + ex);
        } finally {
            BankConnection.closeConnection(con, stmt);
        }
    }
}
