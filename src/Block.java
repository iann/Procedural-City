public class Block {
	private float height;
	private float N;
	private float S;
	private float E;
	private float W;
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public void setNSEW (float N, float S, float E, float W) {
		this.N = N;
		this.S = S;
		this.E = E;
		this.W = W;
	}
	
	public float getHeight(){
		return height;
	}
	
	public float getN(){
		return N;
	}
	
	public float getS(){
		return S;
	}
	
	public float getE(){
		return E;
	}
	
	public float getW(){
		return W;
	}
}
