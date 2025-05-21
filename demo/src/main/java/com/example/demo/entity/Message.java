package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "message")
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_id")
    private Long groupId;

    private String sender;
    private String text;

    // getter / setter
    public Long getId()       { return id;       }
    public Long getGroupId()  { return groupId;  }
    public String getSender() { return sender;   }
    public String getText()   { return text;     }

    public void setId(Long id)             { this.id = id;         }
    public void setGroupId(Long groupId)   { this.groupId = groupId; }
    public void setSender(String sender)   { this.sender = sender; }
    public void setText(String text)       { this.text = text;     }
}
