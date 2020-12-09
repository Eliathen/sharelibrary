package pl.szymanski.sharelibrary.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szymanski.sharelibrary.requests.AddExchangeRequest;
import pl.szymanski.sharelibrary.requests.ExecuteExchangeRequest;
import pl.szymanski.sharelibrary.response.ExchangeResponse;
import pl.szymanski.sharelibrary.response.RequirementResponse;
import pl.szymanski.sharelibrary.services.ports.ExchangeService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exchanges")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping
    public ResponseEntity<ExchangeResponse> saveExchange(@RequestBody AddExchangeRequest addExchangeRequest) {
        return new ResponseEntity<>(
                ExchangeResponse.of(exchangeService.saveExchange(addExchangeRequest)),
                CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<ExchangeResponse>> getExchanges() {
        return new ResponseEntity<>(
                exchangeService.getStartedExchanges().stream().map(ExchangeResponse::of).collect(Collectors.toList()),
                OK
        );
    }

    @PostMapping("/{id}/end")
    public ResponseEntity<Void> finishExchange(@PathVariable("id") Long exchangeId) {
        exchangeService.finishExchange(exchangeId);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExchangeResponse> getExchange(@PathVariable("id") Long exchangeId) {
        return new ResponseEntity<>(
                ExchangeResponse.of(exchangeService.getExchangeById(exchangeId)), OK
        );
    }

    @PostMapping("/execution")
    public ResponseEntity<ExchangeResponse> executeExchange(@RequestBody ExecuteExchangeRequest executeExchangeRequest) {
        return new ResponseEntity<>(
                ExchangeResponse.of(exchangeService.executeExchange(executeExchangeRequest))
                , OK
        );
    }

    @GetMapping("/{id}/requirements")
    public ResponseEntity<List<RequirementResponse>> getRequirements(@PathVariable("id") Long exchangeId) {
        return new ResponseEntity<>(
                exchangeService.getRequirements(exchangeId).stream().map(RequirementResponse::of).collect(Collectors.toList()),
                OK
        );
    }

    @GetMapping("/withUser/{id}")
    public ResponseEntity<List<ExchangeResponse>> getExchangesAtUser(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(
                exchangeService.getExchangesWhereUserIdIsWithUser(userId).stream().map(ExchangeResponse::of).collect(Collectors.toList()), OK
        );
    }

}
