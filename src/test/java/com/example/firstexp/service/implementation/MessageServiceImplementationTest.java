package com.example.firstexp.service.implementation;

import com.example.firstexp.model.dto.MessageDTO;
import com.example.firstexp.model.entity.Message;
import com.example.firstexp.model.repository.MessagesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MessageServiceImplementationTest {

    @InjectMocks
    private MessageServiceImplementation messageServiceImplementation;

    @Mock
    private MessagesRepository messagesRepository;

    @Spy
    private ObjectMapper mapper;

    @Test
    public void create() {
        mapper.registerModule(new JavaTimeModule());
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setSentFrom("123@123.ru");
        messageDTO.setSentTo("123@123.ru");
        messageDTO.setText("TESTTEXT");
        messageDTO.setDate(LocalDateTime.now());
        when(messagesRepository.save(any(Message.class))).thenAnswer(i -> i.getArguments()[0]);
        Message res = messageServiceImplementation.create(messageDTO);
        assertEquals(res.getText(), messageDTO.getText());
    }

    @Test
    public void mark() {

    }

    @Test
    public void getMessagesDTO() {

    }


    @Test
    public void delete() {
    }


    @Test
    public void getMessagesPaged() {
    }


}