package com.api.wow.auction.analysis.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class AccessTokenData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "last_modified")
    private String lastModified;
}
