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
        if(!dataIsValid(account)){
            return null;
        }
        return this.accountDAO.insertAccount(account);
    }

    public boolean dataIsValid(Account account) throws SQLException{
        if(account.getUsername().isBlank() || 
        account.getPassword().length() < 4 || 
        this.accountDAO.getAccountByUsername(account.getUsername()) != null){
            return false;
        }
        return true;
    }

    public boolean loginIsValid(Account account) throws SQLException{
        if(this.accountDAO.getAccountByUsername(account.getUsername()) != null &&
        this.accountDAO.getAccountByUsername(account.getUsername()).getPassword().equals(account.getPassword())){
            return true;
        }
        return false;
    }

    public Account getAccountByUsername(String username) throws SQLException{
        return this.accountDAO.getAccountByUsername(username);
    }

    public Account accountLogin(Account account) throws SQLException{
        if(!loginIsValid(account)){
            return null;
        }
        return getAccountByUsername(account.getUsername());
    }

    public Account getAccountById(int id) throws SQLException{
        return this.accountDAO.getAccountById(id);
    }
    
}