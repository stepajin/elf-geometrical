package cz.cvut.elf.geom.geomplanet.sorting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum ObjectType {
	//level 1	
	APPLE("sorting/objects/level1/apple.png","sorting/objects/level1/apple.png","sorting/objects/level1/apple.png"),
	BREAD("sorting/objects/level1/bread.png","sorting/objects/level1/bread.png","sorting/objects/level1/bread.png"),
	PEACH("sorting/objects/level1/peach.png","sorting/objects/level1/peach.png","sorting/objects/level1/peach.png"),
	POTATO1("sorting/objects/level1/potato1.png","sorting/objects/level1/potato1.png","sorting/objects/level1/potato1.png"),
	POTATO2("sorting/objects/level1/potato2.png","sorting/objects/level1/potato2.png","sorting/objects/level1/potato2.png"),
	SAUSAGE1("sorting/objects/level1/sausage1.png","sorting/objects/level1/sausage1.png","sorting/objects/level1/sausage1.png"),
	SAUSAGE2("sorting/objects/level1/sausage2.png","sorting/objects/level1/sausage2.png","sorting/objects/level1/sausage2.png"),
	STEAK("sorting/objects/level1/steak.png","sorting/objects/level1/steak.png","sorting/objects/level1/steak.png"),
	CHERRY("sorting/objects/level1/cherry.png","sorting/objects/level1/cherry.png","sorting/objects/level1/cherry.png"),
	BURGER("sorting/objects/level1/burger.png","sorting/objects/level1/burger.png","sorting/objects/level1/burger.png"),
	
	//level 2
	CHERRY2("sorting/objects/level2/cherry1.png","sorting/objects/level2/cherry2.png","sorting/objects/level2/cherry3.png"),
	BURGER2("sorting/objects/level2/burger1.png","sorting/objects/level2/burger2.png","sorting/objects/level2/burger3.png"),
	
	//level 3
	PIZZA("sorting/objects/level3/pizza1.png","sorting/objects/level3/pizza2.png","sorting/objects/level3/pizza3.png");
	
	
	//***************
	private final List<String> img;

    private static final ObjectType[] valuesLevel1 = {APPLE,BREAD,PEACH,POTATO1,POTATO2,SAUSAGE1,SAUSAGE2,STEAK,CHERRY,BURGER}; 
    private static final ObjectType[] valuesLevel2 = {CHERRY2, BURGER2};
    private static final ObjectType[] valuesLevel3 = {PIZZA};
    
    
    private static final int sizeLevel1 = valuesLevel1.length;
    private static final int sizeLevel2 = valuesLevel2.length;
    private static final int sizeLevel3 = valuesLevel3.length;

    private static final Random random = new Random();
	
	
    private ObjectType(String img, String img2, String img3) {
        this.img = new ArrayList<String>();
        this.img.add(img);
        this.img.add(img2);
        this.img.add(img3);
    }
    
    
    public List<String> getImages() { 
        return this.img;
    }
    
    
    public static ObjectType getRandomTypeLevel1() {
        return valuesLevel1[random.nextInt(sizeLevel1)];
    }
    
    public static ObjectType getRandomTypeLevel2() {
        return valuesLevel2[random.nextInt(sizeLevel2)];
    }
    
    public static ObjectType getRandomTypeLevel3() {
        return valuesLevel3[random.nextInt(sizeLevel3)];
    }
}
