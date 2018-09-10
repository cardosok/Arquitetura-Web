/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utfpr.cp.sa.controler;

import edu.utfpr.cp.sa.DAO.ConnectionFactory;
import edu.utfpr.cp.sa.entity.Country;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author César
 */
public class ControlerCountry {
    Connection con;
    
    public ControlerCountry(){
        this.con = ConnectionFactory.getConnection();
    }
    
    public boolean cadastra(Country country){
        PreparedStatement stmt = null;
        try{
            if(country != null){
                stmt = con.prepareStatement("INSERT INTO country (name,acronym,phoneDigits) values( ?, ?, ?)");
                stmt.setString(1, country.getName());
                stmt.setString(2, country.getAcronym());
                stmt.setInt(3, country.getPhoneDigits());
                stmt.executeUpdate();
                return true;
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao Cadastrar: "+ ex);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        return false;
    }
    
     public ArrayList<Country> searchAll () {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        String sqlQuery = "SELECT id, name, acronym, phoneDigits FROM country";
        ArrayList<Country> arrayCountry = new ArrayList<>();
        try{
            stmt = con.prepareStatement(sqlQuery);
            rs = stmt.executeQuery();
            while(rs.next()){
                Country country = new Country(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getInt(4));
                arrayCountry.add(country);
            }
            
            return arrayCountry;
            
        }catch(SQLException ex){
            System.err.println("Erro to search countries: "+ ex);
        }
        return null;
    }
    
     public boolean deleteCountry (String name) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        String sqlQuery = "DELETE FROM country WHERE name = " + name + " \"; ";
        try{
            stmt = con.prepareStatement(sqlQuery);
            int s = stmt.executeUpdate();
            if(s >0) return true;
            
            else return false;
        }catch(SQLException ex){
            System.err.println("Erro to search countries: "+ ex);
        }
        return false;
    }
    
     
     public Country searchById (int id) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        String sqlQuery = "SELECT id, name, acronym, phoneDigits FROM country where id = " + id + " \";";
        Country country = null;
        try{
            stmt = con.prepareStatement(sqlQuery);
            rs = stmt.executeQuery();
            while(rs.next()){
                country = new Country(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getInt(4));
            }
            
            return country;
            
        }catch(SQLException ex){
            System.err.println("Erro to search countries: "+ ex);
        }
        return null;
    }
}
