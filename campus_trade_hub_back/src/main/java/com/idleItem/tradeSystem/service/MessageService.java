package com.idleItem.tradeSystem.service;

import com.idleItem.tradeSystem.entity.Message;

import java.util.List;

public interface MessageService {
    boolean addMessage(Message message);
    boolean deleteMessage(Long id);
    Message getMessage(Long id);
    List<Message> getAllMyMessage(Long userId);
    List<Message> getAllIdleMessage(Long idleId);
    boolean updatestatus(long id);
}
