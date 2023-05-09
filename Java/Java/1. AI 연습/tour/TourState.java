package tour;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import search.Action;
import search.State;

public class TourState implements State {
	protected final Set<City> visitedCities;
	protected final City currentCity;
	
	public TourState(City startCity) {
		this.visitedCities = Collections.emptySet();
		this.currentCity = startCity;
	}
	public TourState(Set<City> visitedCities, City currentCity) {
		this.visitedCities = visitedCities;
		this.currentCity = currentCity;
	}
	public Set<Road> getApplicableActions() {
		return currentCity.outgoingRoads;
	}
	public State getActionResult(Action action) {
		Road road = (Road)action;
		Set<City> newVisitedCities = new LinkedHashSet<City>(visitedCities);
		newVisitedCities.add(road.targetCity);
		return new TourState(newVisitedCities, road.targetCity);
	}

	@Override
	public boolean equals(Object that) {

		// Check if we are comparing the same object
		if (this == that) {
			return true;
		}

		//Check if the compared object is null, or in a different class
		if (that == null || getClass() != that.getClass()) {
			return false;
		}

		//There may be multiple sub-classes, thus cast into higher class
		TourState tourState = (TourState) that;

		//If the visited cities are different, return false
		if (!Objects.equals(visitedCities, tourState.visitedCities)) {
			return false;
		}

		//If the visited cities are the same, check if the current city is the same, and return true/false
		return Objects.equals(currentCity, tourState.currentCity);
	}

	@Override
	public int hashCode() {

		//Create appropriate variables; In our function, we use 31 (a popular prime)
		int result = 0; int temp = 0; int prime = 31;

		//If there are visited cities, use those to create an initial temporary hash
		if (visitedCities != null) {
			result = visitedCities.hashCode();
		}

		//Use the current city to create a second temporary hash
		if(currentCity != null) {
			temp = currentCity.hashCode();
		}

		//Use the prime and the two temporary hashes to create a final hash
		result = prime * result + temp;

		return result;
	}
}
