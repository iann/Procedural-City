import java.util.Random;


public class GenerateBuilding {
	
	private static Random rand = new Random();
	
	//max pixels set for N, S, W, E of the block
	private static int maxBlockNSWE;
	
	public static Building generateBuilding(BuildingType type) {
		Building building = new Building(type);
		
		int height = -1;
		int numOfBlocks = -1;
		switch(type){
			case HighRise: 
				height = rand.nextInt(50) + 252;
				numOfBlocks = rand.nextInt(6) + 1;
				maxBlockNSWE = 10; break;
			case MediumRise: 
				height = rand.nextInt(50) + 101;
				numOfBlocks = 1; 
				maxBlockNSWE = 10; break;
			case LowRise: 
				height = rand.nextInt(50) + 31;
				numOfBlocks = 1; 
				maxBlockNSWE = 10; break;
			default: System.out.println("No such building type" + type);System.exit(0);
		}
		
		building.setHeight(height);
		building.setNumOfBlocks(numOfBlocks);
		
		Block[] blocks = generateBlocks(building);
		building.setBlocks(blocks);
		
		//set building width and length
		float maxN = 0.0f;
		float maxS = 0.0f;
		float maxE = 0.0f;
		float maxW = 0.0f;
		for(Block b: blocks){
			maxN = Math.max(maxN, b.getN());
			maxS = Math.max(maxS, b.getS());
			maxE = Math.max(maxE, b.getE());
			maxW = Math.max(maxW, b.getW());
		}
		
		
		building.setWidth(maxE + maxW);
		building.setLength(maxN + maxS);
		
	//	System.out.println(maxE);
		//System.out.println(maxN);
		//System.out.println(maxW);
		//System.out.println(maxS);
		
		building.setMaxE(maxE+.002f);
		building.setMaxN(maxN+.002f);
		building.setMaxW(maxW+.002f);
		building.setMaxS(maxS+.002f);
		
		return building;
	}
	
	private static Block[] generateBlocks(Building building) {
		Block[] blocks = new Block[building.getNumOfBlocks()];
		
		for(int i = 0; i < blocks.length; i++) {
			blocks[i] = new Block();
			
			blocks[i].setNSEW(rand.nextInt(maxBlockNSWE) + 1, rand.nextInt(maxBlockNSWE) + 1, 
								rand.nextInt(maxBlockNSWE) + 1, rand.nextInt(maxBlockNSWE) + 1);
			
			//height of first block is height of the building
			//every other block height in lower, max of 20 pixels of previous block
			blocks[i].setHeight(building.getHeight() - i * rand.nextInt(50) + 1);
		}
		return blocks;
	}
}
