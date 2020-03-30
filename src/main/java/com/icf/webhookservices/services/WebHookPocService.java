package com.icf.webhookservices.services;

import org.json.JSONObject;

public interface WebHookPocService {
    JSONObject getWebHookData(String body);
}
