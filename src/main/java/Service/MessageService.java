package Service;

import java.sql.SQLException;
import java.util.List;

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO){
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    public List<Message> getAllMessages() throws SQLException{
        return this.messageDAO.getAllMessages();
    }

    public Message addMessage(Message message) throws SQLException{
        if(!dataIsValid(message)){
            return null;
        }
        return this.messageDAO.insertMessage(message);
    }

    public Message getMessageById(int id) throws SQLException{
        if(this.messageDAO.getMessageById(id) == null){
            return null;
        }
        return this.messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(int id) throws SQLException{
        if(this.messageDAO.getMessageById(id) == null){
            return null;
        }
        Message temp = this.messageDAO.getMessageById(id);
        this.messageDAO.deleteMessageById(id);
        return temp;
    }
    
    public Message updateMessageById(Message message, int id) throws SQLException{
        if(!validUpdate(message, id)){
            return null;
        }
        this.messageDAO.updateMessageById(id, message.message_text);
        return this.messageDAO.getMessageById(id);
    }

    public List<Message> getPostsFromUser(int id) throws SQLException{
        if(this.messageDAO.getPostsFromUser(id) == null){
            return null;
        }
        return this.messageDAO.getPostsFromUser(id);
    }

    public boolean dataIsValid(Message message) throws SQLException{
        if(message.getMessage_text().isBlank() || 
        message.getMessage_text().length() > 255 || 
        this.accountDAO.getAccountById(message.getPosted_by()) == null){
            return false;
        }
        return true;
    }

    public boolean validUpdate(Message message, int id) throws SQLException{
        if(message.getMessage_text().isBlank() || 
        message.getMessage_text().length() > 255 || 
        this.accountDAO.getAccountById(id) == null){
            return false;
        }
        return true;
    }

}
