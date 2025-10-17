package com.example.web_store.controller;

import com.example.web_store.model.SellerMessage;
import com.example.web_store.service.MarketingExecutiveService;
import com.example.web_store.service.SellerMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/marketing")
public class MarketingExecutiveController {

    @Autowired
    private MarketingExecutiveService marketingExecutiveService;

    @Autowired
    private SellerMessageService sellerMessageService;

    @GetMapping("/products-with-ratings/{productId}")
    public ResponseEntity<Map<String, Object>> getProductWithRatings(@PathVariable String productId) {
        Map<String, Object> result = marketingExecutiveService.getProductWithRatings(productId);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/send-message")
    public SellerMessage sendMessageToSeller(@RequestBody SellerMessage message) {
        return sellerMessageService.sendMessage(message);
    }

    @GetMapping("/messages")
    public List<SellerMessage> getAllMessages() {
        return sellerMessageService.getAllActiveMessages();
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<SellerMessage> getMessageById(@PathVariable String id) {
        Optional<SellerMessage> message = sellerMessageService.getMessageById(id);
        return message.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/messages/{id}")
    public ResponseEntity<SellerMessage> updateMessage(@PathVariable String id, @RequestBody String newMessage) {
        SellerMessage updatedMessage = sellerMessageService.updateMessage(id, newMessage);
        if (updatedMessage != null) {
            return ResponseEntity.ok(updatedMessage);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable String id) {
        boolean deleted = sellerMessageService.deleteMessage(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}