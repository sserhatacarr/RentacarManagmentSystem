package dao;

import core.Db;
import entity.Brand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BrandDao {
    private final Connection connection;

    public BrandDao() {
        this.connection = Db.getInstance();
    }

    public ArrayList<Brand>findAll(){
        ArrayList<Brand> brandList = new ArrayList<>();
        String sql = "SELECT * FROM brand ORDER BY brand_id ASC";
        try{
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while (rs.next()){
             brandList.add(this.match(rs));
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
       return brandList;
    }

    public boolean save (Brand brand){
        String sql = "INSERT INTO brand (brand_name) VALUES (?)";
        try{
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, brand.getName());
            return ps.executeUpdate() != 0;
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return true;
    }

    public boolean update (Brand brand){
        String sql = "UPDATE brand SET brand_name = ? WHERE brand_id = ?";
        try{
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, brand.getName());
            ps.setInt(2, brand.getId());
            return ps.executeUpdate() != -1;
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return true;
    }

    public boolean delete (int id){
        String query = "DELETE FROM brand WHERE brand_id = ?";
        try{
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, id);
            return ps.executeUpdate() != -1;
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return true;
    }

    public Brand getById(int id){
        String sql = "SELECT * FROM brand WHERE brand_id = ?";
        try{
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return this.match(rs);
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Brand match(ResultSet rs){
        Brand brand = new Brand();
        try{
            brand.setId(rs.getInt("brand_id"));
            brand.setName(rs.getString("brand_name"));
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return brand;
    }
}