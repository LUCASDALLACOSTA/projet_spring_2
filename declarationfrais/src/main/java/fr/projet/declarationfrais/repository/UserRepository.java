package fr.projet.declarationfrais.repository;

import fr.projet.declarationfrais.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public class UserRepository extends JpaRepository<User, Long>{
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);

    public void save(User user) {
    }

    public List<User> findAll() {
        return null;
    }
}
