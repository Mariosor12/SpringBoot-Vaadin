package com.app.games.controller;  

import com.app.games.model.Game;  
import com.app.games.service.GameService;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.http.ResponseEntity;  
import org.springframework.web.bind.annotation.*;  

import java.util.List;  

@RestController  
@RequestMapping("/api/games")  
public class GameController {  

    @Autowired  
    private GameService gameService;  

    @GetMapping  
    public List<Game> getAllGames() {  
        return gameService.getAllGames();  
    }  

    @GetMapping("/{id}")  
    public ResponseEntity<Game> getGameById(@PathVariable String id) {  
        return gameService.getGameById(id)  
                .map(ResponseEntity::ok)  
                .orElse(ResponseEntity.notFound().build());  
    } 

    @PostMapping  
    public Game createGame(@RequestBody Game game) {  
        return gameService.createGame(game, "", "", "");  
    }  

    @PutMapping("/{id}")  
    public ResponseEntity<Game> updateGame(@PathVariable String id, @RequestBody Game gameDetails) {  // Cambiar Long a String  
        Game updatedGame = gameService.updateGame(id, gameDetails);  
        return ResponseEntity.ok(updatedGame);  
    }  

    @DeleteMapping("/{id}")  
    public ResponseEntity<Void> deleteGame(@PathVariable String id) {  // Cambiar Long a String  
        gameService.deleteGame(id, "");  
        return ResponseEntity.noContent().build();  
    }  
}