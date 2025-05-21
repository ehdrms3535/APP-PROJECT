package com.example.demo.controller;

import com.example.demo.dto.CreateGroupRequest;
import com.example.demo.dto.JoinRequest;
import com.example.demo.entity.Group;
import com.example.demo.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    // 그룹명으로 조회
    @GetMapping("/name/{name}")
    public ResponseEntity<Group> getByName(@PathVariable String name) {
        Group g = groupService.findByName(name)
                .orElseThrow(() -> new RuntimeException("그룹이 없습니다."));
        return ResponseEntity.ok(g);
    }

    // 그룹 생성
    @PostMapping
    public ResponseEntity<Map<String, Object>> createGroup(@RequestBody CreateGroupRequest req) {
        Group g = groupService.createGroup(req.getName(), req.getOwnerId(), req.getMemberIds());
        return ResponseEntity.ok(Map.of("groupId", g.getId()));
    }

    // 그룹 가입
    @PostMapping("/{id}/join")
    public ResponseEntity<Void> joinGroup(
            @PathVariable Long id,
            @RequestBody JoinRequest req
    ) {
        groupService.addMember(id, req.getUserId());
        return ResponseEntity.ok().build();
    }
}
