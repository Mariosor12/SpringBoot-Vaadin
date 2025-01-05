package com.app.games.service;  

import com.app.games.model.Game;
import com.app.games.model.ListReview;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.web.client.RestTemplate;  
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.ArrayList;
import java.util.Arrays;  
import java.util.List;
import java.util.Optional;  

@Service  
public class GameService {  

    private final String BASE_URL = "https://games-app-az0f.onrender.com/games";
    private final String USERS_BASE_URL = "https://games-app-az0f.onrender.com/list";
    private final RestTemplate restTemplate; 
    private final ListService listService; 

    @Autowired  
    public GameService(RestTemplateBuilder restTemplateBuilder, ListService listService) {  
        this.restTemplate = restTemplateBuilder.build();
        this.listService = listService;  
    }  

    public List<Game> getAllGames() {  
        Game[] games = restTemplate.getForObject(BASE_URL, Game[].class);  
        return Arrays.asList(games);  
    }  

    public Optional<Game> getGameById(String id) {  
        Game game = restTemplate.getForObject(BASE_URL + "/" + id, Game.class);  
        return Optional.ofNullable(game);  
    }  

    public Game findGameById(String id) {  
        Game game = restTemplate.getForObject(BASE_URL + "/" + id, Game.class);  
        return game;  
    }  

    public List<Game> findGameByUserId(String id) {  
        ListReview[] list = restTemplate.getForObject(USERS_BASE_URL + "?userId=" + id, ListReview[].class);
        List<Game> games = new ArrayList<>();
        for (ListReview review : list) {  
            Game user = restTemplate.getForObject(BASE_URL + "/" + review.getGameId(), Game.class);
            games.add(user);
        }    
        return games;  
    }  

    public Game createGame(Game game, String id, String puntuacion, String comentario) {
        List<Game> games = getAllGames();
        game.setId(String.valueOf(games.size() + 1));
        getAllGames();
        List<ListReview> listCount = listService.getAllReviews();
        ListReview list = new ListReview();
        list.setId(String.valueOf(listCount.size() + 1));
        list.setGameId(games.size() + 1);
        list.setUserId(Integer.parseInt(id));
        list.setComentario("");
        list.setPuntuacion(Integer.parseInt(puntuacion));
        list.setFecha(game.getFechaSalida());
        list.setComentario(comentario);
        listService.createReview(list);
        listService.getAllReviews();
        return restTemplate.postForObject(BASE_URL, game, Game.class);  
    }  

    public Game updateGame(String id, Game gameDetails) {  
        Game existingGame = restTemplate.getForObject(BASE_URL + "/" + id, Game.class);
        if (existingGame != null) {   
            // Actualizar los campos del juego existente  
            existingGame.setNombre(gameDetails.getNombre());  
            existingGame.setTipo(gameDetails.getTipo());  
            existingGame.setEmpresaDesarrolladora(gameDetails.getEmpresaDesarrolladora());  
            existingGame.setPlataformas(gameDetails.getPlataformas());  
            existingGame.setCantidadJugadoresLocal(gameDetails.getCantidadJugadoresLocal());  
            existingGame.setFechaSalida(gameDetails.getFechaSalida());  
            existingGame.setCoOpLocal(gameDetails.isCoOpLocal());  
            existingGame.setCoOpOnline(gameDetails.isCoOpOnline());  
  
            restTemplate.put(BASE_URL + "/" + id, existingGame);  
        }  

        getAllGames();
        
        return existingGame; 
    }  

    public void deleteGame(String id, String userId) {    
        restTemplate.delete(BASE_URL + "/" + id);
        deleteReview(id, userId);
    }  

    public void deleteReview(String id, String userId) { 
        Integer gameIdInt = Integer.parseInt(id);  
        Integer userIdInt = Integer.parseInt(userId);
        String url = USERS_BASE_URL + "?userId=" + userIdInt + "&gameId=" + gameIdInt;
        ListReview[] reviews = restTemplate.getForObject(url, ListReview[].class);
        for (ListReview review : reviews) {  
            restTemplate.delete(USERS_BASE_URL + "/" + review.getId());    
        } 
    }  
}