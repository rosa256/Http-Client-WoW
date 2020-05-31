package com.api.wow.auction.analysis.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class AuthorizationData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "access_id")
    private String accessId;

    @Column(name = "access_secret")
    private String accessSecret;

}
