package Service;

import java.sql.SQLException;
import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public List<Message> getAllMessages() throws SQLException{
        return this.messageDAO.getAllMessages();
    }

    public Message addMessage(Message message) throws SQLException{
        return this.messageDAO.insertMessage(message);
    }

    public Message getMessageById(int id) throws SQLException{
        return this.messageDAO.getMessageById(id);
    }

    public void deleteMessageById(int id) throws SQLException{
        this.messageDAO.deleteMessageById(id);
    }
    
    public void updateMessageById(int id, String message) throws SQLException{
        this.messageDAO.updateMessageById(id, message);
    }

    public List<Message> getPostsFromUser(int id) throws SQLException{
        return this.messageDAO.getPostsFromUser(id);
    }

}
