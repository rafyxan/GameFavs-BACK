package com.rafa.gamefavs_back.service;


import com.rafa.gamefavs_back.model.UserVideogame;
import com.rafa.gamefavs_back.model.Videogame;
import com.rafa.gamefavs_back.repository.StatusRepository;
import com.rafa.gamefavs_back.repository.UserRepository;
import com.rafa.gamefavs_back.repository.UserVideogameRepository;
import com.rafa.gamefavs_back.repository.VideogameRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class UserVideogameService {
    private static final Logger logger = LoggerFactory.getLogger(UserVideogameService.class);
    @Autowired
    private UserVideogameRepository userVideogameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideogameRepository videogameRepository;

    @Autowired
    private StatusRepository statusRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // Metodo para añadir un UserVideogame mediante el id de usuario, id de videojuego, id de estado y el objeto Videogame
    @Transactional
    public UserVideogame addUserVideogame(int user_id, int videogame_id, int status_id, Videogame videogameAdd) {
        logger.info("Adding UserVideogame: user_id={}, videogame_id={}, status_id={}", user_id, videogame_id, status_id);

        // Verifica si el videojuego existe, si no, lo guarda
        if (!videogameRepository.existsById((long) videogame_id)) {
            logger.info("Videogame with ID {} does not exist. Saving to database.", videogame_id);
            videogameRepository.save(videogameAdd);
        }

        // Verifica si el usuario existe
        if (!userRepository.existsById((long) user_id)) {
            throw new RuntimeException("User not found with ID " + user_id);
        }

        // Verifica si el estado existe
        if (!statusRepository.existsById((long) status_id)) {
            throw new RuntimeException("Status not found with ID " + status_id);
        }

        // Inserta manualmente el registro en la tabla user_videogames
        String sql = "INSERT INTO user_videogames (user_id, videogame_id, status_id) VALUES (:user_id, :videogame_id, :status_id)";
        entityManager.createNativeQuery(sql)
                .setParameter("user_id", user_id)
                .setParameter("videogame_id", videogame_id)
                .setParameter("status_id", status_id)
                .executeUpdate();

        logger.info("UserVideogame added successfully: user_id={}, videogame_id={}, status_id={}", user_id, videogame_id, status_id);

        // Retorna el objeto UserVideogame creado
        UserVideogame userVideogame = new UserVideogame();
        userVideogame.setUser_id(user_id);
        userVideogame.setVideogame_id(videogame_id);
        userVideogame.setStatus_id(status_id);
        return userVideogame;
    }

    // Metodo para eliminar un UserVideogame mediante el id de usuario, id de videojuego y id de estado
    @Transactional
    public void deleteUserVideogame(Long userId, Long videogameId, Long statusId) {
        logger.info("Deleting UserVideogame: userId={}, videogameId={}, statusId={}", userId, videogameId, statusId);
        userVideogameRepository.deleteByUserIdAndVideogameIdAndStatusId(userId, videogameId, statusId);
        logger.info("UserVideogame deleted successfully: userId={}, videogameId={}, statusId={}", userId, videogameId, statusId);
    }

    public List<UserVideogame> getUserVideogamesByStatus(Long userId, String statusName) {
        return userVideogameRepository.findByUserIdAndStatusName(userId, statusName);
    }

}
