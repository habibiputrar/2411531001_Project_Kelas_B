package DAO;

import model.Service;
import java.util.List;

public interface ServiceDAO {
    void save(Service service);
    List<Service> show();
    void update(Service service);
    void delete(String id);
}
