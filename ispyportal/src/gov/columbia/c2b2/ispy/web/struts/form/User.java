package gov.columbia.c2b2.ispy.web.struts.form;

public class User {
	private String id;
	private String password;
	private String completeName;

	public void setId(String id){
	this.id = id;
	}

	public void setPassword(String password){
	this.password = password;
	}

	public void setCompleteName(String completeName){
	this.completeName = completeName;
	}

	public String getId(){
	return id;
	}

	public String getPassword(){
	return password;
	}

	public String getCompleteName(){
	return completeName;
	}

}
