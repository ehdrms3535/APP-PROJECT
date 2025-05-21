// src/main/java/com/example/demo/dto/MessageDto.java
package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;  // ← 이걸 꼭 추가!

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDto {
    private String sender;
    private String text;

    public MessageDto() {}

    public MessageDto(String sender, String text) {
        this.sender = sender;
        this.text   = text;
    }


    public String getSender() { return sender; }
    public String getText()   { return text;   }

    public void setSender(String sender) { this.sender = sender; }
    public void setText(String text)     { this.text   = text;   }
}
