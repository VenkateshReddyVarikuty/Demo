package com.ugam.demo.core.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

@Component(service=UserService.class, immediate = true)
public class UserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	@Reference
	QueryBuilder queryBuilder;
	
	@Reference
	ResourceResolverFactory rrf;
	
	String users;
	
	public String getUsers() {
		Map<String, String> predicates = new HashMap<String, String>();
		predicates.put("property.value", "rep:User");
		predicates.put("path", "/home/users");
		predicates.put("property", "jcr:primaryType");
		predicates.put("p.limit", "-1");
		predicates.put("p.properties", "rep:principalName");
		
		Map<String, Object> rf = new HashMap<String, Object>();
		rf.put(rrf.SUBSERVICE, "writeservice");
		ResourceResolver rr = null;
		try {
			LOGGER.info("USER SERVICE LOG");
			rr=rrf.getServiceResourceResolver(rf);
			LOGGER.info("\n System User "+rr.getUserID());
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Query query =queryBuilder.createQuery(PredicateGroup.create(predicates),(Session) rr.adaptTo(Session.class));
		SearchResult results = query.getResult();
		List<Hit> hits = results.getHits();
		for (Hit hit : hits) {
            try {
				users = users + "\r" + hit.getProperties().get("rep:principalName", String.class);
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		return users;
	}

}
