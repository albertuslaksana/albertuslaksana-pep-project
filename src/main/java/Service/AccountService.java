package Service;

import java.sql.SQLException;
import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService{
    private AccountDAO accountDAO;
    
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public List<Account> getAllAccount() throws SQLException{
        return this.accountDAO.getAllAccounts();
    }

    public Account addAccount(Account account) throws SQLException{
        return this.accountDAO.insertAccount(account);
    }

    public Account getAccountByUsername(String username) throws SQLException{
        return this.accountDAO.getAccountByUsername(username);
    }

    public Account getAccountById(int id) throws SQLException{
        return this.accountDAO.getAccountById(id);
    }
    
}