package edu.ktlab.w2v.app;

import java.io.IOException;
import java.util.List;

import edu.ktlab.w2v.distance.Distance;
import edu.ktlab.w2v.model.JW2VModel;
import edu.ktlab.w2v.model.Model;

public abstract class App {
	protected Model w2v;
	protected int topNSize;
	protected String path;
	protected Distance measure;

	public App(String path, int topNSize, Distance measure) {
		this.path = path;
		this.topNSize = topNSize;
		this.measure = measure;
	}

	public void loadJW2VModel() throws IOException {
		w2v = new JW2VModel(path);
	}

	public static void insertTopN(String name, float score, float[] vector,
			List<WordEntry> wordsEntrys, int topNSize) {
		if (wordsEntrys.size() < topNSize) {
			wordsEntrys.add(new WordEntry(name, score, vector));
			return;
		}
		float min = Float.MAX_VALUE;
		int minOffe = 0;
		for (int i = 0; i < topNSize; i++) {
			WordEntry wordEntry = wordsEntrys.get(i);
			if (min > wordEntry.score) {
				min = wordEntry.score;
				minOffe = i;
			}
		}

		if (score > min) {
			wordsEntrys.set(minOffe, new WordEntry(name, score, vector));
		}

	}
}
