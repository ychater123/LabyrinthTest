package com.nespresso.sofa.recruitment.labyrinth.factory;

import com.nespresso.sofa.recruitment.labyrinth.Room;

public class Door {
	private Room sourceRoom;
	private Room targetRoom;
	private Boolean hasSensor = false;
	private Boolean isClosed = false;
	
	public Door(Room sourceRoom, Room targetRoom) {
		this.targetRoom = targetRoom;
		this.sourceRoom = sourceRoom;
	}
	
	public Room getSourceRoom() {
		return sourceRoom;
	}
	
	public Room getTargetRoom() {
		return targetRoom;
	}

	public Boolean hasSensor() {
		return hasSensor;
	}
	
	public void setSensor(Boolean hasSensor) {
		this.hasSensor = hasSensor;
	}

	public Boolean isClosed() {
		return isClosed;
	}
	
	public void setClosed(Boolean isClosed) {
		this.isClosed = isClosed;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hasSensor == null) ? 0 : hasSensor.hashCode());
		result = prime * result + ((sourceRoom == null) ? 0 : sourceRoom.hashCode());
		result = prime * result + ((targetRoom == null) ? 0 : targetRoom.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Door other = (Door) obj;
		if (hasSensor == null) {
			if (other.hasSensor != null)
				return false;
		} else if (!hasSensor.equals(other.hasSensor))
			return false;
		if (sourceRoom == null) {
			if (other.sourceRoom != null)
				return false;
		} else if (!sourceRoom.equals(other.sourceRoom))
			return false;
		if (targetRoom == null) {
			if (other.targetRoom != null)
				return false;
		} else if (!targetRoom.equals(other.targetRoom))
			return false;
		return true;
	}
	
	
	

}
