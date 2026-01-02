package com.library.model;
import com.library.enums.MemberType;

public class StudentMember extends Member {
    public StudentMember(String id, String name, String email) {
        super(id, name, email, MemberType.STUDENT);
    }
}