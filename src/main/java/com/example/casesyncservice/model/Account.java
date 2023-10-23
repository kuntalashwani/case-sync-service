package com.example.casesyncservice.model;

import lombok.Data;

@Data
public class Account {
    private String id;
    private String displayId;
    private String name;
    private boolean isDeleted;
    private String partyRoleCategory;
    private String partyRole;
    private String partyRoleDescription;
    private String addressId;
    private int determinationMethodCode;
    private boolean isMain;
    private String partyType;
}
