package com.factory.abs;

public class AodiFactory implements CarFactory {

	@Override
	public Sedan produceSedan() {
		return new AodiSedan();
	}

	@Override
	public Trunk produceTrunk() {
		return new AodiTrunk();
	}

}
