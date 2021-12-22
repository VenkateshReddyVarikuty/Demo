package com.ugam.demo.core.schedulers;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Component(
		service=Runnable.class,
		property = {"scheduler.expression = */10 * * * * ?"}
		)
public class ExpiredArticles implements Runnable {
	
	public static final Logger  LOGGER = LoggerFactory.getLogger(ExpiredArticles.class);
	
	@Reference
	private ResourceResolverFactory rrf;
	
	@Reference
	private Replicator replicator;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		LOGGER.debug("Scheduler is working for every 10sec");		
		String pagePath = "/content/demo/en/home";
		Map<String, Object> rf = new HashMap<String, Object>();
		rf.put(rrf.SUBSERVICE, "writeservice");
		ResourceResolver rr = null;
		try {
			rr=rrf.getServiceResourceResolver(rf);
			PageManager pm = rr.adaptTo(PageManager.class);
			Page homePage = pm.getPage(pagePath);
			if(homePage!=null) {
				Iterator<Page> childPages = homePage.listChildren();
				while(childPages.hasNext()) {
					Page page = childPages.next();
					ValueMap pageProps = page.getProperties();
					Calendar expiryCal = pageProps.get("expirydate", Calendar.class);
					LOGGER.debug("Expiry Time :: {}", expiryCal);
					Date curDate = new Date();
					String pageStatus = pageProps.get("cq:lastReplicationAction", String.class);
					LOGGER.debug("Page Status :: {}", pageStatus);
					LOGGER.debug("Date Compare :: {}", curDate.compareTo(expiryCal.getTime()));
					if(expiryCal != null && pageStatus.equals("Activate") && curDate.compareTo(expiryCal.getTime()) > 0) {
						replicator.replicate(rr.adaptTo(Session.class), ReplicationActionType.DEACTIVATE, page.getPath());
						
					}
					
				}
			}
		} catch (LoginException | ReplicationException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error while deactivating article pages {}", e.getMessage());
			e.printStackTrace();
		}
		
	}
	
}
