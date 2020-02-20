package com.game2048.core.util;

import java.util.LinkedList;
import java.util.List;

public class Observable {
	List<Observer> observers = new LinkedList<>();

	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	public void notifyObservers() {
		for(Observer observer : observers)
			observer.update(this);
	}
}
