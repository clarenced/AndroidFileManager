package fr.upmc.mastersar.filemanager;

import java.io.File;

import android.content.Context;
import android.widget.ArrayAdapter;

public class FileAdapter extends ArrayAdapter<File>{

	public FileAdapter(Context context, int textViewResourceId, File[] objects) {
		super(context, textViewResourceId, objects);
	}

}
