package ie.gmit.sw.ai.searchAlgos;

import java.awt.Color;

import static sun.awt.geom.Crossings.debug;

public class TraversatorStats {
	public static int printStats(Node node, long time, int visitCount, boolean countSteps){
		double depth = 0;
		int count = 0;
		while (node != null){
			node = node.getParent();
			if (node != null){
				if(!countSteps){
					if(node.getNodeType() != 'P'){
						node.setColor(Color.YELLOW);
						node.setNodeType('T');
						node.setWalkable(true);
					}
				}
				count++;
			}
			depth++;
		}

		if(debug){
			if(!countSteps){
				System.out.println("Visited " + visitCount + " nodes in " + time + "ms.");
				System.out.println("Found goal at a depth of " + String.format("%.0f", depth));
				System.out.println("EBF = B* = k^(1/d) = " + String.format("%.2f\n", Math.pow((double) visitCount, (1.00d / depth))));
			}
		}

		return count;
	}
}