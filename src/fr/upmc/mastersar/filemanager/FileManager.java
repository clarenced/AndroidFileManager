package fr.upmc.mastersar.filemanager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FileManager extends ListActivity implements
		android.view.View.OnClickListener {

	private String currentpath;

	private List<File> files_list = new ArrayList<File>();;

	private Context currentContext;

	private FileAdapter fileAdapter;

	Button home, back;

	TextView path;

	private File parent, root;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		currentContext = this;
		path = (TextView) this.findViewById(R.id.path);

		home = (Button) this.findViewById(R.id.home);
		home.setOnClickListener(this);

		back = (Button) this.findViewById(R.id.back);
		back.setOnClickListener(this);

		File f = Environment.getExternalStorageDirectory();
		parent = null;
		root = f.getParentFile();
		File[] files_array = f.listFiles();
		for (File fic : files_array) {
			if (fic.canRead())
				files_list.add(fic);
		}
		currentpath = f.getPath();
		path.setText(currentpath);

		fileAdapter = new FileAdapter(this, R.layout.cell_list, files_list);
		getListView().setAdapter(fileAdapter);

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				File filename = (File) adapter.getItemAtPosition(position);
				if( filename.isDirectory())
					updateListView(filename);
				else
					startIntent(filename);
			}
		});
	}

	protected void startIntent(File filename) {
		
		
	}

	public void updateListView(File filename) {

		if (filename.exists()) {
			if (filename.isDirectory() && filename.canRead()) {
				Log.i("INFO", "---> " + filename.getName());
				Toast.makeText(currentContext,
						filename.getName() + " is a directory", 15).show();
				parent = filename.getParentFile();
				updateDirectory(filename);
			} else {
				Toast.makeText(currentContext,
						filename.getName() + " is a file", 15).show();
			}
		} else {
			Toast.makeText(currentContext,
					filename.getName() + " does not exist", 15).show();
		}

	}

	private void updateDirectory(File filename) {
		TextView path = (TextView) this.findViewById(R.id.path);
		Button button = (Button) this.findViewById(R.id.back);

		button.setEnabled(true);
		try {
			currentpath = filename.getCanonicalPath();
			path.setText(currentpath);

			files_list.clear();
			File[] farray = filename.listFiles();
			for (File f : farray) {
				files_list.add(f);
			}
			fileAdapter.notifyDataSetChanged();
		} catch (IOException ioexception) {
			Log.i("WARNING", "File not found");
		}
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

	@Override
	public void onClick(View v) {

		if (v.getId() == home.getId()) {
			home();
		} else if (v.getId() == back.getId()) {
			back();
		}

	}

	private void home() {
		File f = Environment.getExternalStorageDirectory();
		files_list.clear();

		back.setEnabled(false);
		File[] files_array = f.listFiles();
		parent = null;
		for (File fic : files_array) {
			files_list.add(fic);
		}
		currentpath = f.getPath();
		path.setText(currentpath);
		fileAdapter.notifyDataSetChanged();
	}

	private void back() {
		File f = null;
		if (parent != null) {
			f = parent;
			files_list.clear();
			File[] files_array = f.listFiles();
			for (File fic : files_array) {
				files_list.add(fic);
			}
			currentpath = f.getPath();
			path.setText(currentpath);
			fileAdapter.notifyDataSetChanged();
		}

		if (f.getParent() != null)
			parent = f.getParentFile();
		
		back.setEnabled(!parent.equals(root));

	}

}