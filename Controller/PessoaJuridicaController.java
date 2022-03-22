/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.bean.PessoaJuridica;
import Model.bean.Responsavel;
import Model.dao.PessoaJuridicaDAO;
import Model.dao.ResponsavelDAO;
import View.TelaPessoaJuridica;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class PessoaJuridicaController {
    private TelaPessoaJuridica view;
    
    
    public PessoaJuridicaController(){
        view = new TelaPessoaJuridica();
        readJTabale();
        
        this.view.getBntCadastrar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                cadastrar();
            }
        });
        
        this.view.getBntAtualizar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(view.getTblPessoaJuridica().getSelectedRow() != -1){
                    atualizar();
                }else{
                    JOptionPane.showMessageDialog(null, "Selecione alguma linha");
                } 
            }
        });

        this.view.getBntExcluir().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(view.getTblPessoaJuridica().getSelectedRow() != -1){
                    excluir();
                }else{
                    JOptionPane.showMessageDialog(null, "Selecione alguma linha");
                }                        
            }
        });        
        
        this.view.getBntBuscar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                readJTabaleForDesc(view.getTxtBusca().getText().toString());
                limpaLabel();
            }         
        });
        
        this.view.getTblPessoaJuridica().addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(view.getTblPessoaJuridica().getSelectedRow() != -1){
                    preencheMenu();
                }else{
                    JOptionPane.showMessageDialog(null, "Selecione alguma linha");
                }                
            }        
        });
        
        this.view.getBntAltPessoa().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                alteraTela();
            }         
        });
        
        view.setVisible(true);
    }
    
    public void cadastrar(){
        int id; //testar a inserção quando o banco tiver limpo
        PessoaJuridica pf = new PessoaJuridica();
        PessoaJuridicaDAO dao = new PessoaJuridicaDAO();
        Responsavel resp = new Responsavel();
        ResponsavelDAO respD = new ResponsavelDAO();

        id = dao.maxId();
        if(id == 0){
            insereBanco(id, pf, dao, resp, respD);
        }else if(id == 30){
            JOptionPane.showMessageDialog(null, "Limite de Pessoas Fisícas foi atingido, "
                    + "entre em contato com o suporte tecnico para resolver esse problema");
        }else{
            id++;
            insereBanco(id, pf, dao, resp, respD);            
        }
        limpaLabel();
        readJTabale();           
    }
    
    public void atualizar(){
        PessoaJuridica pf = new PessoaJuridica();
        PessoaJuridicaDAO dao = new PessoaJuridicaDAO();
        Responsavel resp = new Responsavel();
        ResponsavelDAO respD = new ResponsavelDAO();
        int id;

        id = (int)view.getTblPessoaJuridica().getValueAt(view.getTblPessoaJuridica().getSelectedRow(), 0);

        resp.setNomeResponsavel(view.getTxtNome().getText());
        resp.setDtNascimento(view.getTxtData().getText()); 
        resp.setIdResponsavel(id);
        respD.update(resp);

        pf.setIdResponsavel(id); 
        pf.setCnpj(view.getTxtCnpj().getText());
        dao.update(pf);

        limpaLabel();
        readJTabale();           
    }
    
    public void excluir(){
        PessoaJuridica pf = new PessoaJuridica();
        PessoaJuridicaDAO dao = new PessoaJuridicaDAO();
        Responsavel resp = new Responsavel();
        ResponsavelDAO respD = new ResponsavelDAO();
        int id = (int)view.getTblPessoaJuridica().getValueAt(view.getTblPessoaJuridica().getSelectedRow(), 0);

        resp.setIdResponsavel(id);
        pf.setIdResponsavel(id);

        dao.delete(pf);
        respD.delete(resp);

        limpaLabel();
        readJTabale(); 
    }
    
    public void preencheMenu(){
        view.getTxtNome().setText(view.getTblPessoaJuridica().getValueAt(view.getTblPessoaJuridica().getSelectedRow(), 1).toString());
        view.getTxtData().setText(view.getTblPessoaJuridica().getValueAt(view.getTblPessoaJuridica().getSelectedRow(), 2).toString());
        view.getTxtCnpj().setText(view.getTblPessoaJuridica().getValueAt(view.getTblPessoaJuridica().getSelectedRow(), 3).toString());
    }
    
    public void alteraTela(){
        view.dispose();
        PessoaFisicaController inicio = new PessoaFisicaController();        
    }
    
    public void limpaLabel(){
        view.getTxtNome().setText("");
        view.getTxtData().setText("");
        view.getTxtCnpj().setText("");
        view.getTxtBusca().setText("");
    }
    
    public void readJTabale(){
        JTable tblPessoaJuridica = view.getTblPessoaJuridica();
        DefaultTableModel model = (DefaultTableModel) tblPessoaJuridica.getModel();
        model.setNumRows(0);
        PessoaJuridicaDAO pDao = new PessoaJuridicaDAO();
        ResponsavelDAO respD = new ResponsavelDAO();
        int linhas, i=0;
        
        if(!pDao.read().isEmpty()){
            for(PessoaJuridica pf: pDao.read()){
                model.addRow(new Object[]{
                    pf.getIdResponsavel(),
                    "",
                    "",
                    pf.getCnpj()
                });
            }
            linhas = tblPessoaJuridica.getRowCount();
            for(Responsavel r: respD.read()){
                if(r.getIdResponsavel() == (int)tblPessoaJuridica.getValueAt(i, 0)){
                    tblPessoaJuridica.setValueAt(r.getNomeResponsavel(), i, 1);
                    tblPessoaJuridica.setValueAt(r.getDtNascimento(), i, 2);
                    i++;
                }            
                if(i == linhas)
                    break;
            }
        }
    }
    
    public void insereBanco(int id, PessoaJuridica pf, PessoaJuridicaDAO dao, Responsavel resp, ResponsavelDAO respD){
        resp.setIdResponsavel(id);
        resp.setNomeResponsavel(view.getTxtNome().getText());
        resp.setDtNascimento(view.getTxtData().getText());
        respD.create(resp);

        pf.setIdResponsavel(id);
        pf.setCnpj(view.getTxtCnpj().getText());
        dao.create(pf);        
    }
    
    public void readJTabaleForDesc(String desc){
        JTable tblPessoaJuridica = view.getTblPessoaJuridica();
        DefaultTableModel model = (DefaultTableModel) tblPessoaJuridica.getModel();
        model.setNumRows(0);
        PessoaJuridicaDAO pDao = new PessoaJuridicaDAO();
        ResponsavelDAO respD = new ResponsavelDAO();
        int i=0;

        for(PessoaJuridica pf: pDao.readForDesc(desc)){
            model.addRow(new Object[]{
                pf.getIdResponsavel(),
                "",
                "",
                pf.getCnpj()
            });
        }        
        for(Responsavel r: respD.readForDescJuridica(desc)){
            tblPessoaJuridica.setValueAt(r.getNomeResponsavel(), i, 1);
            tblPessoaJuridica.setValueAt(r.getDtNascimento(), i, 2);
            i++;           
        }

    }
}
