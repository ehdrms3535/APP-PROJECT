// CreateGroupRequest.java (com.example.demo.dto)
package com.example.demo.dto;
import java.util.List;
public class CreateGroupRequest {
    private String name;
    private Long ownerId;
    private List<Long> memberIds;
    public CreateGroupRequest() {}
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    public List<Long> getMemberIds() { return memberIds; }
    public void setMemberIds(List<Long> memberIds) { this.memberIds = memberIds; }
}