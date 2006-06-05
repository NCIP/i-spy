package gov.nih.nci.ispy.web.xml;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class XSLParamProcessor {

	public String key;
	public String filter_value1;
	public String filter_value2;
	public String filter_value3;
	public String filter_value4;
	public String filter_value5;
	public String filter_value6;
	public String filter_value7;
	

	public XSLParamProcessor(HttpServletRequest request)	{;
		this.setKey((String)request.getParameter("key"));
		this.setFilter_value1((String)request.getParameter("filter_value1"));
		this.setFilter_value3((String)request.getParameter("filter_value2"));
		this.setFilter_value3((String)request.getParameter("filter_value3"));
		this.setFilter_value4((String)request.getParameter("filter_value4"));
		this.setFilter_value5((String)request.getParameter("filter_value5"));
		this.setFilter_value6((String)request.getParameter("filter_value6"));
		this.setFilter_value7((String)request.getParameter("filter_value7"));
	}

	public String getFilter_value1() {
		return filter_value1;
	}

	public void setFilter_value1(String filter_value1) {
		this.filter_value1 = filter_value1;
	}

	public String getFilter_value2() {
		return filter_value2;
	}

	public void setFilter_value2(String filter_value2) {
		this.filter_value2 = filter_value2;
	}

	public String getFilter_value3() {
		return filter_value3;
	}

	public void setFilter_value3(String filter_value3) {
		this.filter_value3 = filter_value3;
	}

	public String getFilter_value4() {
		return filter_value4;
	}

	public void setFilter_value4(String filter_value4) {
		this.filter_value4 = filter_value4;
	}

	public String getFilter_value5() {
		return filter_value5;
	}

	public void setFilter_value5(String filter_value5) {
		this.filter_value5 = filter_value5;
	}

	public String getFilter_value6() {
		return filter_value6;
	}

	public void setFilter_value6(String filter_value6) {
		this.filter_value6 = filter_value6;
	}

	public String getFilter_value7() {
		return filter_value7;
	}

	public void setFilter_value7(String filter_value7) {
		this.filter_value7 = filter_value7;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
