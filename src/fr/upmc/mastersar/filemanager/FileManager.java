package fr.upmc.mastersar.filemanager;

import java.io.File;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class FileManager extends ListActivity {

	private String currentpath;

	private String[] files;

	private Context currentContext;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		currentContext = this;

		TextView path = (TextView) this.findViewById(R.id.path);

		File f = Environment.getRootDirectory();
		currentpath = f.getPath();
		path.setText(currentpath);

		files = f.list();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.cell_list, files);
		getListView().setAdapter(adapter);

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {

				String filename = (String) adapter.getItemAtPosition(position);
				File f = new File(currentpath + "/" + filename);
				if (f.exists()) {
					if (f.isDirectory()) {
						Toast.makeText(currentContext,
								f.getName() + " is a directory", 15).show();
						files = f.list();
					} else {
						Toast.makeText(currentContext,
								f.getName() + " is a file", 15).show();
					}
				} else {
					Toast.makeText(currentContext,
							filename + " does not exist", 15).show();
				}
				

			}

		});
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