package com.icf.webhookservices.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icf.webhookservices.services.WebHookPocServiceImpl;

@RestController
@RequestMapping({"/analyticsToCampaignIntegration/integartions"})
public class WebHookPocController {
    WebHookPocServiceImpl webhookPocService;

    public WebHookPocController(WebHookPocServiceImpl webhookPocService) {
        this.webhookPocService = webhookPocService;
    }

    @GetMapping({"/webhook"})
    public String webHookDataTest(@RequestParam(name = "challenge") String query) {
        return query;
    }

    @PostMapping({"/webhook"})
    public String webHookPocDataReader(@RequestBody String body) {
        return String.valueOf(this.webhookPocService.getWebHookData(body));
    }
}
