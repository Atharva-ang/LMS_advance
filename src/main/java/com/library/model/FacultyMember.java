package com.library.model;
import com.library.enums.MemberType;

public class FacultyMember extends Member {
    public FacultyMember(String id, String name, String email) {
        super(id, name, email, MemberType.FACULTY);
    }
}