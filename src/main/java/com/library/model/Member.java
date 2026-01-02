package com.library.model;

import com.library.enums.MemberType;
import java.io.Serializable;
import java.time.LocalDate;

public abstract class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String memberId;
    protected String name;
    protected String email;
    protected MemberType type;
    protected LocalDate joinDate;

    public Member(String memberId, String name, String email, MemberType type) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.type = type;
        this.joinDate = LocalDate.now();
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public MemberType getType() { return type; }

    @Override
    public String toString() {
        return String.format("%s | %s | %s", memberId, name, type);
    }
}