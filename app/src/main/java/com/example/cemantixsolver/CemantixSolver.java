package com.example.cemantixsolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CemantixSolver {

	private final Map<String, Double> scoreCache = new HashMap<>();

	private final CemantixScoreService scoreService = new CemantixScoreService();
	private final LexicalFieldService lexicalFieldService = new LexicalFieldService();

	private static final List<String> ROOT_WORDS = Arrays.asList("objet", "ciel", "terre", "sentiment", "pensée", "travail",
			"espace", "santé", "pays", "triste", "histoire", "humain", "vision", "politique");

	public String solve() {

		double bestScore = Double.NEGATIVE_INFINITY;

		List<String> sample = new ArrayList<>(ROOT_WORDS);

		String bestWord = null;

		while (bestScore != 1.0) {
			bestWord = getBestWord(sample);
			if (bestWord != null) {
				Double score = scoreCache.get(bestWord);
				List<String> newSsample = new ArrayList<>();
				if (score != null) {
					if (score == 1.0) {
						return bestWord;
					}
					if (score > bestScore) {
						bestScore = score;
					}
					newSsample = lexicalFieldService.getSimilarWords(bestWord);
				}
				if (newSsample.isEmpty()) {
					sample.remove(bestWord);
				} else {
					sample = newSsample;
				}
			}
		}
		return null;
	}

	public String getBestWord(List<String> sample) {
		Optional<String> max = sample.stream().parallel().filter(w -> !scoreCache.containsKey(w)).max((w1, w2) -> Double
				.compare(scoreService.getScore(w1, scoreCache), scoreService.getScore(w2, scoreCache)));
		return max.orElse(null);
	}

}
