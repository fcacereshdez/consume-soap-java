import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

public class serviceClientWeb {
    public static void main(String[] args) {
        try {
            
            URL url = new URL("aqui el endpoint");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");

           
           
			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage soapMessage = messageFactory.createMessage();

			
			SOAPHeader soapHeader = soapMessage.getSOAPHeader();
			soapHeader.detachNode();

			
			SOAPBody soapBody = soapMessage.getSOAPBody();
			SOAPElement cashinRequest = soapBody.addChildElement("request name", "prefix", "schema soap");

			
			SOAPElement requestHeader = cashinRequest.addChildElement("RequestHeader", "v3", "service uri");
			SOAPElement generalConsumerInformation = requestHeader.addChildElement("GeneralConsumerInformation", "v3");
			generalConsumerInformation.addChildElement("consumerID", "v3").addTextNode("");
			generalConsumerInformation.addChildElement("transactionID", "v3").addTextNode("");
			generalConsumerInformation.addChildElement("country", "v3").addTextNode("");
			generalConsumerInformation.addChildElement("correlationID", "v3").addTextNode("");

			
			SOAPElement requestBody = cashinRequest.addChildElement("requestBody", "sch");
			requestBody.addChildElement("agentId", "sch").addTextNode("");
			requestBody.addChildElement("pin", "sch").addTextNode("");
			requestBody.addChildElement("msisdn", "sch").addTextNode("");
			requestBody.addChildElement("subscriberId", "sch").addTextNode("");
			requestBody.addChildElement("amount", "sch").addTextNode("");

			
			SOAPElement additionalParameters = requestBody.addChildElement("additionalParameters", "sch");
			SOAPElement parameterType = additionalParameters.addChildElement("ParameterType", "v2", "another parameters");
			parameterType.addChildElement("parameterName", "v2").addTextNode("?");
			parameterType.addChildElement("parameterValue", "v2").addTextNode("?");


            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            soapMessage.writeTo(outputStream);
            byte[] soapBytes = outputStream.toByteArray();

           
            OutputStream out = connection.getOutputStream();
            out.write(soapBytes);
            out.close();

         
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            System.out.println("Response: " + response.toString());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    
}
