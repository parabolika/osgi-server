package com.parabolika.server.packet.translate.util;

public class TranslationHelpers {
	public static long nameToLong(String s) {
		long l = 0L;
		for(int i = 0; i < s.length() && i < 12; i++) {
			char c = s.charAt(i);
			l *= 37L;
			if(c >= 'A' && c <= 'Z') l += 1 + c - 65;
			else if(c >= 'a' && c <= 'z') l += 1 + c - 97;
			else if(c >= '0' && c <= '9') l += 27 + c - 48;
		}
		while(l % 37L == 0L && l != 0L) l /= 37L;
		return l;
	}

	public static String textUnpack(byte packedData[], int size) {
		byte[] decodeBuf = new byte[4096];
		int idx = 0, highNibble = -1;
		for (int i = 0; i < size * 2; i++) {
			int val = packedData[i / 2] >> 4 - 4 * (i % 2) & 0xf;
			if (highNibble == -1) {
				if (val < 13) {
					decodeBuf[idx++] = (byte) TranslationConstants.CHARACTER_XLATE_TABLE[val];
				} else {
					highNibble = val;
				}
			} else {
				decodeBuf[idx++] = (byte) TranslationConstants.CHARACTER_XLATE_TABLE[(highNibble << 4) + val - 195];
				highNibble = -1;
			}
		}
		return new String(decodeBuf, 0, idx);
	}

	public static void textPack(byte packedData[], String text) {
		if (text.length() > 80) {
			text = text.substring(0, 80);
		}
		text = text.toLowerCase();
		int carryOverNibble = -1;
		int ofs = 0;
		for (int idx = 0; idx < text.length(); idx++) {
			char c = text.charAt(idx);
			int tableIdx = 0;
			for (int i = 0; i < TranslationConstants.CHARACTER_XLATE_TABLE.length; i++) {
				if (c == (byte) TranslationConstants.CHARACTER_XLATE_TABLE[i]) {
					tableIdx = i;
					break;
				}
			}
			if (tableIdx > 12) {
				tableIdx += 195;
			}
			if (carryOverNibble == -1) {
				if (tableIdx < 13) {
					carryOverNibble = tableIdx;
				} else {
					packedData[ofs++] = (byte) tableIdx;
				}
			} else if (tableIdx < 13) {
				packedData[ofs++] = (byte) ((carryOverNibble << 4) + tableIdx);
				carryOverNibble = -1;
			} else {
				packedData[ofs++] = (byte) ((carryOverNibble << 4) + (tableIdx >> 4));
				carryOverNibble = tableIdx & 0xf;
			}
		}
		if (carryOverNibble != -1) {
			packedData[ofs++] = (byte) (carryOverNibble << 4);
		}
	}
}
