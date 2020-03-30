package com.icf.webhookservices.services;

import java.time.LocalDateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.icf.webhookservices.utilities.SoapClient;

@Service
public class WebHookPocServiceImpl implements WebHookPocService {
    private static final Logger logger = LoggerFactory.getLogger(WebHookPocService.class);
    private static final String ANALYTICS_TRIGGER_DATA_CAPTURED = "Analytics Trigger Data Captured";
    private static final String SAMPLE_EVENT_TRIGGERED = "Sample Event Triggered";

    public WebHookPocServiceImpl() {
    }

    public JSONObject getWebHookData(String body) {
        JSONObject triggerResponse = new JSONObject();
        body = body.replaceAll("com.adobe.mcloud.pipeline.pipelineMessage", "comAdobemCloudPipelinePipelineMessage");
        body = body.replaceAll("com.adobe.mcloud.protocol.trigger", "comAdobeMcloudProtocolTrigger");
        JSONObject trigJsonObjectData = null;

        try {
            trigJsonObjectData = new JSONObject(body);
            triggerResponse.put("eventId", trigJsonObjectData.get("event_id"));
            JSONObject eventJsonObjectData = new JSONObject(trigJsonObjectData.get("event").toString());

            try {
                eventJsonObjectData = (JSONObject)eventJsonObjectData.get("event");
                logger.info(SAMPLE_EVENT_TRIGGERED);
            } catch (JSONException var6) {
                logger.warn(var6.getMessage());
            }

            eventJsonObjectData = (JSONObject)eventJsonObjectData.get("comAdobemCloudPipelinePipelineMessage");
            eventJsonObjectData = (JSONObject)eventJsonObjectData.get("comAdobeMcloudProtocolTrigger");
            triggerResponse.put("visitorId", eventJsonObjectData.get("mcId"));
            triggerResponse.put("triggerId", eventJsonObjectData.get("triggerId"));
            logger.info(ANALYTICS_TRIGGER_DATA_CAPTURED);
            logger.info(String.valueOf(triggerResponse));
            new SoapClient(triggerResponse.get("eventId").toString(), triggerResponse.get("triggerId").toString(), triggerResponse.get("visitorId").toString());
        } catch (JSONException var7) {
            logger.error(LocalDateTime.now() + ":" + var7.getMessage());
        }

        return triggerResponse;
    }
}
