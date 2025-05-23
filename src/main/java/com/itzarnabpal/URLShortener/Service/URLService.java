package com.itzarnabpal.URLShortener.Service;

import com.itzarnabpal.URLShortener.Models.URLModel;
import com.itzarnabpal.URLShortener.Repositories.URLRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class URLService {

    private final URLRepository urlRepository;

    private String generateShortCode(){
        String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();

        return random.ints(6, 0, CHAR_SET.length())
                .mapToObj(CHAR_SET::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public URLModel createShortURL(String URL){
        String shortCode = generateShortCode();

        URLModel urlModel = new URLModel();
        urlModel.setUrl(URL);
        urlModel.setShortCode(shortCode);
        urlModel.setCreatedAt(new Date());
        urlModel.setUpdatedAt(new Date());

        return urlRepository.save(urlModel);
    }

    public URLModel updateShortURL(String shortCode, String URL){
        URLModel existingURLModel = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found for short code"));

        existingURLModel.setUrl(URL);
        existingURLModel.setUpdatedAt(new Date());
        return urlRepository.save(existingURLModel);
    }

    public URLModel getShortURL(String shortCode){
        URLModel existingURL = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL Not Found"));

        existingURL.setAccessCount(existingURL.getAccessCount() + 1);
        return existingURL;
    }

    public URLModel getStatsShortURL(String shortCode) {
        return urlRepository.findByShortCode(shortCode).orElseThrow(() -> new RuntimeException("URL not found"));
    }

    public void deleteShortURL(String shortCode) {
        URLModel existingUrl = urlRepository.findByShortCode(shortCode).orElseThrow(() -> new RuntimeException("URL not found"));
        urlRepository.delete(existingUrl);
    }
}
