package com.factory.abs;

public class BmwFactory implements CarFactory {

	@Override
	public Sedan produceSedan() {
		return new BmwSedan();
	}

	@Override
	public Trunk produceTrunk() {
		return new BmwTrunk();
	}

}
