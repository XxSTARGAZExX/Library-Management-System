package com.lms.service;

import com.lms.entity.Member;
import com.lms.entity.User;
import com.lms.exception.BadRequestException;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.MemberRepository;
import com.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserRepository userRepository;

    public Member createMember(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (memberRepository.findByUserId(userId).isPresent()) {
            throw new BadRequestException("Member profile already exists for this user");
        }

        String membershipId = "MEM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Member member = Member.builder()
                .user(user)
                .membershipId(membershipId)
                .status(Member.MembershipStatus.ACTIVE)
                .maxBooks(5)
                .borrowedBooks(0)
                .totalFines(0.0)
                .build();

        return memberRepository.save(member);
    }

    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));
    }

    public Member getMemberByMembershipId(String membershipId) {
        return memberRepository.findByMembershipId(membershipId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with membership id: " + membershipId));
    }

    public Member getMemberByUserId(Long userId) {
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found for user id: " + userId));
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public List<Member> getAllActiveMembers() {
        return memberRepository.findByStatus(Member.MembershipStatus.ACTIVE);
    }

    public Member suspendMember(Long memberId) {
        Member member = getMemberById(memberId);
        member.setStatus(Member.MembershipStatus.SUSPENDED);
        return memberRepository.save(member);
    }

    public Member activateMember(Long memberId) {
        Member member = getMemberById(memberId);
        member.setStatus(Member.MembershipStatus.ACTIVE);
        return memberRepository.save(member);
    }

    public Member updateBorrowedBooksCount(Long memberId, Integer count) {
        Member member = getMemberById(memberId);
        member.setBorrowedBooks(member.getBorrowedBooks() + count);
        return memberRepository.save(member);
    }

    public long getTotalMembersCount() {
        return memberRepository.countByStatus(Member.MembershipStatus.ACTIVE);
    }
}
