package fr.upmc.mastersar.filemanager;

import java.io.File;
import java.io.IOException;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class FileManager extends ListActivity {

	private String currentpath;

	private File[] files_list;

	private Context currentContext;

	private FileAdapter fileAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		currentContext = this;
		TextView path = (TextView) this.findViewById(R.id.path);

		File f = Environment.getRootDirectory();
		files_list = f.listFiles();
		currentpath = f.getPath();
		path.setText(currentpath);

		fileAdapter = new FileAdapter(this, R.layout.cell_list, files_list);
		fileAdapter.setNotifyOnChange(true);
		getListView().setAdapter(fileAdapter);

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				File filename = (File) adapter.getItemAtPosition(position);
				updateListView(filename);

			}
		});
	}

	public void updateListView(File filename) {
		TextView path = (TextView) this.findViewById(R.id.path);

		try {

			if (filename.exists()) {
				if (filename.isDirectory()) {
					Toast.makeText(currentContext,
							filename.getName() + " is a directory", 15).show();

					currentpath = filename.getCanonicalPath();

					path.setText(currentpath);
					files_list = updateFileList(filename);
					fileAdapter.notifyDataSetChanged();
					for (File f : files_list) {
						Log.i("INFO", "file name : " + f.getName());
					}

				} else {
					Toast.makeText(currentContext,
							filename.getName() + " is a file", 15).show();
				}
			} else {
				Toast.makeText(currentContext,
						filename.getName() + " does not exist", 15).show();
			}

		} catch (IOException ioexception) {
			Log.i("ALERT", "File not found");
		}

	}

	private File[] updateFileList(File fileName) {

		for (File f : files_list) {
			f = null;
		}
		files_list = null;
		return fileName.listFiles();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

}