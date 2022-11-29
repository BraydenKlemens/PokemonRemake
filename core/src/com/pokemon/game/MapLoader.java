package com.pokemon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapLoader {

	// Map
	private final TiledMap tiledMap;
	private final TiledMapRenderer tiledMapRenderer;
	private final int mapPixelW;
	private final int mapPixelH;
	private final int[] backgroundlayers = { 0, 1, 2 };
	private final int[] foregroundlayers = { 3, 4 };
	
	//i have a hashmap to save the name with the map

	public MapLoader(String string) {
		// MAP
		// load map
		tiledMap = new TmxMapLoader().load(string);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		// Map size - pixels
		MapProperties prop = tiledMap.getProperties();
		int mapW = prop.get("width", Integer.class);
		int mapH = prop.get("height", Integer.class);
		int tilePixelW = prop.get("tilewidth", Integer.class);
		int tilePixelH = prop.get("tileheight", Integer.class);
		
		//get map pixel properties
		mapPixelW = mapW * tilePixelW;
		mapPixelH = mapH * tilePixelH;
	}

	/**
	 * 
	 */
	public void renderTiledMap() {
		tiledMapRenderer.render();
	}

	/**
	 * 
	 */
	public void drawBackgroundLayers() {
		tiledMapRenderer.render(backgroundlayers);
	}

	/**
	 * 
	 */
	public void drawForegroundLayers() {
		tiledMapRenderer.render(foregroundlayers);
	}

	/**
	 * @param camera
	 */
	public void setCamera(OrthographicCamera camera) {
		tiledMapRenderer.setView(camera);
	}

	/**
	 * @param camera
	 * @param player
	 */
	public void movecamera(OrthographicCamera camera, Player player) {

		// bottom and left
		camera.position.x = Math.max(player.getX() + 35, Gdx.graphics.getWidth() * camera.zoom / 2);
		camera.position.y = Math.max(player.getY() + 20, Gdx.graphics.getHeight() * camera.zoom / 2);

		// top and right
		camera.position.x = Math.min(camera.position.x, (mapPixelW - (Gdx.graphics.getWidth() / 2 * camera.zoom)));
		camera.position.y = Math.min(camera.position.y, (mapPixelH - (Gdx.graphics.getHeight() / 2 * camera.zoom)));

	}

	/**
	 * @param camera
	 */
	public void updateCamera(OrthographicCamera camera) {
		camera.update();
	}

	/**
	 * @return the tiledMap
	 */
	public TiledMap getTiledMap() {
		return tiledMap;
	}

	/**
	 * @return the tiledMapRenderer
	 */
	public TiledMapRenderer getTiledMapRenderer() {
		return tiledMapRenderer;
	}

	/**
	 * @return the backgroundlayers
	 */
	public int[] getBackgroundlayers() {
		return backgroundlayers;
	}

	/**
	 * @return the foregroundlayers
	 */
	public int[] getForegroundlayers() {
		return foregroundlayers;
	}
}
