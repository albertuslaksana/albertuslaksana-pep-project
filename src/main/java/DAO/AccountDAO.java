package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Util.ConnectionUtil;


public class AccountDAO {

    public List<Account> getAllAccounts() throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        String sql = "select * from account";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            accounts.add(account);
        }
        return accounts;
    }

    public Account insertAccount(Account account) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        String sql = "insert into account(username, password) values (?,?) " ;
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getPassword());
        
        preparedStatement.executeUpdate();
        ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
        if(pkeyResultSet.next()){
            int generated_account_id = (int) pkeyResultSet.getLong(1);
            return new Account(generated_account_id, account.getUsername(), account.getPassword());
        }
        return null;
    }

    public Account getAccountByUsername(String username) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        String sql = "select * from account where username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //write preparedStatement's setInt method here.
        preparedStatement.setString(1, username);

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Account account = new Account(rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password"));
            return account;
        }
        return null;

    }

    public Account getAccountById(int id) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        String sql = "select * from account where account_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //write preparedStatement's setInt method here.
        preparedStatement.setInt(1, id);

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Account account = new Account(rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password"));
            return account;
        }
        return null;
    }
    
}