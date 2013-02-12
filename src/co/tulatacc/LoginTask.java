package co.tulatacc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class LoginTask extends AsyncTask<String, Integer, String> {

	@Override
	protected String doInBackground(String... params) {
		String email = params[0];
		String password = params[1];
		
		try {
			URL url = new URL("https://");
			
		} catch (MalformedURLException e) {
			Log.d("TACC", String.format("Malformed Exception with message: %s", e.getMessage()));
		} catch (IOException e) {
			Log.d("TACC", String.format("IO Exception with message: %s", e.getMessage()));
		}
		
		return null;
	}

}
