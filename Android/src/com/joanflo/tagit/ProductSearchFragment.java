package com.joanflo.tagit;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ProductSearchFragment extends Fragment implements OnItemSelectedListener {

	
	private String[] brands = {"Quicksilver", "Vans", "Zara"};
	private String[] categories = {"Shorts", "T-Shirts", "Trousers"};
	
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_productsearch, container, false);
        
		prepareSearchSection(rootView);
		
        return rootView;
    }



	private void prepareSearchSection(View fragment) {
		TextView et;
		String coin = "€";
		
		// price from
		et = (TextView) fragment.findViewById(R.id.from_coin);
		et.setText(coin);
		
		// price until
		et = (TextView) fragment.findViewById(R.id.until_coin);
		et.setText(coin);
		
		// brand and category spinners
		fillSpinner(fragment, R.id.spinner_productsearch_brand, brands);
		fillSpinner(fragment, R.id.spinner_productsearch_category, categories);
	}



	private void fillSpinner(View fragment, int spinnerId, String[] items) {
		Spinner spinner = (Spinner) fragment.findViewById(spinnerId);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}



	public void onClickButton(View v) {
		// search button clicked
	}



	@Override
	public void onItemSelected(AdapterView<?> adapterView, View v, int i, long l) {
		String name;
		
		TextView tv = (TextView) adapterView.getChildAt(0);
		tv.setTextColor(getResources().getColor(R.color.grey_background));
		
		switch (v.getId()) {
		case R.id.spinner_productsearch_brand:
			name = brands[i];
			break;

		case R.id.spinner_productsearch_category:
			name = categories[i];
			break;
		}
	}



	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

}
