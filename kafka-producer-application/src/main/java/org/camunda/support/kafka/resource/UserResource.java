package org.camunda.support.kafka.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
public class UserResource {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "alex-topic";

    @GetMapping("/publish/{name}")
    public String post(@PathVariable("name") final String name) {
        SendResult<String, String> result = null;
        try {
        	
        	StringBuffer sb = new StringBuffer();
        	sb.append('{');
        	sb.append('"');
        	sb.append("mykey");
        	sb.append('"');
        	sb.append(':');
        	sb.append('"');
        	sb.append(name);
        	sb.append('"');
        	sb.append('}');
        		
            result = kafkaTemplate.send(TOPIC, sb.toString()).get();

        }
        catch (Exception e){
            e.printStackTrace();
        }

        String returnResult = "";
        if (result == null){
           throw new RuntimeException("Could not send Message");
        }
        else {
            returnResult = result.getProducerRecord().toString();
        }

        return returnResult;
    }
    
    @GetMapping("/publishmulti/{name}")
    public String postMulti(@PathVariable("name") final String name) {
        SendResult<String, String> result = null;
        try {
        	
        	for (int k=0; k<10000; k++) {
        		
        		String s = "{\"mykey\":\"myvalue-" +k+  "\"}"; 
        		
        		result = kafkaTemplate.send(TOPIC, s).get();
        		System.out.println( result.getProducerRecord().toString());
        	}

        }
        catch (Exception e){
            e.printStackTrace();
        }


        return "done";
    }
    
    
    @GetMapping("/publishmultitopic/{name}")
    public String postMultitopic(@PathVariable("name") final String name) {
        SendResult<String, String> result = null;
        try {
        	
        	for (int k=0; k<5000; k++) {
        		
        		String s = "{\"mykey\":\"myvalue-" +k+  "\"}"; 
        		
        		result = kafkaTemplate.send(name, s).get();
        		System.out.println( result.getProducerRecord().toString());
        	}

        }
        catch (Exception e){
            e.printStackTrace();
        }


        return "done";
    }
}
