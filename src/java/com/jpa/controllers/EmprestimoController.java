package com.jpa.controllers;

import com.jpa.entities.Emprestimo;
import com.jpa.controllers.util.JsfUtil;
import com.jpa.controllers.util.PaginationHelper;
import com.jpa.entities.Cliente;
import com.jpa.entities.Livro;
import com.jpa.sessions.EmprestimoFacade;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "emprestimoController")
@SessionScoped
public class EmprestimoController implements Serializable {

    private Emprestimo current;
    private DataModel items = null;
    private boolean devolucao = false;
    private boolean report = false;
    @EJB
    private com.jpa.sessions.EmprestimoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public EmprestimoController() {
    }

    public Emprestimo getSelected() {
        if (current == null) {
            current = new Emprestimo();
            selectedItemIndex = -1;
        }
        return current;
    }

    private EmprestimoFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                // Preenche a lista de items
                // Adicionar condicionais de acordo com a página
                @Override
                public DataModel createPageDataModel() {
                    if(!devolucao && !report)
                    {
                        return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                    }
                    else if(devolucao && !report)
                    {
                        return new ListDataModel(getFacade().findAllDevolver(current));
                    }
                    else
                    {
                        return new ListDataModel(getFacade().findAllByCliente(current.getCliente()));
                    }
                }
            };
        }
        return pagination;
    }
    
    public void changeCliente()
    {
        devolucao = false;
        report = true;
        recreateModel(); // items = null
    }
    
    public String getNomeLivro(Livro l)
    {
        return l.getNome();
    }
    
    public String getNomeCliente(Cliente c)
    {
        return c.getNome();
    }

    public String prepareList() {
        devolucao = false;
        report = false;
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Emprestimo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }
    
    public String prepareViewDevolver() {
        current = (Emprestimo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ViewDevolver";
    }

    public String prepareCreate() {
        current = new Emprestimo();
        selectedItemIndex = -1;
        return "Create";
    }
    
    public String prepareDevolver() {
        current = null;
        selectedItemIndex = -1;
        return "Devolver";
    }

    // ALTERAR PARA RETIRAR AO INVÉS DE CREATE
    public String create() {
        try {
            
            // FOR PARA CADA LIVRO SELECIONADO
            
                Emprestimo aux = current.getClone();
            
                // SETAR DATA DE RETIRADA = NOW
                current.setDataretirada(new Date());

                // CALCULAR DATA DE ENTREGA PREVISTA
                current.setDataprevista(calcularDataDevolucao());
                
                current.getLivro().setSituacao(1);

                getFacade().emprestar(current, current.getLivro());
            
            // FIM FOR
            
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("EmprestimoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public String devolver() {
        try {
            
            // Selecionar o livro a ser devolvido.
            // OU devolver todos
            // Calcular se existe atraso, apresentar mensagem de livro devolvido com atraso.
            
            // Calcula a diferença de dias entre a entrega e a data prevista
            long dias = calcularAtraso(current);

            // Se for positivo quer dizer que está sendo entregue com atraso
            // ATRASO
            if(dias > 0)
            {
                // Informar que foi entregue com atraso
            }
            // EM DIA
            else
            {
                // Informar que foi entregue em dia
            }
            
            // Atualiza a data de entrega
            current.setDataentrega(new Date());
            
            //Atualizar a disponibilidade do livro
            current.getLivro().setSituacao(0);
            
            // Atualiza o emprestimo
            getFacade().devolver(current, current.getLivro());
            
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("EmprestimoDevolvido"));
            return prepareViewDevolver();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public String prepareListDevolverUsuario() {
        devolucao = true;
        report = false;
        recreateModel(); // items = null
        return "ListDevolver";
    }
    
    public String prepareListEmprestimosCliente() {
        devolucao = false;
        report = false;
        recreateModel(); // items = null
        return "ListEmprestimosCliente";
    }
    
    // Calcula se tem atraso na devolução de um livro emprestado
    private static long calcularAtraso(Emprestimo emp)
    {
        return getDateDiff(emp.getDataprevista(), new Date(), TimeUnit.DAYS);
    }
    
    // Calcula a diferença entre duas datas e retorna na unidade de tempo enviada por parâmetro
    private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    public String prepareEdit() {
        current = (Emprestimo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("EmprestimoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Emprestimo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("EmprestimoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = Emprestimo.class)
    public static class EmprestimoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EmprestimoController controller = (EmprestimoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "emprestimoController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Emprestimo) {
                Emprestimo o = (Emprestimo) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Emprestimo.class.getName());
            }
        }
    }
    
    // Retorna uma data igual a hoje mais 14 dias
    public static Date calcularDataDevolucao()
    {
        // Hoje
        Date dt = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(dt); 

        // Adiciona 14 dias
        c.add(Calendar.DATE, 14);

        dt = c.getTime();

        return dt;
    }

}
