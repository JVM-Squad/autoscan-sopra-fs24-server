package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GameGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.service.UserService;
import ch.uzh.ifi.hase.soprafs24.service.WebSocketService;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;

import java.util.UUID;


@Controller
public class GameStompController {
    private final GameService gameService;
    private final UserService userService;
    private final WebSocketService webSocketService;

    GameStompController(GameService gameService, UserService userService, WebSocketService webSocketService) {
        this.gameService = gameService;
        this.userService = userService;
        this.webSocketService = webSocketService;
    }

    @MessageMapping("/games/{gameId}/joined")
    public void getLobbyInformation(@DestinationVariable("gameId") UUID gameId){
        Game game = gameService.getGame(gameId);
        GameGetDTO gameGetDTO = DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);
        webSocketService.sendMessageToSubscribers("/topic/games/" + gameId, gameGetDTO);
    }
}
