package com.ugam.demo.core.models;
import com.ugam.demo.core.beans.HttpService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

@Model(adaptables = Resource.class,
adapters = AboutUsModel.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AboutUsModelImpl implements AboutUsModel{

	final Logger log = LoggerFactory.getLogger(AboutUsModelImpl.class);

	@Inject
	String pageNo;

	@Override
	public List<Map<String, String>> getData() throws JSONException, IOException {

		String response = HttpService.readJson(getUrl());
		JSONObject jsonObject =  new JSONObject(response);
		log.info(String.valueOf(jsonObject));
		JSONArray jsonArray1 = jsonObject.getJSONArray("data");
		log.info("Array {}"+jsonArray1);
		log.info("Length {}"+jsonArray1.length());
		List<Map<String, String>> userList = new ArrayList<>();
		for (int i=0;i<jsonArray1.length();i++){
			Map<String,String> user =new HashMap<>();
			user.put("fname",jsonArray1.getJSONObject(i).getString("first_name"));
			user.put("lname",jsonArray1.getJSONObject(i).getString("last_name"));
			user.put("email",jsonArray1.getJSONObject(i).getString("email"));
			user.put("avatar",jsonArray1.getJSONObject(i).getString("avatar"));
			userList.add(user);
		}
		log.info("===list==="+userList);
		return userList;
	}

	@Override
	public String getUrl() {
		return "https://reqres.in/api/users?page="+pageNo;
	}

}
