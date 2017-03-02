package com.kc.intelliment.rest;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc
@RequestMapping("/counter-api")
public class ApiSearchController {
	private final static Logger logger = Logger.getLogger(ApiSearchController.class);
    
	@Autowired
	String passageText;
	
    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String getRequestedWordCount(HttpEntity<String> httpEntity) {
        String reqParamString 	= httpEntity.getBody();
        JSONObject reqParamJson = new JSONObject(reqParamString);
        
        JSONObject resultJson 	= new JSONObject();
        resultJson.put("counts", new JSONArray());
        
        JSONArray searchTextArr = (JSONArray) reqParamJson.get("searchText");
        
        for (int i = 0; i < searchTextArr.length(); i++) {
        	int j=0;
        	String s = (String)searchTextArr.get(i);
        	Pattern p = Pattern.compile(s, Pattern.UNICODE_CASE);
        	Matcher m = p.matcher(passageText);
        	while (m.find()) {
        	    j++;
        	}
        	((JSONArray)resultJson.get("counts")).put(new JSONObject().put(s, j));
        }
        
        logger.debug("KC001" + " - " + resultJson.toString());
        return resultJson.toString();
    }

    @RequestMapping(value = "/top/{limit}", method = RequestMethod.GET, produces = "text/csv")
    public @ResponseBody String getTopWords(@PathVariable String limit) {
    	String result = "";
       	String[] wordsArr = passageText.split("\\W+");
    	
    	Stream<String> stream = Stream.of(wordsArr); 
    	Map<String, Integer> wordCountMap = stream.collect(Collectors.groupingBy(String::toLowerCase, Collectors.summingInt(s -> 1)));
    	
    	LinkedHashMap<String, Integer> wordCountSortedMap = wordCountMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(Long.valueOf(limit).longValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> {
                            throw new IllegalStateException();
                        },
                        LinkedHashMap::new
                ));
    	
    	for (Map.Entry<String, Integer> entry : wordCountSortedMap.entrySet())
    	{
    		result += entry.getKey() + "|" + entry.getValue() + "\n";
    	}
    	logger.debug("KC002");
    	return result;
    }
}