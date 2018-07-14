package com.kemal.spring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.kemal.spring.customAnnotations.ValidRoleName;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ValidRoleName
    @Column(unique = true)
    private String name;
    
    public Role(String name) {
    	this.name = name;
    }

   
}
