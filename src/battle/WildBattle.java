package battle;

import entities.Animation;
import entities.objectEntities.Trainer;
import entities.pokemon.Pokemon;
import org.newdawn.slick.BigImage;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by dwbrite on 4/28/16.
 */
public class WildBattle extends AbstractBattle {
	
	public WildBattle(Image arena, Pokemon wildPokemon, Trainer trainer) {
		this.setArena(arena);
		setEnemyPokemon(new Pokemon[]{wildPokemon});
		try {
			BigImage allyPng = new BigImage("TileSets/Sprites/pokemon/animated/back/" + trainer.getFirstConsciousMonster().getSpriteUrl() + ".png", Image.FILTER_NEAREST, 128);
			BigImage enemyPng = new BigImage("TileSets/Sprites/pokemon/animated/" + wildPokemon.getSpriteUrl() + ".png", Image.FILTER_NEAREST, 128);
			SpriteSheet allySheet = new SpriteSheet(allyPng.getSubImage(0, 0, allyPng.getWidth(), allyPng.getHeight()), 128, 128);
			SpriteSheet enemySheet = new SpriteSheet(enemyPng.getSubImage(0, 0, enemyPng.getWidth(), enemyPng.getHeight()), 128, 128);
			
			setAllySprites(new Animation[]{new Animation(allySheet)});
			setEnemySprites(new Animation[]{new Animation(enemySheet)});
			setAllyTrainers(new Trainer[]{trainer});
		} catch (Exception e) {
			//TODO: Failed to load sprites, maybe. If that fails, ask the user if they would like to save.
			e.printStackTrace();
		}
	}
}
