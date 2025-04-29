package Controller;

import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::newUserRegisterHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getMessageHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getPostsFromUserHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void newUserRegisterHandler(Context context) {
        String jsonString = context.body();
        ObjectMapper om = new ObjectMapper();
        try {
            Account user = om.readValue(jsonString, Account.class);
            if(user.getUsername().isBlank() || user.getPassword().length() < 4 || accountService.getAccountByUsername(user.getUsername()) != null){
                context.status(400);
                return;
            }
            Account newAccount = this.accountService.addAccount(user);
            user.setAccount_id(newAccount.getAccount_id());
            context.json(user);
        } catch (JsonProcessingException e) {
            System.out.println("BAD");
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    private void loginHandler(Context context){
        String jsonString = context.body();
        ObjectMapper om = new ObjectMapper();
        try {
            Account user = om.readValue(jsonString, Account.class);
            if(accountService.getAccountByUsername(user.getUsername()) != null && accountService.getAccountByUsername(user.getUsername()).getPassword().equals(user.getPassword())){
                user.setAccount_id(accountService.getAccountByUsername(user.getUsername()).getAccount_id());
                context.json(user);
            } else {
                context.status(401);
                return;
            }
        } catch (JsonProcessingException e) {
            System.out.println("BAD");
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    private void postMessageHandler(Context context){
        String jsonString = context.body();
        ObjectMapper om = new ObjectMapper();
        try {
            Message user = om.readValue(jsonString, Message.class);
            if(user.getMessage_text().isBlank() || user.getMessage_text().length() > 255 || accountService.getAccountById(user.getPosted_by()) == null){
                context.status(400);
                return;
            }
            Message newMessage = this.messageService.addMessage(user);
            user.setMessage_id(newMessage.getMessage_id());
            context.json(user);
        } catch (JsonProcessingException e) {
            System.out.println("BAD");
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    private void getMessageHandler(Context context) throws SQLException{
        context.json(messageService.getAllMessages());
    }

    private void getMessageByIdHandler(Context context) throws SQLException{
        int id = Integer.parseInt(context.pathParam("message_id"));
        if(messageService.getMessageById(id) == null){
            return;
        }
        context.json(messageService.getMessageById(id));
    }

    private void deleteMessageHandler(Context context) throws SQLException{
        int id = Integer.parseInt(context.pathParam("message_id"));
        if(messageService.getMessageById(id) == null){
            return;
        }
        context.json(messageService.getMessageById(id));
        messageService.deleteMessageById(id);
    }

    private void updateMessageHandler(Context context){
        int id = Integer.parseInt(context.pathParam("message_id"));
        String jsonString = context.body();
        ObjectMapper om = new ObjectMapper();
        try {
            Message user = om.readValue(jsonString, Message.class);
            if(user.getMessage_text().isBlank() || user.getMessage_text().length() > 255 || accountService.getAccountById(id) == null){
                context.status(400);
                return;
            }
            this.messageService.updateMessageById(id, user.getMessage_text());;
            context.json(messageService.getMessageById(id));
        } catch (JsonProcessingException e) {
            System.out.println("BAD");
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    private void getPostsFromUserHandler(Context context) throws SQLException{
        int id = Integer.parseInt(context.pathParam("account_id"));
        if(messageService.getPostsFromUser(id) == null){
            return;
        }
        context.json(messageService.getPostsFromUser(id));
    }



    


}