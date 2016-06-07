package com.sharkbaitextraordinaire.quakes.db;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import com.sharkbaitextraordinaire.quakes.core.Earthquake;

@BindingAnnotation(BindEarthquake.EarthquakeBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindEarthquake {
	
	public static class EarthquakeBinderFactory implements BinderFactory {

		@Override
		public Binder<BindEarthquake, Earthquake> build(Annotation annotation) {
			return (q, bind, quake) -> {
				q.bind("magnitude", quake.getMagnitude());
				q.bind("place", quake.getPlace());
				q.bind("earthquaketime", quake.getEarthquaketime());
				q.bind("updatetime", quake.getUpdate());
				q.bind("tz", quake.getTz());
				q.bind("url", quake.getUrl());
				q.bind("detail", quake.getDetail());
				q.bind("felt", quake.getFelt());
				q.bind("cdi", quake.getCdi());
				q.bind("tsunami", quake.getTsunami());
				q.bind("sig", quake.getSig());
				q.bind("code", quake.getCode());
				q.bind("ids", quake.getIds());
				q.bind("type", quake.getType());
				q.bind("title", quake.getTitle());
				q.bind("id", quake.getId());
				q.bind("longitude", quake.getLocation().getCoordinates().getLongitude());
				q.bind("latitude", quake.getLocation().getCoordinates().getLatitude());
			};
		}
		
	}
}
