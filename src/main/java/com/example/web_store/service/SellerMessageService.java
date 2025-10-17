package com.example.web_store.service;

import com.example.web_store.model.SellerMessage;
import com.example.web_store.repository.SellerMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerMessageService {

    @Autowired
    private SellerMessageRepository sellerMessageRepository;

    public SellerMessage sendMessage(SellerMessage message) {
        return sellerMessageRepository.save(message);
    }

    public List<SellerMessage> getAllActiveMessages() {
        return sellerMessageRepository.findByStatus("ACTIVE");
    }

    public List<SellerMessage> getMessagesBySeller(String sellerId) {
        return sellerMessageRepository.findBySellerIdAndStatus(sellerId, "ACTIVE");
    }

    public Optional<SellerMessage> getMessageById(String id) {
        return sellerMessageRepository.findById(id);
    }

    public SellerMessage updateMessage(String id, String newMessageText) {
        Optional<SellerMessage> optionalMessage = sellerMessageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            SellerMessage message = optionalMessage.get();
            message.setMessageText(newMessageText);
            return sellerMessageRepository.save(message);
        }
        return null;
    }

    public boolean deleteMessage(String id) {
        Optional<SellerMessage> optionalMessage = sellerMessageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            SellerMessage message = optionalMessage.get();
            message.setStatus("DELETED");
            sellerMessageRepository.save(message);
            return true;
        }
        return false;
    }
}