package com.sharkbaitextraordinaire.quakes;

/**
 * Configuration for analysis of earthquakes to determine if
 * notifications should be sent
 *
 */
public class EarthquakeAnalysisConfiguration {

	private double interestDistanceThreshold;
	private double interestMagnitudeThreshold;
	private double worryDistanceThreshold;
	private double worryMagnitudeThreshold;
	
	
	public double getInterestDistanceThreshold() {
		return interestDistanceThreshold;
	}
	public void setInterestDistanceThreshold(double interestDistanceThreshold) {
		this.interestDistanceThreshold = interestDistanceThreshold;
	}
	public double getInterestMagnitudeThreshold() {
		return interestMagnitudeThreshold;
	}
	public void setInterestMagnitudeThreshold(double interestMagnitudeThreshold) {
		this.interestMagnitudeThreshold = interestMagnitudeThreshold;
	}
	public double getWorryDistanceThreshold() {
		return worryDistanceThreshold;
	}
	public void setWorryDistanceThreshold(double worryDistanceThreshold) {
		this.worryDistanceThreshold = worryDistanceThreshold;
	}
	public double getWorryMagnitudeThreshold() {
		return worryMagnitudeThreshold;
	}
	public void setWorryMagnitudeThreshold(double worryMagnitudeThreshold) {
		this.worryMagnitudeThreshold = worryMagnitudeThreshold;
	}
	
	
}
