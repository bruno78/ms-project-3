package com.example.usersapi.models;


import lombok.*;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "FAVORITES")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LOCATION_NAME")
    private String location_name;

    @Column(name = "BORO_NAME")
    private String boro_name;

    @Column(name = "TYPE_NAME")
    private String type_name;

    @Column(name="PROVIDER")
    private String provider;

    @Column(name="SSID")
    private String ssid;

    @Column(name="REMARKS")
    private String remarks;

    @Column(name="NOTES")
    private String notes;

    @Column(name="USER_ID")
    private String user_id;

    public Favorite(String location, String boroName, String typeName,
                    String provider, String ssid, String remarks, String notes, String userId) {
        this.location_name = location;
        this.boro_name = boroName;
        this.type_name = typeName;
        this.provider = provider;
        this.ssid = ssid;
        this.remarks = remarks;
        this.notes = notes;
        this.user_id = userId;
    }

}
