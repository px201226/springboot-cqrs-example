package com.blogsearch.util;

import static java.util.Spliterator.ORDERED;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BatchingIterator<T> implements Iterator<List<T>> {

	public static <T> Stream<List<T>> batchedStreamOf(Stream<T> originalStream, int batchSize) {
		return asStream(new BatchingIterator<>(originalStream.iterator(), batchSize));
	}

	private static <T> Stream<T> asStream(Iterator<T> iterator) {
		return StreamSupport.stream(
				Spliterators.spliteratorUnknownSize(iterator, ORDERED), false
		);
	}

	private int batchSize;
	private List<T> currentBatch;
	private Iterator<T> sourceIterator;

	public BatchingIterator(Iterator<T> sourceIterator, int batchSize) {
		this.batchSize = batchSize;
		this.sourceIterator = sourceIterator;
	}

	@Override
	public boolean hasNext() {
		prepareNextBatch();
		return currentBatch != null && !currentBatch.isEmpty();
	}

	@Override
	public List<T> next() {
		return currentBatch;
	}

	private void prepareNextBatch() {
		currentBatch = new ArrayList<>(batchSize);
		while (sourceIterator.hasNext() && currentBatch.size() < batchSize) {
			currentBatch.add(sourceIterator.next());
		}
	}
}