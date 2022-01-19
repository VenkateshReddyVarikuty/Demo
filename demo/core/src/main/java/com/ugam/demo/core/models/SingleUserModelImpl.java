package com.ugam.demo.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ugam.demo.core.beans.HttpService;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Iterator;

@Model(adaptables = Resource.class,
adapters = SingleUserModel.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SingleUserModelImpl implements SingleUserModel{
	final Logger log = LoggerFactory.getLogger(SingleUserModelImpl.class);

	@Inject
	String id;

	String fname;
	String lname;
	String email;
	String avatar;

	@Override
	public String getUrl(){
		return "https://reqres.in/api/users/"+id;
	}

	@Override
	public String getMessage() throws IOException, JSONException {

		String response = HttpService.readJson(getUrl());
		JSONObject jsonObject =  new JSONObject(response);
		Iterator x = jsonObject.keys();
		JSONArray jsonArray = new JSONArray();

		while (x.hasNext()){
			String key = (String) x.next();
			jsonArray.put(jsonObject.get(key));
		}
		email = jsonArray.getJSONObject(0).getString("email");
		fname=jsonArray.getJSONObject(0).getString("first_name");
		lname=jsonArray.getJSONObject(0).getString("last_name");
		avatar=jsonArray.getJSONObject(0).getString("avatar");

		return response;
	}

	@Override
	public String getFname() {
		return fname;
	}

	@Override
	public String getLname() {
		return lname;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public String getAvatar() {
		return avatar;
	}

}



