package pl.szymanski.sharelibrary.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szymanski.sharelibrary.commanddata.AddExchangeRequest;
import pl.szymanski.sharelibrary.services.ports.ExchangeService;
import pl.szymanski.sharelibrary.views.ExchangeResponse;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exchanges")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping
    public ResponseEntity<ExchangeResponse> saveExchange(@RequestBody AddExchangeRequest addExchangeRequest) {
        return new ResponseEntity<>(
                exchangeService.saveExchange(addExchangeRequest),
                CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ExchangeResponse>> getExchanges(@PathVariable Long id) {
        return new ResponseEntity<>(
                exchangeService.getStartedExchanges(id),
                OK
        );
    }

    @PostMapping("/{id}/end")
    public ResponseEntity<Void> finishExchange(@PathVariable("id") Long exchangeId) {
        exchangeService.finishExchange(exchangeId);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
