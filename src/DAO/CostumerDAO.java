package DAO;

import model.Costumer;
import java.util.List;

public interface CostumerDAO {
    void save(Costumer costumer);
    List<Costumer> show();
    void update(Costumer costumer);
    void delete(String id);
}
