package com.nespresso.sofa.recruitment.labyrinth;

import java.util.HashSet;
import java.util.Set;

import com.nespresso.sofa.recruitment.labyrinth.factory.Door;

public class Labyrinth {
	Set<Room> rooms = new HashSet<Room>();
	Set<Door> doors = new HashSet<Door>();
	private String path = "";

	public Labyrinth(String... ways) {
		for (String way : ways) {
			String[] roomDoorRoom = way.split(Consts.SEPARATOR);
			
			Room sourceRoom = new Room(roomDoorRoom[0]);
			Room targetRoom = new Room(roomDoorRoom[2]);
			
			Door door = new Door(sourceRoom, targetRoom);
			door.setSensor(false);
			doors.add(door);
			
			rooms.add(sourceRoom);
			rooms.add(targetRoom);
			
			if(roomDoorRoom[1].equals(Consts.SIMPLE_DOOR)) {
				door.setSensor(false);
			} else if(roomDoorRoom[1].equals(Consts.SENSOR_DOOR)) {
				door.setSensor(true);
			}
		}
	}
	
	public Door isThereAWay(Room from, Room to) {
		for (Door door : doors) {
			if((door.getSourceRoom().equals(from) && door.getTargetRoom().equals(to)) || 
					(door.getSourceRoom().equals(to) && door.getTargetRoom().equals(from))) 
				return door;
		}
		return null;
	}

	public Walker popIn(String roomName) {
		Walker walker = new Walker(getRoomByName(roomName));
		walker.setLabyrinth(this);
		return walker;
	}

	public String readSensors() {
		return this.path;
	}
	
	public void addToPath(String path) {
		if(this.path == "") this.path = path;
		else this.path = this.path + ";" + path;
	}
	
	public Room getRoomByName(String roomName) {
		Room newRoom = new Room(roomName);
		for (Room room : rooms) {
			if(room.equals(newRoom)) {
				return room; 
			}
		}
		return null;
	}

}
