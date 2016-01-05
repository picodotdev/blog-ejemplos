package io.github.picodotdev.plugintapestry.services.sso;

import java.io.Serializable;
import java.util.UUID;

public class Sid implements Serializable {

	 private static final long serialVersionUID = -4552333438930728660L;
	 
	 private String sid;

	 protected Sid(String sid) {
		  this.sid = sid;
	 }
	 
	 public static Sid newInstance() {
		  return new Sid(UUID.randomUUID().toString());
	 }
	 
	 public String getSid() {
	 	 return sid;
	 }

	 public boolean isValid(String sid) {
		  return this.sid.equals(sid);
	 }
}