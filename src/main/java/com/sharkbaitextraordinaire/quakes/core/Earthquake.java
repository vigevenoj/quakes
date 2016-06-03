package com.sharkbaitextraordinaire.quakes.core;

import org.geojson.Point;
import org.geojson.Feature;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Earthquake {
	private double magnitude;
	private String place;
	private long earthquaketime;
	private long update;
	private int tz;
	private String url;
	private String detail;
	private String felt;
	private String cdi;
	private int tsunami;
	private int sig;
	private String code;
	private String ids;
	private String type;
	private String title;
	private String id;
	private Point location;
	
	public Earthquake() { }
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public Earthquake(double magnitude, String place, long earthquaketime, long update, int tz,
			String url, String detail, String felt, String cdi, int tsunami, int sig,
			String code, String ids, String type, String title, String id, Point location) {
		this.magnitude = magnitude;
		this.place = place;
		this.earthquaketime = earthquaketime;
		this.update = update;
		this.tz = tz;
		this.url = url;
		this.detail = detail;
		this.felt = felt;
		this.cdi = cdi;
		this.tsunami = tsunami;
		this.sig = sig;
		this.code = code;
		this.ids = ids;
		this.type = type;
		this.title = title;
		this.id = id;
		this.location = location;
	}


  public Earthquake(Feature f) {
    /*public Earthquake(double magnitude, String place, long earthquaketime, long update, int tz,
      String url, String detail, String felt, String cdi, int tsunami, int sig,
      String code, String ids, String type, String title, String id, Point location) {*/
    Earthquake quake = new Earthquake();
    if (null != f.getProperty("mag")) {
      quake.setMagnitude(f.getProperty("mag"));
    }
    quake.setPlace(f.getProperty("place"));
    quake.setEarthquaketime(f.getProperty("time"));
    if (null != f.getProperty("update")) {
      quake.setUpdate(f.getProperty("update"));
    }
    quake.setTz(f.getProperty("tz"));
    quake.setUrl(f.getProperty("url"));
    quake.setDetail(f.getProperty("detail"));
    quake.setFelt(f.getProperty("felt"));
    quake.setCdi(f.getProperty("cdi"));
    quake.setTsunami(f.getProperty("tsunami"));
    quake.setSig(f.getProperty("sig"));
    quake.setCode(f.getProperty("code"));
    quake.setIds(f.getProperty("ids"));
    quake.setType(f.getProperty("type"));
    quake.setTitle(f.getProperty("title"));
    quake.setId(f.getProperty("id"));
    if (f.getGeometry() instanceof Point) {
      quake.setLocation((Point) f.getGeometry());
    } else {
      // throw illegal argument exception?
    }
  }
	
	public double getMagnitude() {
		return magnitude;
	}
	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	@JsonProperty("time")
	public long getEarthquaketime() {
		return earthquaketime;
	}
	@JsonProperty("time")
	public void setEarthquaketime(long earthquaketime) {
		this.earthquaketime = earthquaketime;
	}
	public long getUpdate() {
		return update;
	}
	public void setUpdate(long update) {
		this.update = update;
	}
	public int getTz() {
		return tz;
	}
	public void setTz(int tz) {
		this.tz = tz;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getFelt() {
		return felt;
	}
	public void setFelt(String felt) {
		this.felt = felt;
	}
	public String getCdi() {
		return cdi;
	}
	public void setCdi(String cdi) {
		this.cdi = cdi;
	}
	public int getTsunami() {
		return tsunami;
	}
	public void setTsunami(int tsunami) {
		this.tsunami = tsunami;
	}
	public int getSig() {
		return sig;
	}
	public void setSig(int sig) {
		this.sig = sig;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Point getLocation() {
		return this.location;
	}
	
	public void setLocation(Point point) {
		this.location = point;
	}
}
