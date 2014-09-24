package com.joanflo.utils;

import com.joanflo.tagit.ProductActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Parcelable;


public class NFC {

	
	public static final int PARSING_ERROR = -1;
	
	
	
	public static void setNfcAdapter(ProductActivity activity) {
		// Check for available NFC Adapter
		NfcAdapter adapter = NfcAdapter.getDefaultAdapter(activity);
        if (adapter != null) {
        	// Register callback
            adapter.setNdefPushMessageCallback(activity, activity);
        }
	}
	
	
	
	public static boolean isNFCIntent(Intent intent) {
		return NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction());
	}
	
	
	
	public static boolean nfcIsAvailable(Activity activity) {
		NfcManager manager = (NfcManager) activity.getSystemService(Context.NFC_SERVICE);
		NfcAdapter adapter = manager.getDefaultAdapter();
		return adapter != null && adapter.isEnabled();
	}
	
	
	
	public static int retrieveData(Intent intent) {
		try {
			// raw data
			Parcelable[] RawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			
			// NDEF data
			NdefMessage[] NDEFMessages = null;
			
			// convert raw data into NDEF data
			if (RawMessages != null) {
				NDEFMessages = new NdefMessage[RawMessages.length];
	            for (int i = 0; i < RawMessages.length; i++) {
	            	NDEFMessages[i] = (NdefMessage) RawMessages[i];
	            }
	        }
			
			// process NDEF data, get records
			NdefMessage message = NDEFMessages[0];
			NdefRecord[] NDEFRecords = message.getRecords();
			
			// get payload
			NdefRecord record = NDEFRecords[0];
			byte[] payload = record.getPayload();
			
			// convert bytes to integer
			String productId = new String(payload);
			return Integer.valueOf(productId);
			
		} catch (Exception e) {
			return PARSING_ERROR;
		}
	}
	
	
	
	public static NdefMessage createBeamMessage(int id) {
		// convert integer to bytes
		String productId = String.valueOf(id);
		byte[] payload = productId.getBytes();
		
		// create records array
		NdefRecord[] records = new NdefRecord[2];
		NdefRecord record;
		
		// productID record (mime type = text/plain)
		record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);
		records[0] = record;
		
		// application record
		record = NdefRecord.createApplicationRecord("com.joanflo.tagit");
		records[1] = record;
		
		// create NDEF message
		NdefMessage message = new NdefMessage(records);
		
		return message;
	}
	
	
}