package com.api.wow.auction.analysis.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "access_token_data")
@Getter
@Setter
public class AccessTokenData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "last_modified")
    private Timestamp lastModified;
}
