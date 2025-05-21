package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.*;
import java.util.stream.Collectors;


@Entity
@Table(name = "`user`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private int groupid;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    @ManyToMany
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )

    private Set<Group> groups = new HashSet<>();

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getGroupid() { return groupid; }

    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setGroupid(int groupid) { this.groupid = groupid; }
    public Set<Group> getGroups() {
        return groups;
    }

    /**
     * 그룹 목록 설정
     */
    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    /**
     * 그룹 추가 (양방향 연관관계 유지)
     */
    public void addGroup(Group group) {
        groups.add(group);
        group.getMembers().add(this);
    }

    /**
     * 그룹 제거 (양방향 연관관계 유지)
     */
    public void removeGroup(Group group) {
        groups.remove(group);
        group.getMembers().remove(this);
    }

    /**
     * 그룹 이름 목록 조회 (편의 메서드)
     */
    public Set<String> getGroupNames() {
        return groups.stream()
                .map(Group::getName)
                .collect(Collectors.toSet());
    }
}