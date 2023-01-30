package com.example.firstexp.controllers;

import com.example.firstexp.model.dto.MessageDTO;
import com.example.firstexp.model.entity.Message;
import com.example.firstexp.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@Tag(name = "Сообщения")
public class MessagesConroller {

    private final MessageService messageService;

    @PostMapping
    @Operation(summary = "создать сообщение")
    public Message createMessage(@RequestBody MessageDTO messageDTO) {
        return messageService.create(messageDTO);
    }

    @PutMapping
    @Operation(summary = "пометить прочитанным")
    public ResponseEntity<Message> mark(@RequestParam Long id) {
        return ResponseEntity.ok(messageService.mark(id));
    }

    @GetMapping
    @Operation(summary = "получить сообщения")
    public List<MessageDTO> getMessages(@RequestParam String mail, @RequestParam(required = false) String folder) {
        return messageService.getMessagesDTO(mail, folder);
    }

    @GetMapping("/paged")
    @Operation(summary = "получить сообщения с разбивкой по страницам")
    public ModelMap getMessagesP(@RequestParam String mail,
                                 @RequestParam(required = false) String folder,
                                 @RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer perPage,
                                 @RequestParam(required = false, defaultValue = "name") String sort,
                                 @RequestParam(required = false, defaultValue = "ASC") Sort.Direction order) {
        return messageService.getMessagesPaged(mail, folder, page, perPage, sort, order);
    }

    @DeleteMapping
    @Operation(summary = "удалить сообщение")
    public ResponseEntity<HttpStatus> deleteMessage(@RequestParam Long id) {
        messageService.delete(id);
        return ResponseEntity.ok().build();
    }

}
