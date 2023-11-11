package business;

import core.Helper;
import dao.BrandDao;
import entity.Brand;

import java.util.ArrayList;

public class BrandManager {

    private final BrandDao brandDao;

    public BrandManager() {
        this.brandDao = new BrandDao();
    }

    public ArrayList<Object[]> getForTable(int size) {
        ArrayList<Object[]> brandRowList = new ArrayList<>();
        for (Brand brand : this.findAll()) {
            Object[] row = new Object[size];
            int i = 0;
            row[i++] = brand.getId();
            row[i++] = brand.getName();
            brandRowList.add(row);
        }
        return brandRowList;
    }

    public ArrayList<Brand> findAll() {
        return this.brandDao.findAll();
    }

    public boolean save(Brand brand) {
        if (brand.getId() != 0) {
            Helper.showMessage("error");
        }
        return this.brandDao.save(brand);
    }

    public Brand getById(int id) {
        return this.brandDao.getById(id);
    }

    public boolean update(Brand brand) {
        if (brand.getId() == 0) {
            Helper.showMessage("notFound");
        }
        return this.brandDao.update(brand);
    }

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMessage("notFound");
            return false;
        }
        return this.brandDao.delete(id);
    }
}
