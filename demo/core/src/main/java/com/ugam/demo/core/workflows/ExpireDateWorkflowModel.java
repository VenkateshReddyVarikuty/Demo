package com.ugam.demo.core.workflows;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;
import com.ugam.demo.core.services.UserService;
@Component(
		service = WorkflowProcess.class,
		property = {"process.label=Update expiry date"}
		)
public class ExpireDateWorkflowModel implements WorkflowProcess{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	@Reference
	ResourceResolverFactory rrf;

	@Override
	public void execute(WorkItem workItem, WorkflowSession wfSession, MetaDataMap mdMap) throws WorkflowException {
		// TODO Auto-generated method stub
		
		
		
		String payload = workItem.getWorkflowData().getPayload().toString();
		
		Map<String, Object> rf = new HashMap<String, Object>();
		rf.put(rrf.SUBSERVICE, "writeservice");
		ResourceResolver rr = null;
		try {
			
			rr=rrf.getServiceResourceResolver(rf);
			LOGGER.info("\n System User "+rr.getUserID());
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Resource resource = rr.getResource(payload+"/jcr:content");
		if(resource!=null && payload !=null) {
			Node node = resource.adaptTo(Node.class);
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DATE, 20);
			try {
				node.setProperty("expirydate", cal);
				node.getSession().save();
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
