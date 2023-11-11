package business;

import core.Helper;
import dao.ModelDao;
import entity.Model;

import java.util.ArrayList;

public class ModelManager {
    private final ModelDao modelDao = new ModelDao();

    public Model getById(int id){
        return this.modelDao.getById(id);
    }

    public ArrayList<Model> findAll(){
        return this.modelDao.findAll();
    }

    public ArrayList<Object[]> getforTable(int size, ArrayList<Model> modelList){
        ArrayList<Object[]> modelRowList = new ArrayList<>();
        for (Model model : modelList){
            Object[] row = new Object[size];
            int i = 0;
            row[i++] = model.getId();
            row[i++] = model.getBrand().getName();
            row[i++] = model.getName();
            row[i++] = model.getType();
            row[i++] = model.getYear();
            row[i++] = model.getFuel();
            row[i++] = model.getGear();
            modelRowList.add(row);
        }
        return modelRowList;
    }

    public boolean save(Model model){
        if (this.getById(model.getId())!= null){
            Helper.showMessage("error");
            return false;
        }
        return this.modelDao.save(model);
    }

    public boolean update(Model model){
        if (this.getById(model.getId()) == null){
            Helper.showMessage("notFound");
            return false;
        }
        return this.modelDao.update(model);
    }

    public boolean delete(int id){
        if (this.getById(id) == null){
            Helper.showMessage("notFound");
            return false;
        }
        return this.modelDao.delete(id);
    }
}
