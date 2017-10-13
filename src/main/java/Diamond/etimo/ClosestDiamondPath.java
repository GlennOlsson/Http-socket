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
