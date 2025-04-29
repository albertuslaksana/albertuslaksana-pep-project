package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    
    public List<Message> getAllMessages() throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        String sql = "select * from message";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"), rs.getString("message_text"),rs.getLong("time_posted_epoch"));
            messages.add(message);
        }
        return messages;
    }

    public Message insertMessage(Message message) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        String sql = "insert into message(posted_by, message_text, time_posted_epoch) values (?,?,?) " ;
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, message.getPosted_by());
        preparedStatement.setString(2, message.getMessage_text());
        preparedStatement.setLong(3, message.getTime_posted_epoch());

        
        preparedStatement.executeUpdate();
        ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
        if(pkeyResultSet.next()){
            int generated_message_id = (int) pkeyResultSet.getLong(1);
            return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
        }
        return null;
    }

    public Message getMessageById(int id) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        String sql = "select * from message where message_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //write preparedStatement's setInt method here.
        preparedStatement.setInt(1, id);

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Message message = new Message(rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
            return message;
        }
        return null;
    }

    public void deleteMessageById(int id) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        String sql = "delete from message where message_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //write preparedStatement's setInt method here.
        preparedStatement.setInt(1, id);

        preparedStatement.executeUpdate();
    }

    public void updateMessageById(int id, String message) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        String sql = "update message set message_text = ? where message_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //write preparedStatement's setInt method here.
        preparedStatement.setString(1, message);
        preparedStatement.setInt(2, id);

        preparedStatement.executeUpdate();
    }

    public List<Message> getPostsFromUser(int id) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        String sql = "select * from message where posted_by = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"), rs.getString("message_text"),rs.getLong("time_posted_epoch"));
            messages.add(message);
        }
        return messages;
    }


}
