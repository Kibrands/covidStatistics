package es.rigel.covid.persistence;

import es.rigel.covid.model.Registry;
import es.rigel.covid.service.CovidService;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
@Data
@NoArgsConstructor
public class WebAppBean implements Serializable {

    private List<Registry> registryList;
    private String currentNav = "main.xhtml";
    private String searchType = "";
    private String singleSearch = "";
    private Date date;
    private Date utilDateToInsert;
    private Date utilDateToUpdate;
    private Date utilDateToDelete;
    private String countryCode;
    private String countryCodeToDelete;
    private Registry foundRegistry;
    private Registry registryToSave = new Registry();
    private Registry registryToUpdate = new Registry();

    @ManagedProperty("#{covidServiceImpl}")
    private CovidService covidService;

    @PostConstruct
    private void init() {
        registryList = this.covidService.getAllRegistries();
    }

    public void getRegistryByDateAndCountry() {
        foundRegistry = this.covidService.getRegistry(date, countryCode);
        singleSearch = "singleSearch.xhtml";
    }

    public void saveRegistry() {
        this.registryToSave.setDate(new java.sql.Date(this.utilDateToInsert.getTime()));
        this.covidService.saveRegistry(registryToSave);
        registryToSave = new Registry();
        update();
        addMessage("Se ha insertado el registro");
    }

    public void updateRegistry() {
        this.registryToUpdate.setDate(new java.sql.Date(this.utilDateToUpdate.getTime()));
        if (this.covidService.getRegistry(utilDateToUpdate, this.registryToUpdate.getCountryCode()) == null) {
            errorMessage("El registro no existe");
        } else {
            this.covidService.updateRegistry(registryToUpdate);
            registryToUpdate = new Registry();
            update();
            addMessage("Se ha modificado el registro");
        }
    }

    public void deleteRegistry() {
        if (this.covidService.getRegistry(utilDateToDelete, countryCodeToDelete) == null) {
            errorMessage("El registro no existe");
        } else {
            this.covidService.deleteRegistry(utilDateToDelete, countryCodeToDelete);
            update();
            addMessage("Registro borrado correctamente");
        }
    }

    private void update() {
        registryList = this.covidService.getAllRegistries();
        if (date != null && countryCode != null) {
            getRegistryByDateAndCountry();
        }
    }

    private void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private void errorMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
