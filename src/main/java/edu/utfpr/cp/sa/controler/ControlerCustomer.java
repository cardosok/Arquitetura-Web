/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utfpr.cp.sa.controler;

import edu.utfpr.cp.sa.DAO.ConnectionFactory;
import edu.utfpr.cp.sa.entity.Country;
import edu.utfpr.cp.sa.entity.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author César
 */
public class ControlerCustomer {
    
    Connection con;
    
    public ControlerCustomer(){
        this.con = ConnectionFactory.getConnection();
    }
    
    public boolean cadastra(Customer cus){
        PreparedStatement stmt = null;
        try{
            if(cus != null){
                stmt = con.prepareStatement("INSERT INTO customer (name, phone,age,creditLimit,FK_CountryID) values( ?, ?, ?, ?, ?)");
                stmt.setString(1, cus.getName());
                stmt.setString(2, cus.getPhone());
                stmt.setInt(3, cus.getAge());
                stmt.setDouble(4, cus.getCreditLimit());
                stmt.setInt(4, cus.getCountry().getId());
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
    
        public ArrayList<Customer> searchAll () {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        String sqlQuery = "SELECT id, name, acronym, phoneDigits FROM country";
        ArrayList<Customer> arrayCustomer = new ArrayList<>();
        try{ControlerCountry cont = new ControlerCountry();
            
            stmt = con.prepareStatement(sqlQuery);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Country country = cont.searchById(rs.getInt(6));
                Customer customer = new Customer(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getInt(4),rs.getDouble(5),country);
                arrayCustomer.add(customer);
            }
            
            return arrayCustomer;
            
        }catch(SQLException ex){
            System.err.println("Erro to search countries: "+ ex);
        }
        return null;
    }
 
    public boolean deleteCustomer (String name) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        String sqlQuery = "DELETE FROM customer WHERE name = " + name + " \"; ";
        try{
            stmt = con.prepareStatement(sqlQuery);
            int s = stmt.executeUpdate();
            if(s >0) return true;
            
            else return false;
        }catch(SQLException ex){
            System.err.println("Erro to delete customer: "+ ex);
        }
        return false;
    }
 
}
