package com.aks.study.ehcache.entity;


import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
public class Person implements Serializable {
    private String id;
    private String name;

    public static Person create(String id, String name) {
        return new Person(id, name);
    }

    public static Person empty() {
        return new Person();
    }

    public void updateName(String newName) {
        this.name = newName;
    }
}
