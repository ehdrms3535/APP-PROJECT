package com.example.demo.service;

import com.example.demo.entity.Group;
import com.example.demo.entity.User;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupService {

    private final GroupRepository groupRepo;
    private final UserRepository userRepo;

    public GroupService(GroupRepository groupRepo, UserRepository userRepo) {
        this.groupRepo = groupRepo;
        this.userRepo   = userRepo;
    }

    /**
     * 그룹명으로 그룹을 찾아 Optional로 반환
     */
    public Optional<Group> findByName(String name) {
        return groupRepo.findByName(name);
    }

    /**
     * 그룹을 새로 만들고, 소유자(owner)와 memberIds에 해당하는 사용자들을 멤버로 추가
     */
    public Group createGroup(String name, Long ownerId, List<Long> memberIds) {
        User owner = userRepo.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다: " + ownerId));

        Group g = new Group(name, owner);
        // 추가 멤버 조회 & 추가 (owner는 생성자에서 이미 추가됨)
        List<User> members = memberIds.stream()
                .filter(id -> !id.equals(ownerId))
                .map(id -> userRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자: " + id)))
                .collect(Collectors.toList());

        for (User u : members) {
            g.addMember(u);
        }

        return groupRepo.save(g);
    }

    /**
     * 기존 그룹에 사용자를 가입시킨다.
     * (이미 가입된 경우에는 아무 동작도 하지 않도록 엔티티 메서드에서 처리)
     */
    public void addMember(Long groupId, Long userId) {
        Group g = groupRepo.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다: " + groupId));
        User u = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다: " + userId));

        g.addMember(u);
        groupRepo.save(g);
    }
}