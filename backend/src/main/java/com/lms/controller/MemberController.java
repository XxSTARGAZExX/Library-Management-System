package com.lms.controller;

import com.lms.dto.ApiResponse;
import com.lms.entity.Member;
import com.lms.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> createMember(@PathVariable Long userId) {
        try {
            Member member = memberService.createMember(userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Member created successfully", member));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<?> getMemberById(@PathVariable Long memberId) {
        try {
            Member member = memberService.getMemberById(memberId);
            return ResponseEntity.ok(ApiResponse.success("Member fetched successfully", member));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/by-membership/{membershipId}")
    public ResponseEntity<?> getMemberByMembershipId(@PathVariable String membershipId) {
        try {
            Member member = memberService.getMemberByMembershipId(membershipId);
            return ResponseEntity.ok(ApiResponse.success("Member fetched successfully", member));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<?> getMemberByUserId(@PathVariable Long userId) {
        try {
            Member member = memberService.getMemberByUserId(userId);
            return ResponseEntity.ok(ApiResponse.success("Member fetched successfully", member));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllMembers() {
        try {
            List<Member> members = memberService.getAllActiveMembers();
            return ResponseEntity.ok(ApiResponse.success("Members fetched successfully", members));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{memberId}/suspend")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> suspendMember(@PathVariable Long memberId) {
        try {
            Member member = memberService.suspendMember(memberId);
            return ResponseEntity.ok(ApiResponse.success("Member suspended successfully", member));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{memberId}/activate")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<?> activateMember(@PathVariable Long memberId) {
        try {
            Member member = memberService.activateMember(memberId);
            return ResponseEntity.ok(ApiResponse.success("Member activated successfully", member));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }
}
