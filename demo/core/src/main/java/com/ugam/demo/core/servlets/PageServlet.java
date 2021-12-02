package com.ugam.demo.core.servlets;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component(
		service = Servlet.class,
		property = {"sling.servlet.resourceTypes=cq:Page",
				"sling.servlet.selectors=childpages",
				"sling.servlet.extension=json"
		})
public class PageServlet extends SlingSafeMethodsServlet {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        final ResourceResolver resourceResolver = request.getResourceResolver();
        Page page = resourceResolver.adaptTo(PageManager.class).getPage("/content/ugams/us/en");
        List<String> pagesList = new ArrayList<String>();
            Iterator<Page> childPages = page.listChildren();
            while (childPages.hasNext()) {
                Page childPage = childPages.next();
                String pageName;
                pageName =childPage.getTitle();
                pagesList.add(pageName);
            }

        response.setContentType("application/json");
        response.getWriter().print(pagesList);
    }
}

