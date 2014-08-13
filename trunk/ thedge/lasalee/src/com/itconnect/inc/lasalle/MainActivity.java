package com.itconnect.inc.lasalle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity{
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
		Intent intent = new Intent(this, alexcrusher.just6weeks.lib.activities.MainActivity.class);
	   
	    startActivity(intent);

    }
}
