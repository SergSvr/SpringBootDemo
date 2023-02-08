package com.example.firstexp.service.implementation;

import com.example.firstexp.exceptions.CustomException;
import com.example.firstexp.model.dto.MessageDTO;
import com.example.firstexp.model.entity.Message;
import com.example.firstexp.model.repository.MessagesRepository;
import com.example.firstexp.service.MessageService;
import com.example.firstexp.utils.PaginationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImplementation implements MessageService {
    private final MessagesRepository messagesRepository;
    private final ObjectMapper mapper;

    @Override
    public Message create(MessageDTO messageDTO) {
        Message message = mapper.convertValue(messageDTO, Message.class);
        message.setStatus(false);
        return messagesRepository.save(message);
    }

    @Override
    public Message mark(Long id) {
        Message message = messagesRepository.findById(id).orElseThrow(() -> new CustomException("Сообщение не найдено", HttpStatus.NOT_FOUND));
        message.setStatus(true);
        return message;
    }

    @Override
    public List<MessageDTO> getMessagesDTO(String mail, String folder) {
        List<Message> list;
        if (folder == null || folder.equals("Sent"))
            list = messagesRepository.findAllBySentFrom(mail);
        else if (folder.equals("Received"))
            list = messagesRepository.findAllBySentToAndStatus(mail, false);
        else
            list = messagesRepository.findAllBySentToAndStatus(mail, true);
        return list.stream()
                .map(h -> mapper.convertValue(h, MessageDTO.class))
                .collect(Collectors.toList());
    }

    public ModelMap getMessagesPaged(String mail, String folder, Integer page, Integer perPage, String sort, Sort.Direction order) {
        Pageable pageRequest = PaginationUtil.getPageRequest(page, perPage, sort, order);
        Page<Message> pageResult;
        if (folder == null || folder.equals("Sent"))
            pageResult = messagesRepository.findAllBySentFrom(mail, pageRequest);
        else if (folder.equals("Received"))
            pageResult = messagesRepository.findAllBySentToAndStatus(mail, false, pageRequest);
        else
            pageResult = messagesRepository.findAllBySentToAndStatus(mail, true, pageRequest);
        List<MessageDTO> content = pageResult.getContent().stream()
                .map(message -> mapper.convertValue(message, MessageDTO.class))
                .collect(Collectors.toList());

        PagedListHolder<MessageDTO> result = new PagedListHolder<>(content);
        result.setPage(page);
        result.setPageSize(perPage);
        ModelMap map = new ModelMap();
        map.addAttribute("content", result.getPageList());
        map.addAttribute("pageNumber", page);
        map.addAttribute("pageSize", result.getPageSize());
        map.addAttribute("totalPages", pageRequest.getPageNumber());
        return map;
    }


    @Override
    public void delete(Long id) {
        try {
            messagesRepository.deleteById(id);
        } catch (Exception ex) {
            throw new CustomException("Сообщения не найдены", HttpStatus.NOT_FOUND);
        }
    }
}