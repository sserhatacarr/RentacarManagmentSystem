package dao;

import core.Db;
import entity.Model;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ModelDao {
    private Connection connection;
    private final BrandDao brandDao = new BrandDao();

    public ModelDao() {
        this.connection = Db.getInstance();
    }

    public Model getById(int id) {
        String sql = "SELECT * FROM model WHERE model_id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return this.match(rs);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Model> findAll() {
        return this.selectByQuery("SELECT * FROM model ORDER BY model_id ASC");
    }

    public ArrayList<Model> selectByQuery(String query) {
        ArrayList<Model> modelList = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                modelList.add(this.match(rs));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return modelList;
    }

    public boolean save(Model model) {
        String sql = "INSERT INTO model " +
                "(" +
                "model_brand_id, " +
                "model_name, " +
                "model_type, " +
                "model_year, " +
                "model_fuel, " +
                "model_gear" +
                ") VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, model.getBrandId());
            ps.setString(2, model.getName());
            ps.setString(3, model.getType().toString());
            ps.setString(4, model.getYear());
            ps.setString(5, model.getFuel().toString());
            ps.setString(6, model.getGear().toString());
            return ps.executeUpdate() != 0;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return true;
    }

    public boolean update(Model model) {
        String sql = "UPDATE model SET " +
                "model_brand_id = ?, " +
                "model_name = ?, " +
                "model_type = ?, " +
                "model_year = ?, " +
                "model_fuel = ?, " +
                "model_gear = ? " +
                "WHERE model_id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, model.getBrandId());
            ps.setString(2, model.getName());
            ps.setString(3, model.getType().toString());
            ps.setString(4, model.getYear());
            ps.setString(5, model.getFuel().toString());
            ps.setString(6, model.getGear().toString());
            ps.setInt(7, model.getId());
            return ps.executeUpdate() != -1;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return true;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM model WHERE model_id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, id);
            return ps.executeUpdate() != -1;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return true;
    }

    private Model match(ResultSet rs) {
        Model model = new Model();
        try {
            model.setId(rs.getInt("model_id"));
            model.setBrandId(rs.getInt("model_brand_id"));
            model.setName(rs.getString("model_name"));
            model.setType(Model.Type.valueOf(rs.getString("model_type")));
            model.setYear(rs.getString("model_year"));
            model.setFuel(Model.Fuel.valueOf(rs.getString("model_fuel")));
            model.setGear(Model.Gear.valueOf(rs.getString("model_gear")));
            model.setBrand(this.brandDao.getById(rs.getInt("model_brand_id")));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return model;
    }
}
