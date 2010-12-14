import java.util.ArrayList;
import java.util.HashMap;


public class GenerateCityBlock {
	private BuildingType type;
	private ArrayList<ArrayList<Building>> buildings;
	//location of building
	private HashMap<Building, Location> locations;
	//length of block
	private float length;
	//width of block
	private float width;
	
	public GenerateCityBlock(BuildingType type, float length, float width){
		this.type = type;
		this.length = length;
		this.width = width;
		
		buildings = new ArrayList<ArrayList<Building>>();
		locations = new HashMap<Building, Location>();
		
		generate();
		spaceBuildings();
		
	}
	
	private void generate(){
		float xSpace = 0;
		float ySpace = 0;
		
		boolean generate = true;
		int col = 0;
		
		while(generate){
			//keep track of max building length
			float maxY = ySpace;
			buildings.add(new ArrayList<Building>());
			while(xSpace < width){
				Building nBuilding = GenerateBuilding.generateBuilding(type);
				//location after building added
				float cXSpace = xSpace + nBuilding.getWidth();
				float cYSpace = ySpace + nBuilding.getLength();
				//if over width go to next y level
				if(cXSpace > width){
					break;
				}
				//if first building in a row doesn't fill in y direction then break, all buildings generated
				if(xSpace == 0 && cYSpace > length){
					generate = false;
					break;
				}
				//if building is not first in row then regenerate
				else if(cYSpace > length){
					continue;
				}
				maxY = Math.max(maxY, cYSpace);
				
				buildings.get(col).add(nBuilding);
				locations.put(nBuilding, new Location(xSpace, ySpace));
				
				xSpace += nBuilding.getWidth();
			}
			
			col++;
			xSpace = 0.0f;
			ySpace = maxY;
		}
		//remove extra empty ArrayList
		buildings.remove(col-1);
	}
	
	private void spaceBuildings(){
		int numberOfColumns = buildings.size();
		int maxNumberOfRows = 0;
		for(ArrayList<Building> b: buildings){
			maxNumberOfRows = Math.max(maxNumberOfRows, b.size());
		}
		
		float[] xSpace = new float[numberOfColumns];
		float[] ySpace = new float[maxNumberOfRows];
		
		float[] buildingsDistX = new float[numberOfColumns];;
		float[] buildingsDistY = new float[maxNumberOfRows];
		for(int i = 0; i < buildings.size(); i++){
			for(int j = 0; j < buildings.get(i).size(); j++){
				buildingsDistX[i] = buildingsDistX[i] + buildings.get(i).get(j).getWidth();
				buildingsDistY[j] = buildingsDistY[j] + buildings.get(i).get(j).getLength();
			}
		}
		
		for(int i = 0; i < xSpace.length; i++){
			xSpace[i] = (width-buildingsDistX[i])/buildings.get(i).size();
		}
		for(int i = 0; i < ySpace.length; i++){
			ySpace[i] = (length-buildingsDistY[i])/colomnSize(i , numberOfColumns);
		}
		
		float editX;
		float editY;
		for(int i = 0; i < buildings.size()-1; i++){
			for(int j = 0; j < buildings.get(i).size()-1; j++){
				Building building = buildings.get(i).get(j);
				try{
					Building editBuildingRight = buildings.get(i).get(j+1);
					Building editBuildingDown = buildings.get(i+1).get(j);
					
					editX = locations.get(building).getX() + building.getWidth() + xSpace[i];
					editY = locations.get(building).getY() + building.getLength() + ySpace[j];
					
					locations.get(editBuildingRight).setX(editX);
					locations.get(editBuildingDown).setY(editY);
				}
				catch(Exception e){}
			}
		}
	}
	
	private int colomnSize(int i, int maxCols){
		int size = 0;
		for(int j = 0; j < maxCols; j++){
			try{
				buildings.get(j).get(i);
				size++;
			}
			catch(Exception e){}
		}
		return size;
	}
	
	public ArrayList<ArrayList<Building>> getBuildings(){
		return buildings;
	}
	public HashMap<Building, Location> getLocations(){
		return locations;
	}
	
}
