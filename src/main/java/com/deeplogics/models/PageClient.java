package com.deeplogics.models;

import java.util.Collection;

public class PageClient<T> {

	private long count;
	private String next;
	private String previous;
	private Collection<T> results;
	
	public PageClient() {}
	
	
	public PageClient(long count, String next, String previous, Collection<T> results) {
		this.count = count;
		this.next = next;
		this.previous = previous;
		this.results = results;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

	public Collection<T> getResults() {
		return results;
	}

	public void setResults(Collection<T> results) {
		this.results = results;
	}

}
