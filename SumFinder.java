/*
 
Jordan Bui
CS 3310
Fall 2020
10/12/2020

Midterm Project 2

Description:
	Tests arrays of size 100, 1000, 100000, and 1000000. Searches generated 
	random arrays for a given sum and returns the two numbers.
	
	a) Sorted - sorted using comb sort, run time does not include sort.
	b) Unsorted
	
	Prints sum and the two numbers added to get sum.
	Only array of size 100 will print array.
	Prints run time of search algorithm.
	
	Method 1: Brute Force
	Method 2:
	
*/

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SumFinder 
{	

	// Resizes the value for gap in sort
	int nextGap(int g)
	{
		g = (g*10) / 13;
		if (g < 1)
			return 1;
		return g;
	}
	void sort(int arr[])
	{
		int len = arr.length;
		int gap = len;
		boolean hasSwapped = true;
		while (gap != 1 || hasSwapped == true)
		{
			gap = nextGap(gap);
			hasSwapped = false;
			for (int i = 0; i < len - gap; i++)
			{
				if (arr[i] > arr[i + gap])
				{
					int temp = arr[i];
					arr[i] = arr[i + gap];
					arr[i + gap] = temp;
					hasSwapped = true;
				}
			}
		}
	}
	
	
	// Brute Force Sorted Search
	public static int[] searchSortedSumBF(int arr[], int sum)
	{
		int i1 = 0;
		int i2 = arr.length - 1;
		int ansArr[] = new int[] {0, 0};
		
		while (i1 < i2)
		{
			if (arr[i1] + arr[i2] == sum)
			{
				ansArr[0] = arr[i1];
				ansArr[1] = arr[i2];
				return ansArr;
			}
			else if (arr[i1] + arr[i2] < sum)
				i1++;
			else
				i2--;
		}
		return ansArr;
	}
	
	// Brute Force Unsorted Search
	public static int[] searchSumBF(int arr[], int sum)
	{
		
		int arrSum = 0;
		int i1 = 0;
		int i2 = 1;
		int num1 = arr[i1];
		int num2 = arr[i2];
		int ansArr[] = new int[2];
		
		while (arrSum != sum && i1 < arr.length)
		{
			if (i2 >= arr.length && i1 < arr.length)
			{
				i1++;
				i2 = 1;
				num1 = arr[i1];
			}
			num2 = arr[i2];
			arrSum = num1 + num2;
			i2++;
		}
		if (arrSum == sum)
		{
			ansArr[0] = num1;
			ansArr[1] = num2;
			return ansArr;
		} else
			return null;
	}
	
	// HashMap Sorted and Unsorted Search
	public static int[] searchSumHM(int arr[], int sum)
	{
		Map<Integer, Integer> arrMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < arr.length; i++)
		{
			int dif = sum - arr[i];
			if (arrMap.containsKey(dif))
				return new int[] {arrMap.get(dif), i};
			else
				arrMap.put(arr[i], i);
		}
		return new int[] {0, 0};
	}
	
	
	// Test Program
	public static void main(String[] args)
	{
		System.out.println("CS 3310 Midterm Project 2");
		System.out.println("Jordan Bui\n");
		
		SumFinder sumf = new SumFinder();
		Random rand = new Random();
		int loSize = 100;
		int hiSize = 1000000;
		int sum = rand.nextInt(200 - (-200)) - 200;
		
		// Test
		while (loSize <= hiSize)
		{
			int randArr[] = new int[loSize];
			for (int i = 0; i < loSize; i++)
			{
				randArr[i] = rand.nextInt(100 - (-100)) - 100;
			}
			System.out.println("Size " + loSize);
			
			// Unsorted Array
				// Brute Force
				// Display if size = 100
			if (loSize == 100)
			{
				System.out.println("Array Size 100:");
				for (int i = 0; i < loSize; i++)
				{
					System.out.print(randArr[i] + " ");
					if (i % 10 == 0 && i != 0)
						System.out.println();
				}
				System.out.println();
			}
			int answerBF[] = new int[2];
			long startTime = System.nanoTime();
			answerBF = searchSumBF(randArr, sum);
			long endTime = System.nanoTime();
			System.out.println(sum + " = " + answerBF[0] + " + " + answerBF[1]);
			System.out.println("Time elapsed for Unsorted Brute Force: " + (endTime - startTime) + " ns");
			
				// Hash Map
			int answerHM[] = new int[2];
			startTime = System.nanoTime();
			answerHM = searchSumHM(randArr, sum);
			endTime = System.nanoTime();
			System.out.println(sum + " = " + randArr[answerHM[0]] + " + " + randArr[answerHM[1]]);
			System.out.println("Time elapsed for Unsorted Hash Map: " + (endTime - startTime) + " ns");
			System.out.println("----------------------------------------------\n");
			
			
			// Sorted Array
			System.out.println("Size " + loSize);
				// **SORT**
			sumf.sort(randArr);
				// Display if size = 100
			if (loSize == 100)
			{
				System.out.println("Array Size 100:");
				for (int i = 0; i < loSize; i++)
				{
					System.out.print(randArr[i] + " ");
					if (i % 10 == 0 && i != 0)
						System.out.println();
				}
				System.out.println();
			}
				// Brute Force
			int sAnswerBF[] = new int[2];
			startTime = System.nanoTime();
			sAnswerBF = searchSortedSumBF(randArr, sum);
			endTime = System.nanoTime();
			System.out.println(sum + " = " + sAnswerBF[0] + " + " + sAnswerBF[1]);
			System.out.println("Time elapsed for Sorted Brute Force: " + (endTime - startTime) + " ns");
			
				// Hash Map
			int sAnswerHM[] = new int[2];
			startTime = System.nanoTime();
			sAnswerHM = searchSumHM(randArr, sum);
			endTime = System.nanoTime();
			System.out.println(sum + " = " + randArr[sAnswerHM[0]] + " + " + randArr[sAnswerHM[1]]);
			System.out.println("Time elapsed for Sorted Hash Map: " + (endTime - startTime) + " ns");
			System.out.println("----------------------------------------------\n");
			
			// Change size of next array
			loSize *= 10;
			if (loSize == 10000)
				loSize *= 10;
		}		
	}
}