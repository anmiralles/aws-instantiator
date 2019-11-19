package es.anmiralles.instantiator.control;

import es.anmiralles.instantiator.entity.Instance;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class InstanceService {

    private List<Instance> instances;

    @PostConstruct
    public void init(){
        this.instances = createDefaultInstance();
    }

    private List<Instance> createDefaultInstance() {
        List<Instance> result = new ArrayList<>();
        result.add(new Instance("12345", "user1", "secret1", "jenkins", "https://myinstance1.local.net"));
        return result;
    }

    public List<Instance> getInstances() {
        return instances;
    }

    public void addInstance(Instance instance) {
        this.instances.add(instance);
    }

    public void deleteInstance(Instance instance) {
        this.instances.remove(instance);
    }
}
