package com.javarush.khmelov.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"quests", "games"})
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = User.GET_ALL, query = "select u from User u"),
        @NamedQuery(name = User.GET_BETWEEN, query = "select u from User u where u.id between :minId and :maxId"),
        @NamedQuery(name = User.GET_BY_ID, query = "select u from User u where u.id=:id order by u.id"),
})
public class User implements AbstractEntity {

    public static final String GET_ALL = "user.getAll";
    public static final String GET_BETWEEN = "user.getBetween";
    public static final String GET_BY_ID = "user.getById";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Transient
    private final Collection<Quest> quests = new ArrayList<>();

    @Transient
    private final Collection<Game> games = new ArrayList<>();

    public String getImage() { //TODO move to DTO
        return "user-" + id;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || !this.getClass().equals(Hibernate.getClass(o))) return false;

        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
