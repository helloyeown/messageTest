package com.example.demo.mapper;

import com.example.demo.model.dto.messages.GetMessages;
import com.example.demo.model.entity.EntitySample;
import com.example.demo.model.entity.Message;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-20T14:35:29+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class MessageMapperImpl implements MessageMapper {

    @Override
    public GetMessages toDTO(Message message) {
        if ( message == null ) {
            return null;
        }

        GetMessages getMessages = new GetMessages();

        getMessages.setSender( messageSenderName( message ) );
        getMessages.setReceiver( messageReceiverName( message ) );
        getMessages.setId( message.getId() );
        getMessages.setText( message.getText() );
        getMessages.setSendedDt( message.getSendedDt() );
        getMessages.setRead( message.isRead() );
        getMessages.setReadAt( message.getReadAt() );

        return getMessages;
    }

    private String messageSenderName(Message message) {
        if ( message == null ) {
            return null;
        }
        EntitySample sender = message.getSender();
        if ( sender == null ) {
            return null;
        }
        String name = sender.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String messageReceiverName(Message message) {
        if ( message == null ) {
            return null;
        }
        EntitySample receiver = message.getReceiver();
        if ( receiver == null ) {
            return null;
        }
        String name = receiver.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
