package es.anmiralles.instantiator.boundary;

import es.anmiralles.instantiator.control.AwsApiService;
import es.anmiralles.instantiator.control.InstanceService;
import es.anmiralles.instantiator.entity.Instance;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class InstanceListBean implements Serializable {

    private static final long serialVersionUID = 4773746274170179581L;

    private List<Instance> instances;
    private Instance selectedInstance;

    @Inject
    private InstanceService instanceService;

    @Inject
    private AwsApiService awsApiService;

    @PostConstruct
    public void init() {
        instances = instanceService.getInstances();
    }

    public List<Instance> getInstances() {
        return instances;
    }

    /**
     * View Controller for deleting an AWS instance
     * @return navigation
     */
    public String deleteInstance() {

        awsApiService.deleteAwsInstance(selectedInstance.getInstanceId());

        // Removing from instances list
        instanceService.deleteInstance(selectedInstance);

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Deletion successful", "Instance deleted: " + selectedInstance.getInstanceId()));
        context.getExternalContext().getFlash().setKeepMessages(true);

        return "/instances.xhtml?faces-redirect=true";
    }

    public Instance getSelectedInstance() {
        return selectedInstance;
    }

    public void setSelectedInstance(Instance selectedInstance) {
        this.selectedInstance = selectedInstance;
    }
}
