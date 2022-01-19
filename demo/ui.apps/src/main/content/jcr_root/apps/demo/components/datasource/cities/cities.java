<%@page session="false" 
		import="org.apache.sling.api.resource.Resource,
        org.apache.sling.api.resource.ValueMap,
        org.apache.sling.api.resource.ResourceMetadata,
        org.apache.sling.api.wrappers.ValueMapDecorator,
		com.adobe.granite.ui.components.Field,
        com.adobe.granite.ui.components.ds.DataSource,
        com.adobe.granite.ui.components.ds.SimpleDataSource,
        com.adobe.granite.ui.components.ds.ValueMapResource"%>

<%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %>
<cq:defineObjects/>

<%            
	ValueMap fieldProps = (ValueMap)request.getAttribute(Field.class.getName());
	String[] fieldVal = fieldProps.get("value", new String[0]);
	String value = "";
	if (fieldVal.length > 0) {
		value = fieldVal[0];
	}

	ValueMap vm = new ValueMapDecorator(new java.util.HashMap<String, Object>());
	vm.put("value", value);
	
	java.util.List<Resource> resourceList = new java.util.ArrayList<Resource>();
	resourceList.add(new ValueMapResource(resourceResolver, new ResourceMetadata(), "nt:unstructured", vm));

	request.setAttribute(DataSource.class.getName(), new SimpleDataSource(resourceList.iterator()));
%>
