import java.util.Random;
import java.util.Arrays;

public class quickSort {
	private static Random rnd = new Random();
	public static int[] quickSort(int[] array) {
		quickSort(array, 0, array.length - 1);
		return array;
	}

	public static void quickSort(int[] array, int low, int high) {
		if(low >= high)
			return;
		
		int partition = low + rnd.nextInt(high - low + 1);
		int pivot = low;

		swap(array, partition, low);

		for(int i = low + 1; i <= high; i++)
			if(array[i] < array[pivot])
				swap(array, ++pivot, i);

		swap(array, pivot, low);
		quickSort(array, low, pivot - 1);
		quickSort(array, pivot + 1, high);
	}

	public static int[] mergeSort(int[] array) {
		mergeSort(array, new int[array.length], 0, array.length - 1);
		return array;
	}

	public static void mergeSort(int[] array, int[] ray, int low, int high) {
		if(low >= high)
			return;
		
		int mid = (low + high) >>> 1;
		mergeSort(array, ray, low, mid);
		mergeSort(array, ray, mid + 1, high);
		merge(array, ray, low, mid, high);
	}

	private static void merge(int[] array, int[] ray, int low, int mid, int high) {
		for(int i = low; i <= high; i++)
			ray[i] = array[i];

		int lowOfHigh = mid + 1;
		int prod = low;

		while(low <= mid && lowOfHigh <= high) {
			if(ray[low] <= ray[lowOfHigh]) {
				array[prod] = ray[low];
				low++;
			} else {
				array[prod] = ray[lowOfHigh];
				lowOfHigh++;
			}
			prod++;
		}

		while(low <= mid) {
			array[prod] = ray[low];
			prod++;
			low++;
		}
	}

	private static void swap(int[] array, int a, int b) {
		int temp = array[a]; 
		array[a] = array[b];
		array[b] = temp;
	}

	public static void main(String[] args) {
		int[] input = {1,10,5,63,29,71,10,12,44,29,10,-1};
		
		input = mergeSort(input);
		System.out.println(Arrays.toString(input));
	}
}
