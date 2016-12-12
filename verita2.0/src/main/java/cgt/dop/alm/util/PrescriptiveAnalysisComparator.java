package cgt.dop.alm.util;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class PrescriptiveAnalysisComparator implements Comparator<PrescriptiveAnalysisTO> {
	
	public int compare(PrescriptiveAnalysisTO obj1, PrescriptiveAnalysisTO obj2) {		
	    int result = obj1.getSeverity().compareTo(obj2.getSeverity());	
	    if (result == 0)
		result = obj2.getCount().compareTo(obj1.getCount());	    
	    
	    return result;

	}

	@Override
	public Comparator<PrescriptiveAnalysisTO> reversed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<PrescriptiveAnalysisTO> thenComparing(
			Comparator<? super PrescriptiveAnalysisTO> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> Comparator<PrescriptiveAnalysisTO> thenComparing(
			Function<? super PrescriptiveAnalysisTO, ? extends U> keyExtractor,
			Comparator<? super U> keyComparator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U extends Comparable<? super U>> Comparator<PrescriptiveAnalysisTO> thenComparing(
			Function<? super PrescriptiveAnalysisTO, ? extends U> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<PrescriptiveAnalysisTO> thenComparingInt(
			ToIntFunction<? super PrescriptiveAnalysisTO> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<PrescriptiveAnalysisTO> thenComparingLong(
			ToLongFunction<? super PrescriptiveAnalysisTO> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<PrescriptiveAnalysisTO> thenComparingDouble(
			ToDoubleFunction<? super PrescriptiveAnalysisTO> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}


	public static <T extends Comparable<? super T>> Comparator<T> reverseOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static <T extends Comparable<? super T>> Comparator<T> naturalOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static <T> Comparator<T> nullsFirst(Comparator<? super T> comparator) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static <T> Comparator<T> nullsLast(Comparator<? super T> comparator) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static <T, U> Comparator<T> comparing(
			Function<? super T, ? extends U> keyExtractor,
			Comparator<? super U> keyComparator) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static <T, U extends Comparable<? super U>> Comparator<T> comparing(
			Function<? super T, ? extends U> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static <T> Comparator<T> comparingInt(
			ToIntFunction<? super T> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static <T> Comparator<T> comparingLong(
			ToLongFunction<? super T> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static <T> Comparator<T> comparingDouble(
			ToDoubleFunction<? super T> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

}
