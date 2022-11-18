package Data;

import Presentation.Model.Contact;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Presentation.Model.Message;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONSerializer {
    public void writeContactList(int userId, List<Contact> contactList){
        JSONArray contactObjectList = new JSONArray();
        contactList.forEach(contact -> {
            contactObjectList.add(serializeContact(contact));
        });
        try (FileWriter file = new FileWriter(String.valueOf(userId) + ".json")) {
            file.write(contactObjectList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject serializeContact(Contact contact) {
        JSONObject contactObject = new JSONObject();
        contactObject.put("userId", contact.getUserId());
        contactObject.put("username", contact.getUsername());
        JSONArray messageObjectList = new JSONArray();
        contact.getConversation().forEach(message -> {
            messageObjectList.add(serializeMessage(message));
        });
        contactObject.put("conversation", messageObjectList);
        return contactObject;
    }

    private JSONObject serializeMessage(Message message) {
        JSONObject messageObject = new JSONObject();
        messageObject.put("id", message.getId());
        messageObject.put("type", message.getType());
        messageObject.put("senderId", message.getSenderId());
        messageObject.put("receiverId", message.getReceiverId());
        messageObject.put("text", message.getText());
        messageObject.put("created", message.getCreated());
        messageObject.put("isReceived", message.isReceived());
        return messageObject;
    }

    public List<Contact> readContactList(int userId){
        JSONParser jsonParser = new JSONParser();
        List<Contact> contactList = new ArrayList<>();
        try (FileReader reader = new FileReader(String.valueOf(userId) + ".json")) {
            Object contactFile = jsonParser.parse(reader);
            JSONArray contactObjectList = (JSONArray) contactFile;
           // System.out.println(contactObjectList);
            contactObjectList.forEach(contact -> {
                contactList.add(this.parseContactObject((JSONObject) contact));
            });
        } catch (FileNotFoundException e) {
            try (FileWriter file = new FileWriter(String.valueOf(userId) + ".json")) {
                file.flush();
            } catch (IOException error) {
                error.printStackTrace();
            }
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  contactList;
    }

    private Contact parseContactObject(JSONObject contact) {
        int contactId = ((Long) contact.get("userId")).intValue();
        String contactUsername = (String) contact.get("username");
        List<Message> messageList = new ArrayList<>();
        JSONArray conversationObjectList = (JSONArray) contact.get("conversation");
        conversationObjectList.forEach(message -> {
            messageList.add(this.parseMessageObject((JSONObject) message));
        });
        return new Contact(contactId, contactUsername, messageList);
    }

    private Message parseMessageObject(JSONObject message) {
        int messageId = ((Long) message.get("id")).intValue();
        String messageType = (String) message.get("type");
        int messageSenderId = ((Long) message.get("senderId")).intValue();
        int messageReceiverId = ((Long) message.get("receiverId")).intValue();
        String messageText = (String) message.get("text");
        Timestamp messageCreated = (Timestamp) message.get("created");
        Boolean messageReceived = (Boolean) message.get("isReceived");
        return new Message(messageType, messageId, messageText, messageSenderId, messageReceiverId, messageCreated, messageReceived);
    }
}
