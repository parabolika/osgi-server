package com.parabolika.server.model.util;

public class ModelHelpers {
	public static int direction(int dx, int dy) {
		if(dx < 0) {
			if(dy < 0) {
				return 5;
			} else if(dy > 0) {
				return 0;
			} else {
				return 3;
			}
		} else if(dx > 0) {
			if(dy < 0) {
				return 7;
			} else if(dy > 0) {
				return 2;
			} else {
				return 4;
			}
		} else {
			if(dy < 0) {
				return 6;
			} else if(dy > 0) {
				return 1;
			} else {
				return -1;
			}
		}
	}
}
