package com.nespresso.sofa.recruitment.labyrinth;


import com.nespresso.sofa.recruitment.labyrinth.exceptions.ClosedDoorException;
import com.nespresso.sofa.recruitment.labyrinth.exceptions.DoorAlreadyClosedException;
import com.nespresso.sofa.recruitment.labyrinth.exceptions.IllegalMoveException;
import com.nespresso.sofa.recruitment.labyrinth.factory.Door;

public class Walker {
	private Room currentRoom;
	private Labyrinth labyrinth;
	private Door lastDoor;
	
	public Walker(Room currentRoom) {
		this.currentRoom = currentRoom;
	}

	public void walkTo(String targetRoomName) throws IllegalMoveException, ClosedDoorException {
		Room targetRoom = labyrinth.getRoomByName(targetRoomName);
		Door door = labyrinth.isThereAWay(currentRoom, targetRoom);
		if(targetRoom != null && door != null) {
			if(door.isClosed()) {
				throw new ClosedDoorException();
			} else {
				if(door.hasSensor()) labyrinth.addToPath(currentRoom.getName()+targetRoom.getName());
				currentRoom = targetRoom;
				lastDoor = door;
			}
		} 
		else {
			throw new IllegalMoveException();
		}
	}

	public String position() {
		return currentRoom.getName();
	}

	public void closeLastDoor() throws DoorAlreadyClosedException {
		if(lastDoor.isClosed()) {
			throw new DoorAlreadyClosedException();
		} else {
			lastDoor.setClosed(true);
		}
	}

	public void setLabyrinth(Labyrinth labyrinth) {
		this.labyrinth = labyrinth;
	}

}
