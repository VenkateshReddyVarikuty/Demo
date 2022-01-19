package com.ugam.demo.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.collections.iterators.TransformIterator;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.commerce.common.ValueMapDecorator;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;

@Component(
		service = Servlet.class,
		property = {"sling.servlet.resourceTypes=/apps/dropDownList"}
		)

public class DropdownServlet extends SlingSafeMethodsServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(DropdownServlet.class);

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.info("Dropdown servlet");
		ResourceResolver rr = request.getResourceResolver();		
		Resource resource = request.getResource();
		Resource dataSource = resource.getChild("datasource");		
		String rootPath = dataSource.getValueMap().get("rootPath",String.class);
		List<KeyValue> dropDownList = new ArrayList<>();
		LOGGER.debug("Path {} ", rootPath);
		Resource resourcePath = rr.getResource(rootPath);
		LOGGER.debug("Resource Path : {} ", resourcePath);
		Page page = resourcePath.adaptTo(Page.class);
		Iterator<Page> iterator = page.listChildren();
		List<Page> list = new ArrayList<>();
		iterator.forEachRemaining(list::add);
		list.forEach(res -> {
			String name = res.getName();
			String title = res.getTitle();
			dropDownList.add(new KeyValue(name, title));
			LOGGER.debug("Title : {}", title);
		});	
		@SuppressWarnings("unchecked")
		DataSource ds = new SimpleDataSource(new TransformIterator(dropDownList.iterator(),
				input -> {
					KeyValue keyValue = (KeyValue) input;
					ValueMap vm = new ValueMapDecorator(new HashMap<>());
					vm.put("value", keyValue.key);
					vm.put("text", keyValue.value);
					return new ValueMapResource(
							rr, new ResourceMetadata(),
							JcrConstants.NT_UNSTRUCTURED, vm);
				}));
		request.setAttribute(DataSource.class.getName(), ds);		
	}
	private class KeyValue {

		private String key;
		private String value;

		private KeyValue(final String newKey, final String newValue) {
			this.key = newKey;
			this.value = newValue;
		}
	}
}
