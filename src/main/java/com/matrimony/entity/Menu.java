package com.matrimony.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.matrimony.service.serviceImpl.MenuServiceImpl;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "menu_entity")
@EntityListeners(MenuServiceImpl.class)
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String slug;
    private String url;
    private Integer order_number;
    private Integer position_type;
    private Integer status;
    private String icon_path;

    //  @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ", timezone = "UTC")
    private Timestamp createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ", timezone = "UTC")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Menu parent;

//    below code will the child when its parent is deleted
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Menu> menus= new HashSet<>();

//    below two blocks allow to save parent id but doesn't show json when data is retived from data base,
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Ignore during deserialization (when receiving JSON data)
    public Menu getParent() {
        return parent;
    }
    public void setParent(Menu parent_id) {
        this.parent = parent_id;
    }

    @PrePersist
    public void prePersist() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        createdAt = now;
        updatedAt = now;
    }

}
