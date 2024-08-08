package com.example.marsamaroc.dao.repositories;

import com.example.marsamaroc.dao.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test") // Assurez-vous que vous avez un profil de test configuré
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByLogin() {
        // Créez un utilisateur pour le test
        User user = new User();
        user.setLogin("meriem");
        user.setPassword("meriem"); // Assurez-vous que tous les champs requis sont définis
        userRepository.save(user);

        // Testez la méthode findByLogin
        Optional<User> foundUser = userRepository.findByLogin("meriem");
        System.out.println("Utilisateur trouvé : " + foundUser);

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals("meriem", foundUser.get().getLogin());
    }
}