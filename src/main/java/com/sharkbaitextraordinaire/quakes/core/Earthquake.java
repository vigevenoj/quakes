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
	private int felt;
	private int cdi;
	private int tsunami;
	private int sig;
	private String code;
	private String ids;
	private String type;
	private String title;
	private String id;
	private Point location;

	public Earthquake() {
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public Earthquake(double magnitude, String place, long earthquaketime, long update, int tz, String url,
			String detail, int felt, int cdi, int tsunami, int sig, String code, String ids, String type,
			String title, String id, Point location) {
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

	public Earthquake(double magnitude, String place, long earthquaketime, long update, int tz, String url,
			String detail, int felt, int cdi, int tsunami, int sig, String code, String ids, String type,
			String title, String id, double longitude, double latitude) {
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
		this.location = new Point(longitude, latitude);
	}

	public Earthquake(Feature f) {
		/*
		 * public Earthquake(double magnitude, String place, long
		 * earthquaketime, long update, int tz, String url, String detail,
		 * String felt, String cdi, int tsunami, int sig, String code, String
		 * ids, String type, String title, String id, Point location) {
		 */
		this.magnitude = f.getProperty("mag") == null ? 0.0d : f.getProperty("mag");
		this.place = f.getProperty("place") == null ? "" : f.getProperty("place");
		this.earthquaketime = f.getProperty("time") == null ? 0 : f.getProperty("time");
		this.update = f.getProperty("update") == null ? 0 : f.getProperty("update");
		this.tz = f.getProperty("tz") == null ? 0 : f.getProperty("tz");
		this.url = f.getProperty("url") == null ? "" : f.getProperty("url");
		this.detail = f.getProperty("detail") == null ? "" : f.getProperty("detail");
		this.felt = f.getProperty("felt") == null ? 0 : f.getProperty("felt");
		this.cdi = f.getProperty("cdi") == null ? 0 : f.getProperty("cdi");
		this.tsunami = f.getProperty("tsunami") == null ? 0 : f.getProperty("tsunami");
		this.sig = f.getProperty("sig") == null ? 0 : f.getProperty("sig");
		this.code = f.getProperty("code") == null ? "" : f.getProperty("code");
		this.ids = f.getProperty("ids") == null ? "" : f.getProperty("ids");
		this.type = f.getProperty("type") == null ? "" : f.getProperty("type");
		this.title = f.getProperty("title") == null ? "" : f.getProperty("title");
		this.id = f.getId();
		if (f.getGeometry() instanceof Point) {
			this.location = (Point) f.getGeometry();
		} else {
			throw new IllegalArgumentException("Earthquake location was not a point");
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

	public int getFelt() {
		return felt;
	}

	public void setFelt(int felt) {
		this.felt = felt;
	}

	public int getCdi() {
		return cdi;
	}

	public void setCdi(int cdi) {
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
