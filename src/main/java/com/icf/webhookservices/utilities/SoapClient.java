package com.icf.webhookservices.utilities;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoapClient {
    private static final Logger logger = LoggerFactory.getLogger(SoapClient.class);
    public static final String CAMPAIGNPASSWORD = "test";
    public static final String CAMPAIGNUSER = "test";

    public SoapClient(String eventId, String triggerId, String visitorId) {
        try {
            String soapEnvelope = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:xtk:session\">\n   <soapenv:Header/>\n   <soapenv:Body>\n      <urn:Write>\n         <urn:sessiontoken>test/test</urn:sessiontoken>\n         <urn:domDoc>\n            <webhookData xtkschema=\"icf:webhookData\" eventId=\"" + eventId + "\" triggerId=\"" + triggerId + "\" visitorId=\"" + visitorId + "\" _key=\"@visitorId\" _operation=\"insertOrUpdate\"></webhookData>\n         </urn:domDoc>\n      </urn:Write>\n   </soapenv:Body>\n</soapenv:Envelope>";
            String url = "http://adobecampaignlocal.com/nl/jsp/soaprouter.jsp";
            URL obj = new URL(url);
            HttpURLConnection soapConnection = (HttpURLConnection)obj.openConnection();
            soapConnection.setDoOutput(true);
            logger.info("Connection Set to Campaign");
            soapConnection.setRequestMethod("POST");
            soapConnection.setRequestProperty("SOAPAction", "xtk:queryDef#ExecuteQuery");
            soapConnection.addRequestProperty("Content-Type", "text/xml; charset=\"utf-8\"");
            OutputStream reqStream = soapConnection.getOutputStream();
            reqStream.write(soapEnvelope.getBytes());
            logger.info("Soap Request Triggered");
            InputStream resStream = soapConnection.getInputStream();
            byte[] byteBuf = new byte[10240];
            resStream.read(byteBuf);
            logger.info("Data Inserted/Updated Successfully");
        } catch (Exception var12) {
            logger.error(LocalDateTime.now() + ": " + var12.getMessage());
        }

    }
}
