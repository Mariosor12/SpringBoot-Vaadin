package com.app.games.service;  

import com.app.games.model.CustomListItem;
import com.app.games.model.Database;
import com.app.games.model.Game;  
import com.app.games.model.ListReview;  
import com.fasterxml.jackson.core.type.TypeReference;  
import com.fasterxml.jackson.databind.ObjectMapper;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;  
import java.util.List;  
import java.util.Optional;  

@Service  
public class GameService {  

    private final String dbFilePath = "db.json"; // Ruta a tu archivo db.json  
    private List<Game> games = new ArrayList<>();  
    private final ListService listService;   

    @Autowired  
    public GameService(ListService listService) {  
        this.listService = listService;  
        loadGamesFromJsonFile();  
    }  

    private void loadGamesFromJsonFile() {  
        try {  
            ObjectMapper objectMapper = new ObjectMapper();  
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(dbFilePath); // Carga desde classpath  
            if (inputStream == null) {  
                throw new FileNotFoundException("No se encontró el archivo db.json en el classpath.");  
            }  
            Database db = objectMapper.readValue(inputStream, Database.class);  
            games = db.getGames(); // Asegúrate de que la clase Database esté definida correctamente  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    } 

    public List<Game> getAllGames() {  
        return games;  
    }  

    public Optional<Game> getGameById(String id) {  
        return games.stream().filter(game -> game.getId().equals(id)).findFirst();  
    }  

    public Game findGameById(String id) {  
        return getGameById(id).orElse(null);  
    }  

    public List<Game> findGameByUserId(String userId) {  
        List<CustomListItem> reviews = listService.findReviewsByUserId(userId);
        List<Game> userGames = new ArrayList<>();  
        for (CustomListItem review : reviews) {  
            Game userGame = findGameById(review.getGameId().toString()); 
            userGames.add(userGame);
        }    
        return userGames;  
    } 

    public Game createGame(Game game, String userId, String puntuacion, String comentario) {  
        game.setId(String.valueOf(games.size() + 1)); // Asignar un nuevo ID  
        games.add(game); // Añadir el nuevo juego a la lista  

        // Guardar el juego en el archivo JSON  
        saveGamesToJsonFile();  

        CustomListItem list = new CustomListItem();  
        list.setId(String.valueOf(listService.getAllReviews().size() + 1));  
        list.setGameId(games.size()); // usar el tamaño actualizado  
        list.setUserId(Integer.parseInt(userId));  
        list.setComentario(comentario);  
        list.setPuntuacion(Integer.parseInt(puntuacion));  
        list.setFecha(game.getFechaSalida());  

        listService.createReview(list); // Asegúrate de que este método esté implementado  
        return game;  
    }  

    public Game updateGame(String id, Game gameDetails) {  
        Optional<Game> existingGameOpt = getGameById(id);  
        if (existingGameOpt.isPresent()) {  
            Game existingGame = existingGameOpt.get();  
            // Actualiza los campos del juego existente  
            existingGame.setNombre(gameDetails.getNombre());  
            existingGame.setTipo(gameDetails.getTipo());  
            existingGame.setEmpresaDesarrolladora(gameDetails.getEmpresaDesarrolladora());  
            existingGame.setPlataformas(gameDetails.getPlataformas());  
            existingGame.setCantidadJugadoresLocal(gameDetails.getCantidadJugadoresLocal());  
            existingGame.setFechaSalida(gameDetails.getFechaSalida());  
            existingGame.setCoOpLocal(gameDetails.isCoOpLocal());  
            existingGame.setCoOpOnline(gameDetails.isCoOpOnline());  

            // Guardar los cambios en el archivo JSON  
            saveGamesToJsonFile();  
            return existingGame;   
        }  
        return null;   
    }  

    public void deleteGame(String id, String userId) {    
        games.removeIf(game -> game.getId().equals(id));  
        // Guardar cambios en el archivo JSON  
        saveGamesToJsonFile();  
        deleteReview(id, userId);   
    }  

    public void deleteReview(String id, String userId) {   
        listService.deleteReviewsByGameId(id, userId); // Asegúrate de que este método esté implementado  
    }  

    private void saveGamesToJsonFile() {  
        try {  
            ObjectMapper objectMapper = new ObjectMapper();  
            Database db = new Database();  
            db.setGames(games); // Asegúrate de que la clase Database tenga un método setGames  
            objectMapper.writeValue(new File(dbFilePath), db);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}