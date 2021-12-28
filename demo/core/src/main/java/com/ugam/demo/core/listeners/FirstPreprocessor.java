package com.ugam.demo.core.listeners;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.Preprocessor;
import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.ReplicationOptions;

@Component(service = Preprocessor.class,
immediate = true)
public class FirstPreprocessor implements Preprocessor{

	public static final Logger  LOGGER = LoggerFactory.getLogger(FirstPreprocessor.class);

	@Reference
	private ResourceResolverFactory rrf;

	@Override
	public void preprocess(ReplicationAction replicationAction, ReplicationOptions replicationOptions) throws ReplicationException {

		String pagePath = "/content/demo/en/home/jcr:content";
		String actionPath = replicationAction.getPath(); 	 
		String action = replicationAction.getType().getName(); 

		Map<String, Object> rf = new HashMap<String, Object>();
		rf.put(rrf.SUBSERVICE, "writeservice");
		ResourceResolver rr = null;
		Session session = null;
		try {
			rr=rrf.getServiceResourceResolver(rf);
			session = rr.adaptTo(Session.class);
			if(actionPath.equals("/content/demo/en/home")){
				Resource resource = rr.getResource(pagePath);
				if(resource!=null) {
					if (action.equalsIgnoreCase("Activate")) {
						Node node = resource.adaptTo(Node.class);
						Calendar cal = Calendar.getInstance();
						cal.set(Calendar.DATE, 30);
						node.setProperty("expirydate", cal);
						node.getSession().save();
					}
				}

			}

		} catch (LoginException | RepositoryException e) {
			LOGGER.debug(e.getMessage());
			e.printStackTrace();
		}
		finally {
			if (session != null && session.isLive()) {
				session.logout();
			}

		}
	}

}
