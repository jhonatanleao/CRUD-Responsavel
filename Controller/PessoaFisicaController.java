/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.bean.PessoaFisica;
import Model.bean.Responsavel;
import Model.dao.PessoaFisicaDAO;
import Model.dao.ResponsavelDAO;
import View.TelaPessoaFisica;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class PessoaFisicaController {
    private TelaPessoaFisica view;
    
    public PessoaFisicaController(){
        view = new TelaPessoaFisica();
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
                if(view.getTblPessoaFisica().getSelectedRow() != -1){
                    atualizar();
                }else{
                    JOptionPane.showMessageDialog(null, "Selecione alguma linha");
                }                     
            }
        });

        this.view.getBntExcluir().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(view.getTblPessoaFisica().getSelectedRow() != -1){
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
        
        this.view.getTblPessoaFisica().addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(view.getTblPessoaFisica().getSelectedRow() != -1){
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
        PessoaFisica pf = new PessoaFisica();
        PessoaFisicaDAO dao = new PessoaFisicaDAO();
        Responsavel resp = new Responsavel();
        ResponsavelDAO respD = new ResponsavelDAO();

        id = dao.maxId();
        if(id == 0){
            id = 30;
            insereBanco(id, pf, dao, resp, respD);        
        } else {
            id++;
            insereBanco(id, pf, dao, resp, respD);
        }
        limpaLabel();
        readJTabale();        
    }    
    
    public void atualizar(){
        PessoaFisica pf = new PessoaFisica();
        PessoaFisicaDAO dao = new PessoaFisicaDAO();
        Responsavel resp = new Responsavel();
        ResponsavelDAO respD = new ResponsavelDAO();
        int id;

        id = (int)view.getTblPessoaFisica().getValueAt(view.getTblPessoaFisica().getSelectedRow(), 0);

        resp.setIdResponsavel(id);
        resp.setNomeResponsavel(view.getTxtNome().getText());
        resp.setDtNascimento(view.getTxtData().getText());
        respD.update(resp);

        pf.setIdResponsavel(id);
        pf.setCpf(view.getTxtCpf().getText());
        pf.setNaturalidade(view.getTxtNaturalidade().getText());
        dao.update(pf);

        limpaLabel();
        readJTabale();        
    }
    
    public void excluir(){
        PessoaFisica pf = new PessoaFisica();
        PessoaFisicaDAO dao = new PessoaFisicaDAO();
        Responsavel resp = new Responsavel();
        ResponsavelDAO respD = new ResponsavelDAO();
        int id = (int)view.getTblPessoaFisica().getValueAt(view.getTblPessoaFisica().getSelectedRow(), 0);

        resp.setIdResponsavel(id);
        pf.setIdResponsavel(id);

        dao.delete(pf);
        respD.delete(resp);

        limpaLabel();
        readJTabale();        
    }
    
    public void preencheMenu(){
        view.getTxtNome().setText(view.getTblPessoaFisica().getValueAt(view.getTblPessoaFisica().getSelectedRow(), 1).toString());
        view.getTxtNaturalidade().setText(view.getTblPessoaFisica().getValueAt(view.getTblPessoaFisica().getSelectedRow(), 2).toString());
        view.getTxtData().setText(view.getTblPessoaFisica().getValueAt(view.getTblPessoaFisica().getSelectedRow(), 3).toString());
        view.getTxtCpf().setText(view.getTblPessoaFisica().getValueAt(view.getTblPessoaFisica().getSelectedRow(), 4).toString());        
    }
    
    public void alteraTela(){
        view.dispose();
        PessoaJuridicaController juridica = new PessoaJuridicaController();        
    }
    
    public void limpaLabel(){
        view.getTxtNome().setText("");
        view.getTxtData().setText("");
        view.getTxtCpf().setText("");
        view.getTxtNaturalidade().setText("");
        view.getTxtBusca().setText("");
    }
    
    public void insereBanco(int id, PessoaFisica pf, PessoaFisicaDAO dao, Responsavel resp, ResponsavelDAO respD){
        resp.setIdResponsavel(id);
        resp.setNomeResponsavel(view.getTxtNome().getText());
        resp.setDtNascimento(view.getTxtData().getText());
        respD.create(resp);

        pf.setIdResponsavel(id);
        pf.setCpf(view.getTxtCpf().getText());
        pf.setNaturalidade(view.getTxtNaturalidade().getText());
        dao.create(pf);
    }
    
    public void readJTabale(){
        JTable tblPessoaFisica = view.getTblPessoaFisica();
        DefaultTableModel model = (DefaultTableModel) tblPessoaFisica.getModel();
        model.setNumRows(0);
        PessoaFisicaDAO pDao = new PessoaFisicaDAO();
        ResponsavelDAO respD = new ResponsavelDAO();
        int linhas, i=0;
        
        if(!pDao.read().isEmpty()){
            for(PessoaFisica pf: pDao.read()){
                model.addRow(new Object[]{
                    pf.getIdResponsavel(),
                    "",
                    pf.getNaturalidade(),
                    "",
                    pf.getCpf()
                });
            }
            linhas = tblPessoaFisica.getRowCount();
            for(Responsavel r: respD.read()){
                if(r.getIdResponsavel() == (int)tblPessoaFisica.getValueAt(i, 0)){
                    tblPessoaFisica.setValueAt(r.getNomeResponsavel(), i, 1);
                    tblPessoaFisica.setValueAt(r.getDtNascimento(), i, 3);
                    i++;
                }            
                if(i == linhas)
                    break;
            }
        }
    }
    
    public void readJTabaleForDesc(String desc){ //atualizar aqui igual acima
        JTable tblPessoaFisica = view.getTblPessoaFisica();
       DefaultTableModel model = (DefaultTableModel) tblPessoaFisica.getModel();
        model.setNumRows(0);
        PessoaFisicaDAO pDao = new PessoaFisicaDAO();
        ResponsavelDAO respD = new ResponsavelDAO();
        int i=0;

        for(PessoaFisica pf: pDao.readForDesc(desc)){
            model.addRow(new Object[]{
                pf.getIdResponsavel(),
                "",
                pf.getNaturalidade(),
                "",
                pf.getCpf()
            });
        }        
        for(Responsavel r: respD.readForDescFisica(desc)){
            tblPessoaFisica.setValueAt(r.getNomeResponsavel(), i, 1);
            tblPessoaFisica.setValueAt(r.getDtNascimento(), i, 3);
            i++;           
        }

    }
}
