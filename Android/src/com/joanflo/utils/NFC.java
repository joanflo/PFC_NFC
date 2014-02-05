package com.joanflo.utils;

import com.joanflo.tagit.ProductActivity;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.util.Log;


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
	
	
	
	public static void checkAvaiability() {
		// comprovar que el mobil te NFC?
		
		// comprovar que el mobil te NFC activat
		
		// comprovar que el mobil te android beam activat
		
		// comprovar tipus de tag llegit + mime type 
		// (assegurar que la nostra app es la correcta)
	}
	
	
	
	public static int retrieveData(Intent intent) {
		try {
    		Log.i("ANDROD BEAM", "retrieveData 0");
    		
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
    		Log.i("ANDROD BEAM", "retrieveData 1");
			return Integer.valueOf(productId);
			
		} catch (Exception e) {
			return PARSING_ERROR;
		}
	}
	
	
	
	public static NdefMessage createBeamMessage(int id) {
		Log.i("ANDROD BEAM", "createBeamMessage 1");
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

		Log.i("ANDROD BEAM", "createBeamMessage 2");
		return message;
	}
	
	
}