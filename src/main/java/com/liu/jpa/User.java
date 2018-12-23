package com.liu.jpa;

import lombok.*;

import javax.persistence.*;

/**
 * @author liu
 * @date 2018/12/23 15:38
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class User {

    @Id
    @Column(name = "user_id", length = 36)
    private String id;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String password;

    @Column(length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Role role;
}
