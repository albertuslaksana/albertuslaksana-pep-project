package Controller;

import java.sql.SQLException;
import java.util.List;

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
     *  
     */
    private void newUserRegisterHandler(Context context) throws JsonProcessingException, SQLException {
        String jsonString = context.body();
        ObjectMapper om = new ObjectMapper();
        Account user = om.readValue(jsonString, Account.class);
        Account temp = accountService.addAccount(user);
        if(temp == null){
            context.status(400);
            return;
        }
        context.json(temp);
    }

    private void loginHandler(Context context) throws JsonProcessingException, SQLException{
        String jsonString = context.body();
        ObjectMapper om = new ObjectMapper();
        Account user = om.readValue(jsonString, Account.class);
        Account temp = accountService.accountLogin(user);
        if(temp == null){
            context.status(401);
            return;
        }
        context.json(temp);
    }

    private void postMessageHandler(Context context) throws JsonProcessingException, SQLException{
        String jsonString = context.body();
        ObjectMapper om = new ObjectMapper();
        Message user = om.readValue(jsonString, Message.class);
        Message temp = messageService.addMessage(user);
        if(temp == null){
            context.status(400);
            return;
        }
        context.json(temp);
    }

    private void getMessageHandler(Context context) throws SQLException{
        context.json(messageService.getAllMessages());
    }

    private void getMessageByIdHandler(Context context) throws SQLException{
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message temp = messageService.getMessageById(id);
        if(temp == null){
            return;
        }
        context.json(temp);
    }

    private void deleteMessageHandler(Context context) throws SQLException{
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message temp = messageService.deleteMessageById(id);
        if(temp == null){
            return;
        }
        context.json(temp);
    }

    private void updateMessageHandler(Context context) throws JsonProcessingException, SQLException{
        int id = Integer.parseInt(context.pathParam("message_id"));
        String jsonString = context.body();
        ObjectMapper om = new ObjectMapper();
        Message user = om.readValue(jsonString, Message.class);
        Message temp = messageService.updateMessageById(user, id);
        if(temp == null){
            context.status(400);
            return;
        }
        context.json(temp);
    }

    private void getPostsFromUserHandler(Context context) throws SQLException{
        int id = Integer.parseInt(context.pathParam("account_id"));
        List<Message> temp = messageService.getPostsFromUser(id);
        if(temp == null){
            return;
        }
        context.json(messageService.getPostsFromUser(id));
    }



    


}