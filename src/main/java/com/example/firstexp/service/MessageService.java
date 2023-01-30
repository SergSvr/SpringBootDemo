package com.example.firstexp.service;

import com.example.firstexp.model.dto.MessageDTO;
import com.example.firstexp.model.entity.Message;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelMap;

import java.util.List;


public interface MessageService {
    Message create(MessageDTO messageDTO);

    Message mark(Long id);

    List<MessageDTO> getMessagesDTO(String mail, String folder);

    void delete(Long id);

    ModelMap getMessagesPaged(String mail, String folder, Integer page, Integer perPage, String sort, Sort.Direction order);
}
