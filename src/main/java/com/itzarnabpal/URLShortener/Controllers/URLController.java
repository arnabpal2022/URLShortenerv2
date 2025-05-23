package com.itzarnabpal.URLShortener.Controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.itzarnabpal.URLShortener.Models.URLModel;
import com.itzarnabpal.URLShortener.Service.URLService;
import com.itzarnabpal.URLShortener.Views.JSONView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/v1/shorten")
@RequiredArgsConstructor
public class URLController {
    private final URLService urlService;


    @PostMapping
    @JsonView(JSONView.Default.class)
    public ResponseEntity<?> createShortURL(@Valid @RequestBody URLModel urlModel){
        try{
            URLModel response = urlService.createShortURL(urlModel.getUrl());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{shortCode}")
    @JsonView(JSONView.Default.class)
    public ResponseEntity<?> retrieveOriginalURL(@PathVariable(value = "shortCode") String shortCode){
        try{
            URLModel response = urlService.getShortURL(shortCode);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{shortCode}")
    @JsonView(JSONView.Default.class)
    public ResponseEntity<?> updateShortURL(@PathVariable(value = "shortCode") String shortCode, @Valid @RequestBody URLModel url){
        try {
            URLModel urlDB = urlService.updateShortURL(shortCode, url.getUrl());
            return new ResponseEntity<>(urlDB, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<?> deleteShortURL(@PathVariable(value = "shortCode") String shortCode){
        try {
            urlService.deleteShortURL(shortCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{shortCode}/stats")
    @JsonView(JSONView.Stats.class)
    public ResponseEntity<?> getShortUrlStats(@PathVariable(value = "shortCode") String shortCode){
        try {
            URLModel urlDB = urlService.getStatsShortURL(shortCode);
            return new ResponseEntity<>(urlDB, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/redirect/{shortCode}")
    public RedirectView redirectToOriginalUrl(@PathVariable(value = "shortCode") String shortCode){
        URLModel urlDB = urlService.getShortURL(shortCode);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(urlDB.getUrl());
        return redirectView;
    }


}
