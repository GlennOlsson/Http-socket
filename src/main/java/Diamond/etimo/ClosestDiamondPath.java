/*
 * Copyright 2017 Glenn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons
 * to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package Diamond.etimo;

import java.util.ArrayList;

public class ClosestDiamondPath {
	private ArrayList<Diamond> diamonds;
	private long pathLength;
	
	public ClosestDiamondPath(Diamond diamond1, Diamond diamond2, Diamond diamond3, Diamond diamond4, Diamond diamond5, long baseX, long baseY){
		diamonds = new ArrayList<>();
		diamonds.add(diamond1);
		diamonds.add(diamond2);
		diamonds.add(diamond3);
		diamonds.add(diamond4);
		diamonds.add(diamond5);
		
		long baseAndFirst = getRouteLength(baseX, baseY, diamond1.getX(), diamond1.getY());
		long firstAndSecond = getRouteLength(diamond1.getX(), diamond1.getY(), diamond2.getX(), diamond2.getY());
		long secondAndThird = getRouteLength(diamond2.getX(), diamond2.getY(), diamond3.getX(), diamond3.getY());
		long thirdAndFourth = getRouteLength(diamond3.getX(), diamond3.getY(), diamond4.getX(), diamond4.getY());
		long fourthAndFifth = getRouteLength(diamond4.getX(), diamond4.getY(), diamond5.getX(), diamond5.getY());
		long fifthAndBase = getRouteLength(diamond5.getX(), diamond5.getY(), baseX, baseY);
		
		pathLength = baseAndFirst + fifthAndBase + secondAndThird + thirdAndFourth + fourthAndFifth + fifthAndBase;
		
	}
	
	private long getRouteLength(long x1, long y1, long x2, long y2){
		long thisRoute = Math.abs(x1 - x2) + Math.abs(y1 - y2);
		return thisRoute;
	}
	
	public long getPathLength() {
		return pathLength;
	}
	
	public ArrayList<Diamond> getDiamonds() {
		return diamonds;
	}
}
